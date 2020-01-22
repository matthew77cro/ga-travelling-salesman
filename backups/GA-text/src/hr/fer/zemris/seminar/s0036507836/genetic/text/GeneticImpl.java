package hr.fer.zemris.seminar.s0036507836.genetic.text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

import hr.fer.zemris.seminar.s0036507836.genetic.DnaNotCompatibleException;
import hr.fer.zemris.seminar.s0036507836.genetic.IAlphabet;
import hr.fer.zemris.seminar.s0036507836.genetic.IDna;
import hr.fer.zemris.seminar.s0036507836.genetic.IEntity;

public class GeneticImpl {
	
	/**
	 * Goal string
	 */
	public static List<Character> goal;
	
	/**
	 * Random object for getting random values
	 */
	private static Random rnd = new Random(System.currentTimeMillis());
	
	/**
	 * Crosses two dna chains. Randomly generates a percentage and uses first part of char sequence from
	 * the first parent, and rest from second.
	 */
	public static final BiFunction<List<Character>, List<Character>, List<Character>> crossoverFunction;
	
	/**
	 * Mutates dna chain with given mutation rate using 
	 * <a href="https://www.tutorialspoint.com/genetic_algorithms/genetic_algorithms_mutation.htm">swap mutation</a>.
	 * Reason for using said algorithms is to preserve relative order of elements and have only one
	 * appearance of a given object in the dna chain.
	 */
	public static final BiFunction<IDna<Character>, Double, IDna<Character>> mutationFunction;

	/**
	 * Function for calculating fitness of an entity
	 */
	public static final Function<IEntity<Character>, Double> fitnessFunction;

	/**
	 * Generating random dna for initial population
	 */
	public static final Function<IAlphabet<Character>, IDna<Character>> randomDnaGenerator;
	
	static {		
		crossoverFunction = (dna1, dna2) -> {
			List<Character> child = new ArrayList<>();
			
			int length = dna1.size();
			int middle = rnd.nextInt(length);
			
			for(int i=0; i<length; i++) {
				char next;
				if(i<middle) next = dna1.get(i);
				else next = dna2.get(i);
				child.add(next);
			}
			
			return child;
		};
		
		mutationFunction = (child, mutationRate) -> {
			for(int i=0, stop = child.getGenes().size(); i<stop; i++)
				if(Math.random()<mutationRate) {
					char replacement = child.getAlphabet().getGenes().get(rnd.nextInt(child.getAlphabet().getGenes().size()));
					child.getGenes().set(i, replacement);
				}
			
			return child;
		};
		
		fitnessFunction = (e) -> {
			if(e.getDna().getGenes().size()!=goal.size()) throw new DnaNotCompatibleException("Entity dna chain size is not equal to size of goal");
			int fitness = 0;
			int length = e.getDna().getGenes().size();
			for(int i=0; i<length; i++) {
				if(e.getDna().getGenes().get(i)==goal.get(i)) {
					fitness++;
				}
			}
			return (double) fitness;
		};
		
		randomDnaGenerator = (alphabet) -> {			
			List<Character> dna = new ArrayList<>();
				
			for(int i=0, dnaSize = alphabet.getGenes().size(), length = goal.size(); i<length; i++) {
				int random = rnd.nextInt(dnaSize);
				dna.add(alphabet.getGenes().get(random));
			}
			
			return new IDna<Character>(dna, alphabet);
		};
	}
	
	public static List<Character> strToList(String string) {
		List<Character> list = new ArrayList<Character>();
		for(int i=0, length = string.length(); i<length; i++) {
			list.add(string.charAt(i));
		}
		return list;
	}
	
	public static String listToStr(List<Character> list) {
		StringBuilder sb = new StringBuilder();
		list.forEach((c) -> sb.append(c));
		return sb.toString();
	}

}
