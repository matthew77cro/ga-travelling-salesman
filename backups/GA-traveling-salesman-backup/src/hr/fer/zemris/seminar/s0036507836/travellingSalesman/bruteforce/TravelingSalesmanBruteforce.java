package hr.fer.zemris.seminar.s0036507836.travellingSalesman.bruteforce;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hr.fer.zemris.seminar.s0036507836.travellingSalesman.City;

public class TravelingSalesmanBruteforce implements Iterable<List<City>>{
	
	private List<City> cities;
	
	public TravelingSalesmanBruteforce(List<City> cities) {
		this.cities = cities;
	}

	@Override
	public Iterator<List<City>> iterator() {
		return new CityIterator();
	}
	
	private class CityIterator implements Iterator<List<City>> {
	
		private List<City> cities;
		private List<City> next;
		private int[] indices;
		private int[] stop;
		
		public CityIterator() {
			this.cities = new ArrayList<City>(TravelingSalesmanBruteforce.this.cities);
			this.next = cities;
			this.indices = new int[cities.size()];
			this.stop = new int[cities.size()];
			
			for(int i=0; i<indices.length; i++) {
				indices[i] = i;
				stop[i] = stop.length-1-i;
			}
			
			/*TEST PRINT
			for(int i : indices) {
				System.out.print(i + " ");
			}
			System.out.println();*/
		}

		@Override
		public boolean hasNext() {
			return next!=null;
		}

		//LEXICOGRAPHIC ORDERING
		@Override
		public List<City> next() {
			if(!hasNext()) throw new IllegalStateException();
			
			List<City> permutation = next;
			
			/* ALGORITHM:
			1. Find the largest x such that P[x]<P[x+1].
			   (If there is no such x, P is the last permutation.)
			2. Find the largest y such that P[x]<P[y].
			3. Swap P[x] and P[y].
			4. Reverse P[x+1 .. n].
			*/
			
			int x=-1, y=-1;
			// STEP 1
			for(int i = indices.length-2; i>=0; i--) {
				if(indices[i]<indices[i+1]) {
					x = i;
					break;
				}
			}
			
			// STEP 2
			for(int i = indices.length-1; i>=0; i--) {
				if(indices[x]<indices[i]) {
					y = i;
					break;
				}
			}
			
			// STEP 3
			int holder = indices[x];
			indices[x] = indices[y];
			indices[y] = holder;
			
			// STEP 4
			for(int i = x+1, stop = (indices.length-(x+1))/2+x+1; i<stop; i++) {
				int holder2 = indices[i];
				indices[i] = indices[indices.length-1-(i-(x+1))];
				indices[indices.length-1-(i-(x+1))] = holder2;
			}
			
			if(indices[0]!=0) {
				this.next = null;
			} else {
				List<City> newPermutation = new ArrayList<City>();
				for(int i : indices) {
					newPermutation.add(cities.get(i));
				}
				this.next = newPermutation;
			}
			
			/*TEST PRINT
			for(int i : indices) {
				System.out.print(i + " ");
			}
			System.out.println();*/
			
			return permutation;
		}
		
	}

}
