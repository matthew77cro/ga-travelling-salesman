package hr.fer.zemris.seminar.s0036507836.genetic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * This class model a population of entities of a genetic algorithm for travelling salesman problem.
 * @author Matija Bačić
 *
 */
public class Population {
	
	/**
	 * Random object for getting random values
	 */
	static Random rnd = new Random(System.currentTimeMillis());
	
	/**
	 * Size of the population (number of entities).
	 */
	private final int populationSize;
	/**
	 * Mutation rate from generation to generation
	 */
	private double mutationRate;
	/**
	 * Current generation number (0 at the start then increments by one)
	 */
	private int generation;
	/**
	 * Alphabet from which genes are picked.
	 */
	private final Alphabet alphabet;
	
	/**
	 * List of entities in this population
	 */
	private List<Entity> population;
	
	/**
	 * Creates and initialises a new population.
	 * @param populationSize size of the population (number of entities)
	 * @param mutationRate mutation rate from generation to generation
	 * @param alphabet alphabet from which the genes are picked for each entity
	 * @throws NullPointerException if alphabet is <code>null</code>
	 */
	public Population(int populationSize, double mutationRate, Alphabet alphabet) {
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.alphabet = Objects.requireNonNull(alphabet);
		this.generation = 0;
		
		population = initialPopulation();
		sortPopulationByFitness();
	}

	/**
	 * Advances the population to the next by crossing over best entities from current generation. Next
	 * generation has the same number of entities as current population.
	 */
	public void nextGeneration() {
		ArrayList<Entity> newPopulation = new ArrayList<>();
		
		double fitnessSum = 0;
		
		// choosing which entity is participating in a crossover process is like throwing darts
		// it is more likely to hit one with higher fitness
		
		int tenPercentPopulation = (int) (0.1*populationSize);
		double darts[] = new double[tenPercentPopulation];
		for(int i=0; i<tenPercentPopulation; i++) {
			Entity e = population.get(i);
			if(i==0) darts[i] = e.getFitness();
			else darts[i] = e.getFitness() + darts[i-1];
			i++;
			fitnessSum += e.getFitness();
		}
		
		for (int i=0; i<populationSize; i++) {
			double entity1Code = rnd.nextDouble()*fitnessSum;
			double entity2Code = rnd.nextDouble()*fitnessSum;
			
			Entity e1, e2;
			int x, y;
			for(x=0;x<darts.length-1 && darts[x]<entity1Code; x++);
			for(y=0;y<darts.length-1 && darts[y]<entity2Code; y++);
			
			e1=population.get(x);
			e2=population.get(y);
			
			newPopulation.add(new Entity(DnaUtil.crossover(e1.getDna(), e2.getDna(), mutationRate)));
		}
		
		population = newPopulation;
		sortPopulationByFitness();
		generation++;
	}
	
	/**
	 * Returns entity with best fitness value from the current generation.
	 * @return entity with best fitness value from the current generation.
	 */
	public Entity getBestForCurrentGeneration() {
		return population.get(0);
	}
	
	/**
	 * Sorts population list by fitness value descending.
	 */
	public void sortPopulationByFitness() { 
		population.sort((o1, o2) -> Double.compare(o2.getFitness(), o1.getFitness()));
	}

	/**
	 * Returns number of entities of the population.
	 * @return number of entities of the population.
	 */
	public int getPopulationSize() {
		return populationSize;
	}
	
	/**
	 * Returns all entities from the current generation of this population sorted by fitness value descending.
	 * @return all entities from the current generation of this population sorted by fitness value descending.
	 */
	public List<Entity> getPopulation() {
		return population;
	}

	/**
	 * Returns the mutation rate.
	 * @return the mutation rate.
	 */
	public double getMutationRate() {
		return mutationRate;
	}
	
	/**
	 * Sets the new mutation rate
	 * @param mutationRate new value for the mutation rate
	 */
	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}
	
	/**
	 * Returns the number of current generation
	 * @return the number of current generation
	 */
	public int getGeneration() {
		return generation;
	}
	
	/**
	 * Creates the initial population of size populationSize form alphabet of this population.
	 * @return list of random entities with size of populationSize and genes from this population's alphabet
	 */
	private List<Entity> initialPopulation() {
		List<Entity> population = new ArrayList<>();
		
		Set<Integer> alreadyChosen = new HashSet<>();
		for(int i=0, populationSize=getPopulationSize(); i<populationSize; i++) {
			List<Town> newDna = new ArrayList<Town>();
			
			for(int j=0, dnaSize = alphabet.getTowns().size(); j<dnaSize; j++) {		
				//pick a random town that has not already been chosen
				int random = rnd.nextInt(alphabet.getTowns().size());
				while(!alreadyChosen.add(random)) random = rnd.nextInt(alphabet.getTowns().size());
				
				newDna.add(alphabet.getTowns().get(random));
			}
			
			alreadyChosen.clear();
			
			//used for testing purposes
			/*for(Town t : newDna) {
				System.out.println("NewX: " + t.getX() + " NewY: " + t.getY());
			}
				
			System.out.println("-----------------------------");*/
			
			population.add(new Entity(new Dna(newDna, alphabet)));
		}
		
		return population;
	}

}
