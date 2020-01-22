package hr.fer.zemris.seminar.s0036507836;

import java.util.Arrays;

public class DNA {
	
	private final int[] genes;
	
	public DNA(int[] dna) {
		this.genes = Arrays.copyOf(dna, dna.length);
	}
	
	public int[] getGenes() {
		return Arrays.copyOf(genes, genes.length);
	}

}
