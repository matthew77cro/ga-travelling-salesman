package hr.fer.zemris.seminar.s0036507836.genetic;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class models the alphabet from which the genes are picked. Only genes that are in the instance of
 * this class for one population can be in DNA chain of one entity that belongs to that population. Genes
 * are modelled with the class Town, i.e. one gene of a DNA chain of one entity is one Town. 
 * @author Matija Bačić
 *
 */
public class Alphabet {
	
	/**
	 * Alphabet from which genes (towns) are picked.
	 */
	private List<Town> towns;
	
	/**
	 * Town from which salesman starts his journey
	 */
	private Town startTown;
	
	/**
	 * Creates and initialises a new alphabet. startTown must not be contained in the list
	 * of towns which salesman needs to visit.
	 * @param towns towns which salesman needs to visit
	 * @param startTown town from which salesman starts his journey
	 * @throws NullPointerException if towns or startTown is null
	 * @throws IllegalArgumentException if towns list is empty or towns list contains startTown or towns list contains a null element
	 */
	public Alphabet(List<Town> towns, Town startTown) {
		if(towns==null || startTown==null) throw new NullPointerException("Arguments cannot be null!");
		if(towns.size()==0) throw new IllegalArgumentException("There must be at leas one town to visit!");
		if(towns.contains(startTown)) throw new IllegalArgumentException("Start town must not be in the list of towns to visit!");
		if(towns.contains(null)) throw new IllegalArgumentException("All towns in the list must be initialized! (They must not be null)");
		
		this.towns = Collections.unmodifiableList(towns);
		this.startTown = startTown;
	}
	
	/**
	 * Returns towns which salesman needs to visit without the starting town
	 * @return towns which salesman needs to visit
	 */
	public List<Town> getTowns() {
		return towns;
	}
	
	/**
	 * Returns the town from which salesman starts his journey
	 * @return the town from which salesman starts his journey
	 */
	public Town getStartTown() {
		return startTown;
	}
	
	/**
	 * Checks whether a given list of towns is a valid permutation of list of towns in this alphabet
	 * @return true if it is a valid permutation, false otherwise
	 */
	public boolean isPermutation(List<Town> towns) {
		if(towns.size()!=this.towns.size()) return false;
		
		for(Town t : towns) {
			if(!this.towns.contains(t)) return false;
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(startTown, towns);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Alphabet))
			return false;
		Alphabet other = (Alphabet) obj;
		return Objects.equals(startTown, other.startTown) && Objects.equals(towns, other.towns);
	}

}
