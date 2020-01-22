package hr.fer.zemris.seminar.s0036507836;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.seminar.s0036507836.genetic.IDna;
import hr.fer.zemris.seminar.s0036507836.genetic.IEntity;
import hr.fer.zemris.seminar.s0036507836.genetic.IPopulation;

public class Population implements IPopulation {
	
	private final int populationSize;
	private double mutationRate;
	private int generation;
	private final int rows;
	private final int cols;
	private final int goalX;
	private final int goalY;
	private final int startX;
	private final int startY;
	
	private boolean finished = false;
	
	private List<IEntity> population;
	
	private static Random rnd = new Random(System.currentTimeMillis());
	
	public Population(int populationSize, double mutationRate, int rows, int cols, int goalX, int goalY, int startX, int startY) {
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.rows = rows;
		this.cols = cols;
		this.goalX = goalX;
		this.goalY = goalY;
		this.startX = startX;
		this.startY = startY;
		population = initialPopulation(Entity::new, DNA::new);
	}
	
	public void step() {
		if(finished) throw new IllegalStateException();
		population.forEach((e) -> e.step());
		sortPopulationByFitness();
	}
	
	public void nextGeneration() {
		if(finished) throw new IllegalStateException();
		ArrayList<IEntity> newPopulation = new ArrayList<>();
		
		double fitnessSum = 0;
		
		// choosing which entity is participating in a crossover process is like throwing darts
		// it is more likely to hit one with higher fitness
		double darts[] = new double[populationSize];
		
		int i = 0;
		for(IEntity e : population) {
			if(i==0) darts[i] = e.getFitness();
			else darts[i] = e.getFitness() + darts[i-1];
			i++;
			fitnessSum += e.getFitness();
		}
		
		if(fitnessSum==0) {
			population = initialPopulation(Entity::new, DNA::new);
			generation++;
			return;
		}
		
		for (i=0; i<populationSize; i++) {
			double entity1Code = rnd.nextDouble()*fitnessSum;
			double entity2Code = rnd.nextDouble()*fitnessSum;
			
			IEntity e1, e2;
			int x, y;
			for(x=0;x<darts.length && darts[x]<entity1Code; x++);
			for(y=0;y<darts.length && darts[y]<entity2Code; y++);
			
			e1=population.get(x);
			e2=population.get(y);
			
			newPopulation.add(new Entity(IDna.crossover(e1.getDna(), e2.getDna(), mutationRate, DNA::new), startX, startY, cols, rows));
		}
		
		population = newPopulation;
		sortPopulationByFitness();
		generation++;
	}
	
	public void sortPopulationByFitness() {
		population.forEach((e) -> e.calculateFitness(goalX, goalY));
		population.sort((o1, o2) -> Double.compare(o2.getFitness(), o1.getFitness()));
		if(population.get(0).getX()==goalX && population.get(0).getY()==goalY) {
			finished = true;
		}
	}

	public int getPopulationSize() {
		return populationSize;
	}
	
	public List<IEntity> getPopulation() {
		return population;
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
	
	public int getGoalX() {
		return goalX;
	}
	
	public int getGoalY() {
		return goalY;
	}
	
	@Override
	public int getStartX() {
		return startX;
	}
	
	@Override
	public int getStartY() {
		return startY;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		char board[] = new char[rows*cols];
		
		population.forEach((e) -> {
			if(e.getY()<0 || e.getX()<0) return;
			int position = e.getY()*cols+e.getX();
			if(position>=0 && position<board.length)
				board[position]='X';
		});
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				if(board[i*cols+j]!='X') board[i*cols+j]='O';
				sb.append(board[i*cols+j]);
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
}
