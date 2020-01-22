package hr.fer.zemris.seminar.s0036507836.genetic;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class models the alphabet from which the genes are picked. Only genes that are in the instance of
 * this class for one population can be in DNA chain of one entity that belongs to that population.
 * @author Matija Bačić
 *
 */
public class IAlphabet<T> {
	
	/**
	 * Alphabet from which genes are picked.
	 */
	private List<T> genes;
	
	/**
	 * Creates and initialises a new alphabet.
	 * @param genes list from which genes for dna chain are picked
	 * @throws NullPointerException if genes is null
	 * @throws IllegalArgumentException if genes list is empty or genes list contains a null element
	 */
	public IAlphabet(List<T> genes) {
		if(genes==null) throw new NullPointerException("Genes cannot be null!");
		if(genes.size()==0) throw new IllegalArgumentException("There must be at leas one gene in the list!");
		if(genes.contains(null)) throw new IllegalArgumentException("All genes in the list must be initialized! (They must not be null)");
		
		this.genes = Collections.unmodifiableList(genes);
	}
	
	/**
	 * Returns all genes in this alphabet as unmodifiable list
	 * @return all genes in this alphabet as unmodifiable list
	 */
	public List<T> getGenes() {
		return genes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(genes);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof IAlphabet))
			return false;
		IAlphabet other = (IAlphabet) obj;
		return Objects.equals(genes, other.genes);
	}

}
