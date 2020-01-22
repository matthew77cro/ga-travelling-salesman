package hr.fer.zemris.seminar.s0036507836.travellingSalesman.window;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.seminar.s0036507836.genetic.IEntity;
import hr.fer.zemris.seminar.s0036507836.genetic.IPopulation;
import hr.fer.zemris.seminar.s0036507836.travellingSalesman.City;

public class PopulationEvolver implements Runnable{

	private IPopulation<City> p;
	private Board b;
	private Window w;
	
	private AtomicBoolean running;
	
	public PopulationEvolver(IPopulation<City> p, Board b, Window w) {
		this.p = p;
		this.b = b;
		this.w = w;
		this.running = new AtomicBoolean(false);
	}
	
	@Override
	public void run() {
		
		running.set(true);
		
    	w.info(String.format("Created population of size %d with mutation %f.", p.getPopulationSize(), p.getMutationRate()));
    	
    	b.repaint();
    	while(b.isDrawing().get());
		
		IEntity<City> best = p.getBestForCurrentGeneration();
		int bestGeneration = 0;
		
		w.getPathLength().setText("Length: " + 1.0/best.getFitness());

		while(!w.isStopped().get()) {
			if(best.getFitness()<p.getBestForCurrentGeneration().getFitness()) {
				best = p.getBestForCurrentGeneration();
				b.setCities(best.getDna().getGenes());
				b.repaint();
				while(b.isDrawing().get());
				bestGeneration = p.getGeneration();
				w.getPathLength().setText("Length: " + 1.0/best.getFitness());
			}
			
			w.getGenerationNo().setText("Generation: " + p.getGeneration());
			p.nextGeneration();
		}
		
		w.info(String.format("Best enity has been found in %d. generation with path length %f.", bestGeneration, 1.0/best.getFitness()));
		
		running.set(false);
		
	}
	
	public AtomicBoolean isRunning() {
		return running;
	}
	
}
