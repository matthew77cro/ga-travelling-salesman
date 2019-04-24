package hr.fer.zemris.seminar.s0036507836.genetic;

import java.util.List;
import java.util.Objects;

/**
 * Models a dna chain of one entity. You can think of dna chain as a list of towns in order in which
 * salesman needs to visit them.
 * @author Matija Bačić
 *
 */
public class Dna{
	
	/**
	 * List of towns in order in which salesman needs to visit them. (without the starting town)
	 */
	private List<Town> genes;
	
	/**
	 * Alphabet from which the genes are picked. Here is contained the information of starting town.
	 */
	private Alphabet alphabet;
	
	/**
	 * Creates and initialises a new dna chain. Correct size of genes list is defined as
	 * the size of towns list in the alphabet object without the starting town.
	 * @param genes list of towns in order of visit
	 * @param alphabet alphabet from which the genes are picked
	 * @throws NullPointerException if arguments are null
	 * @throws IllegalArgumentException if genes contain start town or size of the genes list is not correct or list of genes does not contain same towns as list of towns in the alphabet object (i.e. list of genes must be a permutation of the list of towns in the alphabet object)
	 */
	public Dna(List<Town> genes, Alphabet alphabet) {
		if(genes!=null && genes.size()!=alphabet.getTowns().size()) throw new IllegalArgumentException("Genes size must be the same as number of towns to visit!");
		if(genes!=null && genes.contains(alphabet.getStartTown())) throw new IllegalArgumentException("Start town must not be part of genes");
		if(!alphabet.isPermutation(genes)) throw new IllegalArgumentException("Gene list is not a valid permutation!");
		
		this.genes = Objects.requireNonNull(genes);
		this.alphabet = Objects.requireNonNull(alphabet);
	}
	
	/**
	 * Returns genes of the dna (towns in order of visit).
	 * @return genes of the dna
	 */
	public List<Town> getGenes() {
		return genes;
	}
	
	/**
	 * Returns the alphabet of this dna
	 * @return the alphabet of this dna
	 */
	public Alphabet getAlphabet() {
		return alphabet;
	}
	
	/**
	 * Checks if two dna chains are compatible for crossover genetic operation.
	 * They are compatible only if size of chains are same and if they pick
	 * their genes from the same alphabet.
	 * @param obj object for which to check the compatibility
	 * @return true if dna chains are compatible, false otherwise
	 */
	public boolean isCompatible(Object obj) {
		if(!(obj instanceof Dna)) return false;
		
		Dna other = (Dna) obj;
		if(this.getGenes().size()!=other.getGenes().size()) return false;
		if(!this.alphabet.equals(other.alphabet)) return false;
		
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(alphabet, genes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Dna))
			return false;
		Dna other = (Dna) obj;
		return Objects.equals(alphabet, other.alphabet) && Objects.equals(genes, other.genes);
	}

}
