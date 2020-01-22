package hr.fer.zemris.seminar.s0036507836.genetic;

import java.util.Random;

public interface IDna {
	
	static Random rnd = new Random(System.currentTimeMillis());

	public Genes[] getGenes();
	
	static IDna crossover(IDna dna1, IDna dna2, double mutationRate, DnaCreator dnaCreator) {
		if(dna1==null || dna2==null) throw new NullPointerException();
		if(mutationRate<0 || mutationRate>=1) throw new IllegalArgumentException();
		if(!dna1.isCompatible(dna2)) throw new DnaNotCompatibleException();
		
		int length = dna1.getGenes().length;
		Genes[] child = new Genes[length];
		
		int middle = rnd.nextInt(length);
		
		for(int i=0; i<length; i++) {
			Genes next;
			if(i<middle) next = dna1.getGenes()[i];
			else next = dna2.getGenes()[i];
			child[i]=next;
		}
		
		Genes[] alphabet = Genes.getAlphabet();
		for(int i=0; i<length; i++)
			if(Math.random()<mutationRate) {
				Genes replacement = alphabet[rnd.nextInt(alphabet.length)];
				child[i]=replacement;
			}
		
		return dnaCreator.createDNA(child);
	}
	
	default boolean isCompatible(Object obj) {
		if(!(obj instanceof IDna)) return false;
		
		IDna other = (IDna) obj;
		if(this.getGenes().length!=other.getGenes().length) return false;
		
		return true;
	}

}
