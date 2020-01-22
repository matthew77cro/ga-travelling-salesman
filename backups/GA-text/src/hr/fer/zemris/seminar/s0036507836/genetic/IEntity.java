package hr.fer.zemris.seminar.s0036507836.genetic;

import java.util.Objects;
import java.util.function.Function;

/**
 * This class models an entity of a genetic algorithm.
 * @author Matija Bačić
 *
 */
public class IEntity<T> {
	
	/**
	 * Entity's dna chain
	 */
	private IDna<T> dna;
	
	/**
	 * Entity's fitness
	 */
	private double fitness;
	
	/**
	 * Creates and initialises a new entity. Initial fitness is set to -1.
	 * @param dna dna chain of the entity
	 * @throws NullPointerException if dna is null
	 */
	public IEntity(IDna<T> dna) {
		this.dna = Objects.requireNonNull(dna);
		this.fitness = -1;
	}
	
	
	/**
	 * Returns dna chain of the entity
	 * @return dna chain of the entity
	 */
	public IDna<T> getDna() {
		return dna;
	}
	
	/**
	 * Returns fitness of the entity. Initial fitness is set to -1.
	 * @return fitness of the entity
	 */
	public double getFitness() {
		return fitness;
	}
	
	/**
	 * Sets the fitness of the entity to the one calculated by the given fitness function.
	 * Higher fitness should mean better entity (higher chance to survive and get to the next generation).
	 * @throws NullPointerException if fitnessFunction is null
	 */
	public void calculateFitness(Function<IEntity<T>, Double> fitnessFunction) {
		
		if(fitnessFunction == null) throw new NullPointerException("Fitness function is null");
		this.fitness = fitnessFunction.apply(this);
		
	}
	
	

}
