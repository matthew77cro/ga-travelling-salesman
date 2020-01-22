package hr.fer.zemris.seminar.s0036507836.genetic;

public enum Genes {
	
	EAST,
	WEST,
	NORTH,
	SOUTH;
	
	private static final Genes[] alphabet = new Genes[] {EAST, WEST, NORTH, SOUTH};
	
	public static Genes[] getAlphabet() {
		return  alphabet;
	}
	
	@Override
	public String toString() {
		if(this == EAST) {
			return "0";
		} else if(this == SOUTH) {
			return "1";
		} else if(this == WEST) {
			return "2";
		} else if(this == NORTH) {
			return "3";
		}
		return "";
	}

}
