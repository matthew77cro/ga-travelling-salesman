package hr.fer.zemris.seminar.s0036507836;

import hr.fer.zemris.seminar.s0036507836.genetic.Genes;
import hr.fer.zemris.seminar.s0036507836.genetic.IDna;
import hr.fer.zemris.seminar.s0036507836.genetic.IEntity;

public class Entity implements IEntity{
	
	private final IDna dna;
	private double fitness;
	private int x;
	private int y;
	private int startX;
	private int startY;
	
	private int rows;
	private int cols;
	
	private int stepsTaken;
	
	public Entity(IDna dna, int startX, int startY, int rows, int cols) {
		this.dna = dna;
		this.fitness = -1;
		this.startX = startX;
		this.startY = startY;
		this.rows = rows;
		this.cols = cols;
		x = startX;
		y = startY;
	}
	
	public void step() {
		if(x<0 || x>=cols) return;
		if(y<0 || y>=rows) return;
		
		int position = y*cols+x;
		Genes direction = dna.getGenes()[position];
		
		switch(direction) {
			case EAST:
				x++;
				break;
			case SOUTH:
				y++;
				break;
			case WEST:
				x--;
				break;
			case NORTH:
				y--;
				break;
			default:
				throw new IllegalArgumentException();
		}
		
		stepsTaken++;
	}

	public IDna getDna() {
		return dna;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public int getStartX() {
		return this.startX;
	}
	
	@Override
	public int getStartY() {
		return this.startY;
	}
	
	public void reset() {
		x = startX;
		y = startY;
	}
	
	public int getStepsTaken() {
		return stepsTaken;
	}
	
	public void calculateFitness(int xGoal, int yGoal) {
		double fitness = IEntity.fitness(this, xGoal, yGoal);
		if(this.fitness<fitness)
			this.fitness = fitness;
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public int getCols() {
		return cols;
	}
	
}