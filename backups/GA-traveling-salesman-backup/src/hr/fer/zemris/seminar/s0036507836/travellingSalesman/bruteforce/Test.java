package hr.fer.zemris.seminar.s0036507836.travellingSalesman.bruteforce;

import java.util.Arrays;
import java.util.Scanner;

import hr.fer.zemris.seminar.s0036507836.travellingSalesman.City;

public class Test {
	
	public static void main(String[] args) {
		TravelingSalesmanBruteforce tsb = new TravelingSalesmanBruteforce(
				Arrays.asList(new City("One", 0, 0), 
						new City("One", 0, 0), 
						new City("One", 0, 0),
						new City("One", 0, 0), 
						new City("One", 0, 0)
						));
		
		Scanner sc = new Scanner(System.in);
		var it = tsb.iterator();
		while(it.hasNext()) {
			//sc.next();
			it.next();
		}
		sc.close();
	}

}
