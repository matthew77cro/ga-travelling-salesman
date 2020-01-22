package hr.fer.zemris.seminar.s0036507836;

import java.util.Arrays;

import hr.fer.zemris.seminar.s0036507836.genetic.Genes;
import hr.fer.zemris.seminar.s0036507836.genetic.IDna;

public class DNA implements IDna{
	
	private final Genes[] genes;
	
	public DNA(Genes[] dna) {
		this.genes = Arrays.copyOf(dna, dna.length);
	}
	
	public Genes[] getGenes() {
		return Arrays.copyOf(genes, genes.length);
	}

}
