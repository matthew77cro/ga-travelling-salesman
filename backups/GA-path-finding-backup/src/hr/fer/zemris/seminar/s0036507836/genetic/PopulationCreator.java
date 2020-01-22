package hr.fer.zemris.seminar.s0036507836.genetic;

public interface PopulationCreator {

	public IPopulation createPopulation(int populationSize, double mutationRate, int rows, int cols, int goalX, int goalY, int startX, int startY);
	
}
