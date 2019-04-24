package hr.fer.zemris.seminar.s0036507836.genetic;

import java.util.List;
import java.util.Objects;

/**
 * This class models an entity of a genetic algorithm for travelling salesman problem.
 * @author Matija Bačić
 *
 */
public class Entity {
	
	/**
	 * Entity's dna chain
	 */
	private Dna dna;
	
	/**
	 * Entity's fitness
	 */
	private double fitness;
	
	/**
	 * Creates and initialises a new entity
	 * @param dna dna chain of the entity
	 * @throws NullPointerException if dna is null
	 */
	public Entity(Dna dna) {
		this.dna = Objects.requireNonNull(dna);
		calculateFitness();
	}
	
	
	/**
	 * Returns dna chain of the entity
	 * @return dna chain of the entity
	 */
	public Dna getDna() {
		return dna;
	}
	
	/**
	 * Returns fitness of the entity
	 * @return fitness of the entity
	 */
	public double getFitness() {
		return fitness;
	}
	
	/**
	 * Calculates the fitness of the entity as reciprocal value of sum of distances between
	 * all towns salesman visits (including starting town). Better fitness means better entity.
	 */
	private void calculateFitness() {
		
		double fitness = 0;
		List<Town> genes = dna.getGenes();
		fitness += dna.getAlphabet().getStartTown().distanceTo(genes.get(0));
		for(int i=0, stop=genes.size()-1; i<stop; i++) {
			fitness += genes.get(i).distanceTo(genes.get(i+1));
		}
		this.fitness = 1.0/fitness;
		
	}
	
	

}
