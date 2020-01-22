package hr.fer.zemris.seminar.s0036507836.genetic.text.window;

import java.util.ArrayList;
import java.util.stream.IntStream;

import javax.swing.SwingUtilities;

import hr.fer.zemris.seminar.s0036507836.genetic.IAlphabet;
import hr.fer.zemris.seminar.s0036507836.genetic.IPopulation;
import hr.fer.zemris.seminar.s0036507836.genetic.text.GeneticImpl;

public class PopulationEvolver implements Runnable{
	
	private Window w;
	private boolean running = false;
	
	public PopulationEvolver(Window w) {
		this.w = w;
	}

	@Override
	public void run() {
		running = true;
		initAndRun();
		running = false;
		w.disableInput(false);
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
		
		generate(target, DNAalphabet, populationSize, mutationRate, w.getTarget().getText());
	}
	
	private void generate(String target, String dnaAlphabet, int populationSize, double mutationRate, String goal) {
		
		var dnaList = new ArrayList<Character>();
		var goalList = new ArrayList<Character>();
		IntStream.range(0, dnaAlphabet.length()).forEach((i) -> dnaList.add(dnaAlphabet.charAt(i)));
		IntStream.range(0, goal.length()).forEach((i) -> goalList.add(goal.charAt(i)));
		
		GeneticImpl.goal = goalList;
		IPopulation<Character> p = new IPopulation<Character>(populationSize, mutationRate, 
				new IAlphabet<Character>(dnaList), 
				GeneticImpl.crossoverFunction, 
				GeneticImpl.mutationFunction, 
				GeneticImpl.fitnessFunction, 
				GeneticImpl.randomDnaGenerator);
		
		 do {
			SwingUtilities.invokeLater(() -> w.getBestOfGeneration().setText(GeneticImpl.listToStr(p.getBestForCurrentGeneration().getDna().getGenes())));
			SwingUtilities.invokeLater(() -> w.getGenerationNo().setText(Integer.valueOf(p.getGeneration()).toString()));
			SwingUtilities.invokeLater(() -> w.getBestFintessValue().setText(Double.toString(((double)p.getPopulation().get(0).getFitness())/GeneticImpl.goal.size())));
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			p.nextGeneration();
		} while(!p.getBestForCurrentGeneration().getDna().getGenes().equals(goalList) && running);
		 
		SwingUtilities.invokeLater(() -> w.getBestOfGeneration().setText(GeneticImpl.listToStr(p.getBestForCurrentGeneration().getDna().getGenes())));
		SwingUtilities.invokeLater(() -> w.getGenerationNo().setText(Integer.valueOf(p.getGeneration()).toString()));
		SwingUtilities.invokeLater(() -> w.getBestFintessValue().setText(Double.toString(((double)p.getPopulation().get(0).getFitness())/GeneticImpl.goal.size())));
		
	}
	
	public void toggleRunning() {
		this.running = running ? false : true;
	}
	
	public boolean isRunning() {
		return running;
	}

}
