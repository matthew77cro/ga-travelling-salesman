package hr.fer.zemris.seminar.s0036507836.travellingSalesman.genetic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import hr.fer.zemris.seminar.s0036507836.genetic.IAlphabet;
import hr.fer.zemris.seminar.s0036507836.genetic.IDna;
import hr.fer.zemris.seminar.s0036507836.genetic.IPopulation;
import hr.fer.zemris.seminar.s0036507836.travellingSalesman.City;

public class Demo extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Demo() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(550, 550);
	}
	
	public static void main(String[] args) {
		
		City[] townsArr = { new City("Osijek", 450, 100),
						 new City("Zagreb", 200, 80),
						 new City("Pula", 20, 100),
						 new City("Varazdin", 250, 20),
						 new City("Djakovo", 460, 125),
						 new City("Rijeka", 50, 150),
						 new City("Zadar", 75, 300),
						 new City("Dubrovnik", 150, 450),
						 new City("Sl Brod", 350, 150)
						 };
		List<City> towns = Arrays.asList(townsArr);
		
		IPopulation<City> p = new IPopulation<City>(200, 0.12, new IAlphabet<City>(towns), GeneticImpl.crossoverFunction,
				GeneticImpl.mutationFunction, GeneticImpl.fitnessFunction, GeneticImpl.randomDnaGenerator);
		
		JFrame frame = new Demo();
		Board b = new Board(p.getBestForCurrentGeneration().getDna());
		frame.add(b);
		frame.setVisible(true);
		
		Scanner sc = new Scanner(System.in);
		
		double min = Double.MAX_VALUE;
		
		for(int i=0; i<5000000; i++) {
			//System.out.print("Generation: " + i + " -> BEST DISTANCE: ");
			double distance = 1.0/p.getBestForCurrentGeneration().getFitness();
			//System.out.println(distance);  //printing distance for best entity
			if(min>distance) {
				min = distance;
				b.setDna(p.getBestForCurrentGeneration().getDna());
			}
			p.nextGeneration();
			frame.repaint();
		}
		
		System.out.println("MINIMUM: " + min);
		
		for(City t : p.getBestForCurrentGeneration().getDna().getGenes()) {
			System.out.println(t.getX() + " " + t.getY());
		}
		
		sc.close();
		
	}
	
	private static class Board extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private IDna<City> dna;
		
		public Board(IDna<City> dna) {
			this.dna = dna;
		}
		
		public void setDna(IDna<City> dna) {
			this.dna = dna;
		}
		
		@Override
		protected void paintComponent(Graphics g) {			
			Graphics2D gr = (Graphics2D) g;
			
			List<City> towns = dna.getGenes();
			
			for(int i=0; i<towns.size()-1; i++) {
				gr.drawString(towns.get(i).getName(), towns.get(i).getX(), towns.get(i).getY());
				gr.drawLine(towns.get(i).getX(), towns.get(i).getY(), towns.get(i+1).getX(), towns.get(i+1).getY());
			}
			City last = towns.get(towns.size()-1);
			gr.drawString(last.getName(), last.getX(), last.getY());
		
		}
		
	}

}
