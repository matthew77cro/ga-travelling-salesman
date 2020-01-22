package hr.fer.zemris.seminar.s0036507836.travellingSalesman.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;

import hr.fer.zemris.seminar.s0036507836.travellingSalesman.City;

public class Board extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private List<City> cities;
	private final int boardWidth;
	private final int boardHeight;
	
	private AtomicBoolean drawing;
	
	public Board(int boardWidth, int boardHeight) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		this.setPreferredSize(new Dimension(boardWidth, boardHeight));
		this.drawing = new AtomicBoolean(false);
		
		this.setOpaque(true);
		this.setBackground(Color.LIGHT_GRAY);
	}
	
	public List<City> getCities() {
		return cities;
	}
	
	public int getBoardWidth() {
		return boardWidth;
	}
	
	public int getBoardHeight() {
		return boardHeight;
	}
	
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	
	public AtomicBoolean isDrawing() {
		return drawing;
	}
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(cities==null) return;
		
		drawing.set(true);
		
		Graphics2D gr = (Graphics2D) g;
		
		for(int i=0; i<cities.size()-1; i++) {
			gr.drawString(cities.get(i).getName(), cities.get(i).getX(), cities.get(i).getY());
			gr.drawLine(cities.get(i).getX(), cities.get(i).getY(), cities.get(i+1).getX(), cities.get(i+1).getY());
		}
		City last = cities.get(cities.size()-1);
		gr.drawString(last.getName(), last.getX(), last.getY());
		
		drawing.set(false);

    }
    
}