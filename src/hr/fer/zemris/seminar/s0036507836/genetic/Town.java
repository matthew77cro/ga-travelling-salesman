package hr.fer.zemris.seminar.s0036507836.genetic;

import java.util.Objects;

/**
 * This class models a town for travelling salesman problem
 * @author Matija
 *
 */
public class Town {
	
	private String name;
	private int x;
	private int y;
	
	/**
	 * Creates and initialises a new town
	 * @param name name of the town
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Town(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns town's name
	 * @return town's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns town's x-coordinate
	 * @return town's x-coordinate
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns town's y-coordinate
	 * @return town's y-coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Calculates and returns the distance from this town to the town given in the argument as other
	 * @param other town for which to calculate the distance from this town
	 * @return distance from this town the the town given in the argument as other
	 * @throws NullPointerException if other is <code>null</code>
	 */
	public double distanceTo(Town other) {
		return distance(this, other);
	}
	
	/**
	 * Calculates and returns the distance between t1 and t2
	 * @param t1 first town
	 * @param t2 second town
	 * @return the distance between t1 and t2
	 * @throws NullPointerException if t1 or t2 is null
	 */
	public static double distance(Town t1, Town t2) {
		return Math.sqrt(Math.pow(t1.x-t2.x,2) + Math.pow(t1.y-t2.y,2));
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Town))
			return false;
		Town other = (Town) obj;
		return x == other.x && y == other.y;
	}

}
