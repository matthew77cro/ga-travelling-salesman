package hr.fer.zemris.seminar.s0036507836;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
	
	private final int populationSize;
	private double mutationRate;
	private final DNA goal;
	private final int goalSize;
	private int generation;
	private final DNA alphabet;
	
	private ArrayList<Entity> population;
	
	private Random rnd = new Random(System.currentTimeMillis());
	
	public Population(int populationSize, double mutationRate, DNA goal, DNA alphabet) {
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.goal = goal;
		this.goalSize = goal.getGenes().length;
		this.generation = 0;
		this.alphabet = alphabet;
		fillRandomEnitites();
	}

	private void fillRandomEnitites() {
		population = new ArrayList<Entity>();
		int lengthOfAlphabet = alphabet.getGenes().length;
		
		for(int i=0; i<populationSize; i++) {
			int[] entityValue = new int[goalSize];
			
			for(int j=0; j<goalSize; j++) {
				entityValue[j] = alphabet.getGenes()[rnd.nextInt(lengthOfAlphabet)];
			}
			
			population.add(new Entity(new DNA(entityValue), alphabet));
		}
		
		sortPopulationByFitness();
	}
	
	public void nextGeneration() {
		ArrayList<Entity> newPopulation = new ArrayList<Entity>();
		
		int fitnessSum = 0;
		
		// choosing which entity is participating in a crossover process is like throwing darts
		// it is more likely to hit one with higher fitness
		int darts[] = new int[populationSize];
		
		int i = 0;
		for(Entity e : population) {
			if(i==0) darts[i] = e.getFitness();
			else darts[i] = e.getFitness() + darts[i-1];
			i++;
			fitnessSum += e.getFitness();
		}
		
		if(fitnessSum<=0) {
			fillRandomEnitites();
			generation++;
			return;
		}
		
		for (i=0; i<populationSize; i++) {
			int entity1Code = rnd.nextInt(fitnessSum); // +1 because upper bound is exclusive
			int entity2Code = rnd.nextInt(fitnessSum); // +1 because upper bound is exclusive
			
			Entity e1, e2;
			int x, y;
			for(x=0; darts[x]<entity1Code && x<darts.length; x++);
			for(y=0; darts[y]<entity2Code && y<darts.length; y++);
			
			e1=population.get(x);
			e2=population.get(y);
			
			newPopulation.add(Entity.crossBreed(e1, e2, mutationRate));
		}
		
		population = newPopulation;
		sortPopulationByFitness();
		generation++;
	}
	
	private void sortPopulationByFitness() {
		int i = 0;
		for(Entity e : population) {
			e.calculateFitness(goal);
			i++;
			System.out.println(i);
		}
		population.sort((o1, o2) -> { 
			System.out.println("Yo");
			return Integer.compare(o2.getFitness(), o1.getFitness());
		});
	}
	
	public Entity getBestForCurrentGeneration() {
		return population.get(0);
	}

	public int getPopulationSize() {
		return populationSize;
	}
	
	public List<Entity> getPopulation() {
		return population;
	}

	public DNA getGoal() {
		return goal;
	}

	public int getGoalSize() {
		return goalSize;
	}

	public double getMutationRate() {
		return mutationRate;
	}
	
	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}
	
	public int getGeneration() {
		return generation;
	}
	
}
