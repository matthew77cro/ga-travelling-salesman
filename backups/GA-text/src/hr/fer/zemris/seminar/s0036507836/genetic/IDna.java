package hr.fer.zemris.seminar.s0036507836.genetic;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Models a dna chain of one entity.
 * @author Matija Bačić
 *
 */
public class IDna<T>{
	
	/**
	 * List of genes in this dna chain
	 */
	private List<T> genes;
	
	/**
	 * Alphabet from which the genes are picked.
	 */
	private IAlphabet<T> alphabet;
	
	/**
	 * Creates and initialises a new dna chain.
	 * @param genes list of genes in this chain
	 * @param alphabet alphabet from which the genes are picked
	 * @throws NullPointerException if arguments are null
	 * @throws IllegalArgumentException if genes list contain genes that are not part of the alphabet
	 */
	public IDna(List<T> genes, IAlphabet<T> alphabet) {
		if(!isValid(genes, alphabet)) throw new IllegalArgumentException("Gene list is not valid!");
		this.genes = Objects.requireNonNull(genes);
		this.alphabet = Objects.requireNonNull(alphabet);
	}
	
	/**
	 * Checks if all genes in the gene list are contained in given alphabet. If the answer is yes - genes
	 * are valid, otherwise they are not valid.
	 * @param genes genes chain for which to check validity
	 * @param alphabet alphabet of genes
	 * @return true if genes are valid, false otherwise
	 */
	private static <T> boolean isValid(List<T> genes, IAlphabet<T> alphabet) {
		List<T> alphabetList = alphabet.getGenes();
		for(T gene : genes) {
			if(!alphabetList.contains(gene)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns genes of the dna.
	 * @return genes of the dna
	 */
	public List<T> getGenes() {
		return genes;
	}
	
	/**
	 * Returns the alphabet of this dna chain
	 * @return the alphabet of this dna chain
	 */
	public IAlphabet<T> getAlphabet() {
		return alphabet;
	}
	
	/**
	 * Checks if two dna chains are compatible for crossover genetic operation.
	 * They are compatible only if size of chains are same and if they pick
	 * their genes from the same alphabet.
	 * @param obj object for which to check the compatibility
	 * @return true if dna chains are compatible, false otherwise
	 */
	@SuppressWarnings("rawtypes")
	public boolean isCompatible(Object obj) {
		if(!(obj instanceof IDna)) return false;
		
		IDna other = (IDna) obj;
		if(this.getGenes().size()!=other.getGenes().size()) return false;
		if(!this.alphabet.equals(other.alphabet)) return false;
		
		return true;
	}
	
	/**
	 * Crosses two dna chains using crossover function and then mutates child chain using mutation function.
	 * @param dna1 first parent dna chain
	 * @param dna2 second parent dna chain
	 * @param mutationRate rate for mutating the child dna chain
	 * @param crossoverFunction function which crosses over two dna chains
	 * @param mutationFunction function which mutates dna chain with given mutation rate as double
	 * @return new dna chain created as a result of a crossover operation
	 * @throws NullPointerException if dna1 or dna2 is null or either of the functions needed is null
	 * @throws IllegalArgumentException if mutationRate is <0 or >1
	 * @throws DnaNotCompatibleException if dna chains are not compatible for crossover operation (see Dna.isCompatible method)
	 */
	public static <T> IDna<T> crossover(IDna<T> dna1, IDna<T> dna2, double mutationRate, BiFunction<List<T>, List<T>, List<T>> crossoverFunction, BiFunction<IDna<T>, Double, IDna<T>> mutationFunction) {
		if(dna1==null || dna2==null) throw new NullPointerException("Dna must not be null!");
		if(crossoverFunction==null) throw new NullPointerException("Crossover function is null!");
		if(mutationFunction==null) throw new NullPointerException("Mutation function is null!");
		if(mutationRate<0 || mutationRate>=1) throw new IllegalArgumentException("Mutation rate must be between 0 and 1!");
		if(!dna1.isCompatible(dna2)) throw new DnaNotCompatibleException("dna1 and dna2 are not compatible!");
		
		List<T> childChain = crossoverFunction.apply(dna1.getGenes(), dna2.getGenes());
		IDna<T> child = new IDna<T>(childChain, dna1.getAlphabet());
		child = mutationFunction.apply(child, mutationRate);
		
		return child;
	}

	@Override
	public int hashCode() {
		return Objects.hash(alphabet, genes);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof IDna))
			return false;
		IDna other = (IDna) obj;
		return Objects.equals(alphabet, other.alphabet) && Objects.equals(genes, other.genes);
	}

}
