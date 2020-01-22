package hr.fer.zemris.seminar.s0036507836.window;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import hr.fer.zemris.seminar.s0036507836.genetic.IPopulation;

public class PopulationEvolver implements Runnable{

	private IPopulation p;
	private Board b;
	private JLabel generationNo;
	
	private boolean stop = false;
	private boolean running = false;
	
	public PopulationEvolver(IPopulation p, Board b, JLabel generationNo) {
		this.p = p;
		this.b = b;
		this.generationNo = generationNo;
	}
	
	@Override
	public void run() {
		
		running = true;
		
		int maxNumOfSteps = p.getGoalX()+p.getGoalY()-p.getStartX()-p.getStartY();
		
		while(true) {
			SwingUtilities.invokeLater(() -> b.repaint());
			
			generationNo.setText("Generation: " + p.getGeneration());
			
			for(int i=0; i<maxNumOfSteps; i++) {
				p.step();
				SwingUtilities.invokeLater(() -> b.repaint());
				if(stop) return;
			}
			
			if(p.isFinished()) break;
			p.nextGeneration();
			b.setPopulation(p.getPopulation());
		}
		
		running = false;
		
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
