package hr.fer.zemris.seminar.s0036507836.demo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import hr.fer.zemris.seminar.s0036507836.genetic.Alphabet;
import hr.fer.zemris.seminar.s0036507836.genetic.Dna;
import hr.fer.zemris.seminar.s0036507836.genetic.Population;
import hr.fer.zemris.seminar.s0036507836.genetic.Town;

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
		
		Town[] townsArr = { new Town("Zagreb", 200, 80),
						 new Town("Pula", 20, 100),
						 new Town("Varazdin", 250, 20),
						 new Town("Djakovo", 460, 125),
						 new Town("Rijeka", 50, 150),
						 new Town("Zadar", 75, 300),
						 new Town("Dubrovnik", 150, 450),
						 new Town("Sl Brod", 350, 150) 
						 };
		List<Town> towns = Arrays.asList(townsArr);
		
		Population p = new Population(200, 0.01, new Alphabet(towns, new Town("Osijek", 450, 100)));
		
		JFrame frame = new Demo();
		Board b = new Board(p.getBestForCurrentGeneration().getDna());
		frame.add(b);
		frame.setVisible(true);
		
		Scanner sc = new Scanner(System.in);
		
		double min = Double.MAX_VALUE;
		
		for(int i=0; i<5000; i++) {
			//System.out.print("Generation: " + i + " -> BEST DISTANCE: ");
			double distance = 1.0/p.getBestForCurrentGeneration().getFitness();
			//System.out.println(distance);  //printing distance for best entity
			if(min>distance) min = distance;
			p.nextGeneration();
			//sc.next();
			b.setDna(p.getBestForCurrentGeneration().getDna());
			frame.repaint();
		}
		
		System.out.println("MINIMUM: " + min);
		
		for(Town t : p.getBestForCurrentGeneration().getDna().getGenes()) {
			System.out.println(t.getX() + " " + t.getY());
		}
		
		sc.close();
		
	}
	
	private static class Board extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Dna dna;
		
		public Board(Dna dna) {
			this.dna = dna;
		}
		
		public void setDna(Dna dna) {
			this.dna = dna;
		}
		
		@Override
		protected void paintComponent(Graphics g) {			
			Graphics2D gr = (Graphics2D) g;
			
			List<Town> towns = dna.getGenes();
			
			Town start = dna.getAlphabet().getStartTown();
			gr.drawString(start.getName(), start.getX(), start.getY());
			gr.drawLine(start.getX(), start.getY(), towns.get(0).getX(), towns.get(0).getY());
			for(int i=0; i<towns.size()-1; i++) {
				gr.drawString(towns.get(i).getName(), towns.get(i).getX(), towns.get(i).getY());
				gr.drawLine(towns.get(i).getX(), towns.get(i).getY(), towns.get(i+1).getX(), towns.get(i+1).getY());
			}
			Town last = towns.get(towns.size()-1);
			gr.drawString(last.getName(), last.getX(), last.getY());
		
		}
		
	}

}
