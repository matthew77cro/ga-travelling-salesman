package hr.fer.zemris.seminar.s0036507836;

import java.util.Arrays;
import java.util.Random;

public class Entity {
	
	private final DNA dna;
	private int fitness;
	private DNA alphabet;
	
	private static Random rnd = new Random(System.currentTimeMillis());
	
	public Entity(DNA dna, DNA alphabet) {
		this.dna = dna;
		this.alphabet = alphabet;
	}

	public DNA getDna() {
		return dna;
	}
	
	public int getFitness() {
		return fitness;
	}
	
	public DNA getAlphabet() {
		return alphabet;
	}
	
	public static Entity crossBreed(Entity parent1, Entity parent2, double mutationRate) {
		if(parent1==null || parent2==null) throw new NullPointerException();
		if(mutationRate<0 || mutationRate>=1) throw new IllegalArgumentException();
		if(!parent1.isCompatible(parent2)) throw new DnaNotCompatibleException();
		
		int length = parent1.getDna().getGenes().length;
		int[] child = new int[length];
		
		int middle = rnd.nextInt(length);
		
		for(int i=0; i<length; i++) {
			int next;
			if(i<middle) next = parent1.getDna().getGenes()[i];
			else next = parent2.getDna().getGenes()[i];
			child[i]=next;
		}
		
		for(int i=0; i<length; i++)
			if(Math.random()<mutationRate) {
				int replacement = parent1.getAlphabet().getGenes()[rnd.nextInt(parent1.getAlphabet().getGenes().length)];
				child[i]=replacement;
			}
		
		return new Entity(new DNA(child), parent1.getAlphabet());
	}
	
	public void calculateFitness(DNA goal) {
		this.fitness = Entity.fitness(this, goal);
	}
	
	public static int fitness(Entity e, DNA goal) {
		if(e.dna.getGenes().length!=goal.getGenes().length) throw new DnaNotCompatibleException();
		int fitness = 0;
		int length = e.dna.getGenes().length;
		for(int i=0; i<length; i++) {
			if(e.dna.getGenes()[i]==goal.getGenes()[i]) {
				fitness++;
			}
		}
		return fitness;
	}
	
	public boolean isCompatible(Object obj) {
		if(!(obj instanceof Entity)) return false;
		
		Entity other = (Entity) obj;
		if(this.getDna().getGenes().length!=other.getDna().getGenes().length) return false;
		if(!Arrays.equals(this.getAlphabet().getGenes(), other.getAlphabet().getGenes())) return false;
		
		return true;
	}
	
}