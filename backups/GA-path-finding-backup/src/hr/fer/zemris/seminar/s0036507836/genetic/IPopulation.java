package hr.fer.zemris.seminar.s0036507836.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface IPopulation {
	
	static Random rnd = new Random(System.currentTimeMillis());
	
	public void step();
	
	public void nextGeneration();
	
	default IEntity getBestForCurrentGeneration() {
		return getPopulation().get(0);
	}
	
	public void sortPopulationByFitness();
	public int getPopulationSize();
	public List<IEntity> getPopulation();
	public double getMutationRate();
	public void setMutationRate(double mutationRate);
	public int getGeneration();
	public int getGoalX();
	public int getGoalY();
	public int getStartX();
	public int getStartY();
	public int getRows();
	public int getCols();
	public boolean isFinished();
	
	default List<IEntity> initialPopulation(EntityCreator entityCreator, DnaCreator dnaCreator) {
		List<IEntity> population = new ArrayList<>();
		
		Genes[] alphabet = Genes.getAlphabet();
 		int lengthOfAlphabet = alphabet.length;
		
		for(int i=0, populationSize=getPopulationSize(); i<populationSize; i++) {
			int gridSize = getRows() * getCols();
			Genes[] entityValue = new Genes[gridSize];
			
			for(int j=0; j<gridSize; j++) {
				/*int x = j%getRows();
				int y = j/getCols();
				
				if(x>0 && x<getCols()-1 && y>0 && y<getRows()-1) {*/ // mislio sam ograniciti skup rijesenja, malo mu pomoci
				entityValue[j] = alphabet[rnd.nextInt(lengthOfAlphabet)];
			}
			
			population.add(entityCreator.createEntity(dnaCreator.createDNA(entityValue), getStartX(), getStartY(), getRows(), getCols()));
		}
		
		return population;
	}

}
