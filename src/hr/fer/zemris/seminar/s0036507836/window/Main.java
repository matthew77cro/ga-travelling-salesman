package hr.fer.zemris.seminar.s0036507836.window;

import javax.swing.SwingUtilities;

/**
 * 
 * Main class from which the application starts.
 * 
 * @author Matija Bačić
 *
 */
public class Main {
	
	/**
	 * With of the window
	 */
	static int width;
	
	/**
	 * Height of the window
	 */
	static int height;
	
	public static void main(String[] args) {
		
		try {
			width = Integer.parseInt(args[0]);
			height = Integer.parseInt(args[1]);
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
				Window w = new Window(width, height);
				w.setLocationRelativeTo(null);
				w.setVisible(true);
			}
		});
	}

}
