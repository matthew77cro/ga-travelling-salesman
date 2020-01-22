package hr.fer.zemris.seminar.s0036507836;

import java.util.Scanner;

import hr.fer.zemris.seminar.s0036507836.genetic.Genes;
import hr.fer.zemris.seminar.s0036507836.genetic.IPopulation;

public class Main {
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		int rows = 10;
		int cols = 15;
		
		IPopulation p = new Population(1, 0.01, rows, cols, 12, 8, 0, 0);
		
		Genes[] genes = p.getPopulation().get(0).getDna().getGenes();
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				System.out.print(genes[i*15+j]);
			}
			System.out.println();
		}
		
		do {
			System.out.printf(p.toString());
			System.out.println(p.getPopulation().get(0).getFitness());
			p.step();
			String s = sc.next();
			if(s.equals("end")) break;
		} while(true);
		
		p.nextGeneration();
		
		do {
			System.out.printf(p.toString());
			System.out.println(p.getPopulation().get(0).getFitness());
			p.step();
			String s = sc.next();
			if(s.equals("end")) break;
		} while(true);
		
		sc.close();
		
	}
	
}
