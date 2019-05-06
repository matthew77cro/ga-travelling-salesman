package hr.fer.zemris.seminar.s0036507836.genetic.travellingSalesman.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import hr.fer.zemris.seminar.s0036507836.genetic.IDna;
import hr.fer.zemris.seminar.s0036507836.genetic.travellingSalesman.City;

public class Board extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private IDna<City> dna;
	private final int boardWidth;
	private final int boardHeight;
	
	private boolean drawing;
	
	public Board(int boardWidth, int boardHeight) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		this.setPreferredSize(new Dimension(boardWidth, boardHeight));
		
		this.setBackground(Color.LIGHT_GRAY);
	}
	
	public IDna<City> getDna() {
		return dna;
	}
	
	public int getBoardWidth() {
		return boardWidth;
	}
	
	public int getBoardHeight() {
		return boardHeight;
	}
	
	public void setDna(IDna<City> dna) {
		this.dna = dna;
	}
	
	public boolean isDrawing() {
		return drawing;
	}
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(dna==null) return;
		
		//SEMAPHORE ACQUIRE
		Graphics2D gr = (Graphics2D) g;
		
		List<City> cities = dna.getGenes();
		
		for(int i=0; i<cities.size()-1; i++) {
			gr.drawString(cities.get(i).getName(), cities.get(i).getX(), cities.get(i).getY());
			gr.drawLine(cities.get(i).getX(), cities.get(i).getY(), cities.get(i+1).getX(), cities.get(i+1).getY());
		}
		City last = cities.get(cities.size()-1);
		gr.drawString(last.getName(), last.getX(), last.getY());
		//SEMAPHORE RELEASE
    }
    
}