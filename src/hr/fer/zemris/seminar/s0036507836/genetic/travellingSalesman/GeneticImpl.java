package hr.fer.zemris.seminar.s0036507836.genetic.travellingSalesman;

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
	 * Random object for getting random values
	 */
	private static Random rnd = new Random(System.currentTimeMillis());
	
	/**
	 * Crosses two dna chains using 
	 * <a href="http://www.dmi.unict.it/mpavone/nc-cs/materiale/moscato89.pdf">ordered crossover</a>.
	 * Reason for using said algorithms is to preserve relative order of elements and have only one
	 * appearance of a given object in the dna chain.
	 */
	public static final BiFunction<List<City>, List<City>, List<City>> crossoverFunction;
	
	/**
	 * Mutates dna chain with given mutation rate using 
	 * <a href="https://www.tutorialspoint.com/genetic_algorithms/genetic_algorithms_mutation.htm">swap mutation</a>.
	 * Reason for using said algorithms is to preserve relative order of elements and have only one
	 * appearance of a given object in the dna chain.
	 */
	public static final BiFunction<IDna<City>, Double, IDna<City>> mutationFunction;

	/**
	 * Function for calculating fitness of an entity
	 */
	public static final Function<IEntity<City>, Double> fitnessFunction;

	/**
	 * Generating random dna for initial population
	 */
	public static final Function<IAlphabet<City>, IDna<City>> randomDnaGenerator;
	
	static {		
		crossoverFunction = (dna1, dna2) -> {
			if(!dna1.get(0).equals(dna2.get(0))) throw new DnaNotCompatibleException("Start cities are different!"); //if starting town is different, they are not compatible for crossovering
			int length = dna1.size();
			List<City> child = new ArrayList<>(length);
			
			child.add(dna1.get(0)); //set starting city
			for(int i=1; i<length; i++)
				child.add(null);
			
			//ordered crossover
			//border bounds for dna from first parent (but skipping first city in the dna chain - it must be at that position, starting city must not change!)
			int border1 = 1+rnd.nextInt(length-1);
			int border2 = 1+rnd.nextInt(length-1);
			
			//copying dna from first parent (including genes directly at the border)
			if(border1==border2) {
				child.set(border1, dna1.get(border1));
			} else {
				int lowerBorder = border1 < border2 ? border1 : border2;
				int upperBorder = border1 > border2 ? border1 : border2;
				
				for(int i=lowerBorder; i<=upperBorder; i++) {
					child.set(i, dna1.get(i));
				}
			}
			
			//from second parent (again, skipping first (starting) city)
			int parentIndex = 0;
			for(int i=1; i<length; i++) {
				if(child.get(i)==null) {
					while(child.contains(dna2.get(parentIndex))) parentIndex++;
					child.set(i, dna2.get(parentIndex));
				}
			}
			
			return child;
		};
		
		mutationFunction = (child, mutationRate) -> {
			//swap mutation
			//careful so we do not swap first town (first town is always a starter town)
			for(int i=1, length=child.getGenes().size(); i<length; i++) {
				if(Math.random()<mutationRate) {
					int swapWith = 1+rnd.nextInt(length-1);
					City holder = child.getGenes().get(i);
					child.getGenes().set(i, child.getGenes().get(swapWith));
					child.getGenes().set(swapWith, holder);
				}
			}
			
			return child;
		};
		
		fitnessFunction = (entity) -> {
			double fitness = 0;
			List<City> genes = entity.getDna().getGenes();
			for(int i=0, stop=genes.size()-1; i<stop; i++) {
				fitness += genes.get(i).distanceTo(genes.get(i+1));
			}
			return 1.0/fitness;
		};
		
		randomDnaGenerator = (alphabet) -> {			
			List<City> dna = new ArrayList<>();
				
			dna.add(alphabet.getGenes().get(0)); // adding a starting town
			for(int i=1, dnaSize = alphabet.getGenes().size(); i<dnaSize; i++) {		
				//pick a random town that has not already been chosen
				int random = 1+rnd.nextInt(dnaSize-1);
				while(dna.contains(alphabet.getGenes().get(random))) random = 1+rnd.nextInt(dnaSize-1);
				dna.add(alphabet.getGenes().get(random));
			}
			
			return new IDna<City>(dna, alphabet);
		};
	}

}
