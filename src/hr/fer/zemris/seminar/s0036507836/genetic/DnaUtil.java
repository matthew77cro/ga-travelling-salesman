package hr.fer.zemris.seminar.s0036507836.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utilities class used for genetic algorithm for travelling salesman problem
 * @author Matija Bačić
 *
 */
public class DnaUtil {
	
	/**
	 * Random object for getting random values
	 */
	private static Random rnd = new Random(System.currentTimeMillis());
	
	/**
	 * Crosses two dna chains using 
	 * <a href="http://www.dmi.unict.it/mpavone/nc-cs/materiale/moscato89.pdf">ordered crossover</a> 
	 * and mutates resulting dna chain with given mutation rate using 
	 * <a href="https://www.tutorialspoint.com/genetic_algorithms/genetic_algorithms_mutation.htm">swap mutation</a>.
	 * Reason for using said algorithms is to preserve relative order of elements and hava only one
	 * appearance of a given object in the dna chain.
	 * @param dna1 first parent dna chain
	 * @param dna2 second parent dna chain
	 * @param mutationRate rate for mutating the child dna chain
	 * @return new dna chain created as a result of a crossover operation
	 * @throws NullPointerException if dna1 or dna2 is null
	 * @throws IllegalArgumentException if mutationRate is <0 or >1
	 * @throws DnaNotCompatibleException if dna chains are not compatible for crossover operation (see Dna.isCompatible method)
	 */
	public static Dna crossover(Dna dna1, Dna dna2, double mutationRate) {
		if(dna1==null || dna2==null) throw new NullPointerException("Dna must not be null!");
		if(mutationRate<0 || mutationRate>=1) throw new IllegalArgumentException("Mutation rate must be between 0 and 1!");
		if(!dna1.isCompatible(dna2)) throw new DnaNotCompatibleException("dna1 and dna2 are not compatible!");
		
		int length = dna1.getGenes().size();
		List<Town> child = new ArrayList<Town>(length);
		
		for(int i=0; i<length; i++)
			child.add(null);
		
		//ordered crossover
		int border1 = rnd.nextInt(length);
		int border2 = rnd.nextInt(length);
		
		//from first parent
		if(border1==border2) {
			child.set(border1, dna1.getGenes().get(border1));
		} else {
			int lowerBorder = border1 < border2 ? border1 : border2;
			int upperBorder = border1 > border2 ? border1 : border2;
			
			for(int i=lowerBorder; i<=upperBorder; i++) {
				child.set(i, dna1.getGenes().get(i));
			}
		}
		
		int parentIndex = 0;
		//from second parent
		for(int i=0; i<length; i++) {
			if(child.get(i)==null) {
				while(child.contains(dna2.getGenes().get(parentIndex))) parentIndex++;
				child.set(i, dna2.getGenes().get(parentIndex));
			}
		}
		
		//swap mutation
		//careful so we do not swap first town (first town is always a starter town)
		for(int i=0; i<length; i++)
			if(Math.random()<mutationRate) {
				int swapWith = rnd.nextInt(length);
				Town holder = child.get(i);
				child.set(i, child.get(swapWith));
				child.set(swapWith, holder);
			}
		
		return new Dna(child, dna1.getAlphabet());
	}

}
