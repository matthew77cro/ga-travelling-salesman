package hr.fer.zemris.seminar.s0036507836.genetic.text.window;

import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Window extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField target;
	private JTextField DNAalphabet;
	private JTextField populationSize;
	private JTextField mutationRate;
	
	private JButton startButton;
	private JButton stopButton;
	
	private JLabel bestOfGeneration;
	private JLabel generationNo;
	private JLabel bestFintessValue;
	
	private PopulationEvolver pe;

	public Window() {
		this.setSize(640, 250);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Genetic algorithm - GENERATING CHAR ARRAY");
		init();
	}
	
	private void init() {		
		this.setLayout(new GridLayout(1,2));
		
		Panel p = new Panel(new GridLayout(0,2));
		
		JLabel l1 = new JLabel();
		l1.setText("Target character sequence:");
		l1.setHorizontalAlignment(JLabel.CENTER);
		p.add(l1);
		
		target = new JTextField();
		target.setText("Genetski algoritmi.");
		p.add(target);
		
		JLabel l2 = new JLabel();
		l2.setText("DNA alphabet:");
		l2.setHorizontalAlignment(JLabel.CENTER);
		p.add(l2);
		
		DNAalphabet = new JTextField();
		DNAalphabet.setText("qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM.!?' ");
		p.add(DNAalphabet);
		
		JLabel l3 = new JLabel();
		l3.setText("Population size:");
		l3.setHorizontalAlignment(JLabel.CENTER);
		p.add(l3);
		
		populationSize = new JTextField();
		populationSize.setText("200");
		p.add(populationSize);
		
		JLabel l4 = new JLabel();
		l4.setText("Mutation rate:");
		l4.setHorizontalAlignment(JLabel.CENTER);
		p.add(l4);
		
		mutationRate = new JTextField();
		mutationRate.setText("0.01");
		p.add(mutationRate);
		
		startButton = new JButton();
		startButton.setText("START");
		startButton.setName("startButton");
		p.add(new JLabel());
		p.add(new JLabel());
		p.add(startButton);
		startButton.addActionListener((e) -> {
			disableInput(true);
			pe = new PopulationEvolver(this);
			new Thread(pe).start();
		});
		
		stopButton = new JButton();
		stopButton.setText("STOP");
		stopButton.setName("stopButton");
		stopButton.setEnabled(false);
		p.add(stopButton);
		stopButton.addActionListener((e) -> {
				pe.toggleRunning();
				disableInput(false);
		});
		
		Panel p2 = new Panel(new GridLayout(2, 1));
		
		bestOfGeneration = new JLabel();
		bestOfGeneration.setText("<GENERATING>");
		bestOfGeneration.setHorizontalAlignment(JLabel.CENTER);
		p2.add(bestOfGeneration);
		
		Panel p3 = new Panel(new GridLayout(2,2));
		
		JLabel generation = new JLabel();
		generation.setText("Generation:");
		generation.setHorizontalAlignment(JLabel.CENTER);
		p3.add(generation);
		
		generationNo = new JLabel();
		generationNo.setText("<GENERATION No>");
		generationNo.setHorizontalAlignment(JLabel.CENTER);
		p3.add(generationNo);
		
		JLabel bestFitness = new JLabel();
		bestFitness.setText("Best fitness:");
		bestFitness.setHorizontalAlignment(JLabel.CENTER);
		p3.add(bestFitness);
		
		bestFintessValue = new JLabel();
		bestFintessValue.setText("<BEST FINESS VALUE>");
		bestFintessValue.setHorizontalAlignment(JLabel.CENTER);
		p3.add(bestFintessValue);
		
		p2.add(p3);
		
		this.add(p);
		this.add(p2);
	}
	
	public void disableInput(boolean disable) {
		getTarget().setEnabled(!disable);
		getDNAalphabet().setEnabled(!disable);
		getMutationRate().setEnabled(!disable);
		getPopulationSize().setEnabled(!disable);
		getStartButton().setEnabled(!disable);
		getStopButton().setEnabled(disable);
	}
	
	public JTextField getTarget() {
		return target;
	}

	public JTextField getDNAalphabet() {
		return DNAalphabet;
	}

	public JTextField getPopulationSize() {
		return populationSize;
	}

	public JTextField getMutationRate() {
		return mutationRate;
	}

	public JButton getStartButton() {
		return startButton;
	}

	public JButton getStopButton() {
		return stopButton;
	}

	public JLabel getBestOfGeneration() {
		return bestOfGeneration;
	}

	public JLabel getGenerationNo() {
		return generationNo;
	}
	
	public JLabel getBestFintessValue() {
		return bestFintessValue;
	}

}
