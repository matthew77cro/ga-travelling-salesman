package hr.fer.zemris.seminar.s0036507836.window;

import javax.swing.SwingUtilities;

import hr.fer.zemris.seminar.s0036507836.Population;

public class Main {
	
	static int rows;
	static int cols;
	
	public static void main(String[] args) {
		
		try {
			rows = Integer.parseInt(args[0]);
			cols = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex) {
			System.err.println("Incorrect argument format!");
			return;
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.err.println("Two arguments expected!");
			return;
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Window w = new Window(rows, cols, Population::new);
				w.setVisible(true);
			}
		});
	}

}
