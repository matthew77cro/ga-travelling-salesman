package hr.fer.zemris.seminar.s0036507836.window;

import javax.swing.SwingUtilities;

import hr.fer.zemris.seminar.s0036507836.DNA;
import hr.fer.zemris.seminar.s0036507836.Population;

public class PopulationEvolver implements Runnable{
	
	private Window w;
	private boolean run = false;
	
	public PopulationEvolver(Window w) {
		this.w = w;
	}

	@Override
	public void run() {
		
		run = true;
		disableWhenRunning();
		initAndRun();
		enableAfterRunning();
		run = false;
		
	}
	
	private void disableWhenRunning() {
		w.getTarget().setEnabled(false);
		w.getDNAalphabet().setEnabled(false);
		w.getMutationRate().setEnabled(false);
		w.getPopulationSize().setEnabled(false);
		w.getStartButton().setEnabled(false);
		w.getStopButton().setEnabled(true);
	}
	
	private void enableAfterRunning() {
		w.getTarget().setEnabled(true);
		w.getDNAalphabet().setEnabled(true);
		w.getMutationRate().setEnabled(true);
		w.getPopulationSize().setEnabled(true);
		w.getStartButton().setEnabled(true);
		w.getStopButton().setEnabled(false);
	}
	
	private void initAndRun() {
		if(w.getTarget().getText().isEmpty() || w.getDNAalphabet().getText().isEmpty() || w.getMutationRate().getText().isEmpty() || w.getPopulationSize().getText().isEmpty()) {
			SwingUtilities.invokeLater(() -> w.getBestOfGeneration().setText("Error: text fields must not be empty!"));
			return;
		}
		
		String target = w.getTarget().getText();
		String DNAalphabet = w.getDNAalphabet().getText();
		int populationSize;
		double mutationRate;
		
		
		try {
			populationSize = Integer.parseInt(w.getPopulationSize().getText());
			if(populationSize<=0) throw new IllegalArgumentException();
		} catch (Exception e) {
			SwingUtilities.invokeLater(() -> w.getBestOfGeneration().setText("Population size must be integer > 0"));
			return;
		}
		
		try {
			mutationRate = Double.parseDouble(w.getMutationRate().getText());
			if(mutationRate<0 || mutationRate>1) throw new IllegalArgumentException();
		} catch (Exception e) {
			SwingUtilities.invokeLater(() -> w.getBestOfGeneration().setText("Mutation rate must be double >=0 && <=1"));
			return;
		}
		
		generate(target, DNAalphabet, populationSize, mutationRate);
	}
	
	private void generate(String target, String dnaAlphabet, int populationSize, double mutationRate) {
		Population p = new Population(populationSize, mutationRate, new DNA(target.toCharArray()), 
				new DNA(dnaAlphabet.toCharArray()));
		
		do {
			SwingUtilities.invokeLater(() -> w.getBestOfGeneration().setText(p.getPopulation().get(0).getDna().getGenesAsString()));
			SwingUtilities.invokeLater(() -> w.getGenerationNo().setText(Integer.valueOf(p.getGeneration()).toString()));
			SwingUtilities.invokeLater(() -> w.getBestFintessValue().setText(Double.toString(((double)p.getPopulation().get(0).getFitness())/p.getGoalSize())));
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			p.nextGeneration();
		} while(!p.getPopulation().get(0).getDna().getGenesAsString().equals(target) && run);
		
		SwingUtilities.invokeLater(() -> w.getBestOfGeneration().setText(p.getPopulation().get(0).getDna().getGenesAsString()));
		SwingUtilities.invokeLater(() -> w.getGenerationNo().setText(Integer.valueOf(p.getGeneration()).toString()));
		SwingUtilities.invokeLater(() -> w.getBestFintessValue().setText(Double.toString(((double)p.getPopulation().get(0).getFitness())/p.getGoalSize())));
		
	}
	
	public void toggleRun() {
		this.run = run ? false : true;
	}

}
