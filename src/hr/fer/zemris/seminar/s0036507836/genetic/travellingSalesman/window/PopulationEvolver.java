package hr.fer.zemris.seminar.s0036507836.genetic.travellingSalesman.window;

import javax.swing.JLabel;

import hr.fer.zemris.seminar.s0036507836.genetic.IEntity;
import hr.fer.zemris.seminar.s0036507836.genetic.IPopulation;
import hr.fer.zemris.seminar.s0036507836.genetic.travellingSalesman.City;

public class PopulationEvolver implements Runnable{

	private IPopulation<City> p;
	private Board b;
	private JLabel generationNo;
	private JLabel pathLength;
	private boolean slowTheTime;
	
	private int bestGeneration = 0;
	private IEntity<City> best = null;
	
	private boolean stop = false;
	private boolean running = false;
	
	public PopulationEvolver(IPopulation<City> p, Board b, JLabel generationNo, JLabel pathLength, boolean slowTheTime) {
		this.p = p;
		this.b = b;
		this.generationNo = generationNo;
		this.pathLength = pathLength;
		this.slowTheTime = slowTheTime;
	}
	
	@Override
	public void run() {
		
		running = true;
		
		best = p.getBestForCurrentGeneration();
		pathLength.setText("Length: " + 1.0/best.getFitness());

		while(!stop) {
			if(best.getFitness()<p.getBestForCurrentGeneration().getFitness()) {
				best = p.getBestForCurrentGeneration();
				b.setDna(best.getDna());
				b.repaint();
				while(b.isDrawing()){
					System.out.print(".");
				}
				bestGeneration = p.getGeneration();
				pathLength.setText("Length: " + 1.0/best.getFitness());
			}
			
			generationNo.setText("Generation: " + p.getGeneration());
			p.nextGeneration();
			
			if(slowTheTime)
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
		}
		
		running = false;
		
	}
	
	public IEntity<City> getBest() {
		return best;
	}
	
	public int getBestGeneration() {
		return bestGeneration;
	}
	
	public void stop() {
		stop = true;
	}
	
	public boolean isStopped() {
		return stop;
	}
	
	public boolean isRunning() {
		return running;
	}
	
}
