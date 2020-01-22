package hr.fer.zemris.seminar.s0036507836.genetic.text.window;

import javax.swing.SwingUtilities;

public class Main {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Window w = new Window();
				w.setLocationRelativeTo(null);
				w.setVisible(true);
			}
		});
	}

}
