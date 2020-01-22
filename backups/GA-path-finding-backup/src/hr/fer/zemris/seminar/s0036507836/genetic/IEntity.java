package hr.fer.zemris.seminar.s0036507836.genetic;

public interface IEntity {
	
	public void step();
	public IDna getDna();
	public double getFitness();
	public int getX();
	public int getY();
	public void reset();
	public int getStepsTaken();
	public int getStartX();
	public int getStartY();
	public int getRows();
	public int getCols();
	
	public void calculateFitness(int xGoal, int yGoal);
	
	static double fitness(IEntity e, int xGoal, int yGoal) {
		return 1/Math.sqrt(Math.pow(xGoal-e.getX(), 2)+Math.pow(yGoal-e.getY(), 2));
	}

}
