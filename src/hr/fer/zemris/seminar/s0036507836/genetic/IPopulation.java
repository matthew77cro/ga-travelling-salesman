package hr.fer.zemris.seminar.s0036507836.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This class model a population of entities of a genetic algorithm.
 * @author Matija Bačić
 *
 */
public class IPopulation<T> {
	
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
	private final IAlphabet<T> alphabet;
	
	/**
	 * List of entities in this population
	 */
	private List<IEntity<T>> population;
	
	/**
	 * Crossover function
	 */
	private BiFunction<List<T>, List<T>, List<T>> crossoverFunction;
	
	/**
	 * Mutation function
	 */
	private BiFunction<IDna<T>, Double, IDna<T>> mutationFunction;

	/**
	 * Function for calculating fitness of an entity
	 */
	private Function<IEntity<T>, Double> fitnessFunction;

	/**
	 * Generating random dna for initial population
	 */
	private Function<IAlphabet<T>, IDna<T>> randomDnaGenerator;
	
	/**
	 * Creates and initialises a new population.
	 * @param populationSize size of the population (number of entities)
	 * @param mutationRate mutation rate from generation to generation
	 * @param alphabet alphabet from which the genes are picked for each entity
	 * @param crossoverFunction function used for cross over operation
	 * @param mutationFunction function used for mutation operation
	 * @param fitnessFunction function used for calculating the fitness of an entity
	 * @param randomDnaGenerator supplier used for generating random dna; all genes in dna chains generated with this supplier must be present in the alphabet 
	 * @throws NullPointerException if alphabet is <code>null</code>
	 * @throws IllegalArgumentException if some genes generated with reandomDnaGenerator are not in the alphabet
	 */
	public IPopulation(int populationSize, double mutationRate, IAlphabet<T> alphabet, 
			BiFunction<List<T>, List<T>, List<T>> crossoverFunction, 
			BiFunction<IDna<T>, Double, IDna<T>> mutationFunction,
			Function<IEntity<T>, Double> fitnessFunction,
			Function<IAlphabet<T>, IDna<T>> randomDnaGenerator) {
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.alphabet = Objects.requireNonNull(alphabet);
		this.crossoverFunction = crossoverFunction;
		this.mutationFunction = mutationFunction;
		this.fitnessFunction = fitnessFunction;
		this.randomDnaGenerator = randomDnaGenerator;
		this.generation = 0;
		
		population = initialPopulation();
		sortPopulationByFitness();
	}

	/**
	 * Advances the population to the next by crossing over best entities from current generation. Next
	 * generation has the same number of entities as current population.
	 */
	public void nextGeneration() {
		ArrayList<IEntity<T>> newPopulation = new ArrayList<>();
		
		double fitnessSum = 0;
		
		// choosing which entity is participating in a crossover process is like throwing darts
		// it is more likely to hit one with higher fitness
		
		int tenPercentPopulation = (int) (0.1*populationSize);
		double darts[] = new double[tenPercentPopulation];
		for(int i=0; i<tenPercentPopulation; i++) {
			IEntity<T> e = population.get(i);
			if(i==0) darts[i] = e.getFitness();
			else darts[i] = e.getFitness() + darts[i-1];
			i++;
			fitnessSum += e.getFitness();
		}
		
		for (int i=0; i<populationSize; i++) {
			double entity1Code = rnd.nextDouble()*fitnessSum;
			double entity2Code = rnd.nextDouble()*fitnessSum;
			
			int x, y;
			for(x=0;x<darts.length-1 && darts[x]<entity1Code; x++);
			for(y=0;y<darts.length-1 && darts[y]<entity2Code; y++);
			
			//ILI OVAKO
//			for(x=0;x<darts.length-1 && darts[x+1]>entity1Code; x++);
//			for(y=0;y<darts.length-1 && darts[y+1]>entity2Code; y++);
			
			IEntity<T> e1=population.get(x);
			IEntity<T> e2=population.get(y);
			
			newPopulation.add(new IEntity<T>(IDna.crossover(e1.getDna(), e2.getDna(), mutationRate, crossoverFunction, mutationFunction)));
		}
		
		population = newPopulation;
		sortPopulationByFitness();
		generation++;
	}
	
	/**
	 * Returns entity with best fitness value from the current generation.
	 * @return entity with best fitness value from the current generation.
	 */
	public IEntity<T> getBestForCurrentGeneration() {
		return population.get(0);
	}
	
	/**
	 * Sorts population list by fitness value descending.
	 */
	public void sortPopulationByFitness() {
		population.forEach((entity) -> entity.calculateFitness(fitnessFunction));
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
	public List<IEntity<T>> getPopulation() {
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
	 * Returns the alphabet for generating dna chains
	 * @return the alphabet for generating dna chains
	 */
	public IAlphabet<T> getAlphabet() {
		return alphabet;
	}
	
	/**
	 * Returns the function used for cross over operation
	 * @return the function used for cross over operation
	 */
	public BiFunction<List<T>, List<T>, List<T>> getCrossoverFunction() {
		return crossoverFunction;
	}
	
	/**
	 * Returns the function used for calculating fitness of an entity
	 * @return the function used for calculating fitness of an entity
	 */
	public Function<IEntity<T>, Double> getFitnessFunction() {
		return fitnessFunction;
	}
	
	/**
	 * Returns the function used for mutation operation
	 * @return the function used for mutation operation
	 */
	public BiFunction<IDna<T>, Double, IDna<T>> getMutationFunction() {
		return mutationFunction;
	}
	
	/**
	 * Returns the supplier used for generating random dna
	 * @return the function used for generating random dna
	 */
	public Function<IAlphabet<T>, IDna<T>> getRandomDnaGenerator() {
		return randomDnaGenerator;
	}
	
	/**
	 * Creates the initial population of size populationSize using random population generator
	 * @return list of random entities with size of populationSize and genes from random population generator
	 */
	private List<IEntity<T>> initialPopulation() {
		List<IEntity<T>> population = new ArrayList<>();
		
		for(int i=0, populationSize=getPopulationSize(); i<populationSize; i++) {
			IDna<T> newDna = randomDnaGenerator.apply(alphabet);
			population.add(new IEntity<T>(newDna));
		}
		
		return population;
	}

}
