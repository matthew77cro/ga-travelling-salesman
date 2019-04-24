package hr.fer.zemris.seminar.s0036507836.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import hr.fer.zemris.seminar.s0036507836.genetic.Dna;
import hr.fer.zemris.seminar.s0036507836.genetic.Town;

public class Board extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Dna dna;
	private final int boardWidth;
	private final int boardHeight;
	
	private boolean drawing;
	
	public Board(int boardWidth, int boardHeight) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		this.setPreferredSize(new Dimension(boardWidth, boardHeight));
		
		this.setBackground(Color.LIGHT_GRAY);
	}
	
	public Dna getDna() {
		return dna;
	}
	
	public int getBoardWidth() {
		return boardWidth;
	}
	
	public int getBoardHeight() {
		return boardHeight;
	}
	
	public void setDna(Dna dna) {
		this.dna = dna;
	}
	
	public boolean isDrawing() {
		return drawing;
	}
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(dna==null) return;
		
		drawing = true;
		Graphics2D gr = (Graphics2D) g;
		
		List<Town> towns = dna.getGenes();
		
		Town start = dna.getAlphabet().getStartTown();
		gr.drawString(start.getName(), start.getX(), start.getY());
		gr.drawLine(start.getX(), start.getY(), towns.get(0).getX(), towns.get(0).getY());
		for(int i=0; i<towns.size()-1; i++) {
			gr.drawString(towns.get(i).getName(), towns.get(i).getX(), towns.get(i).getY());
			gr.drawLine(towns.get(i).getX(), towns.get(i).getY(), towns.get(i+1).getX(), towns.get(i+1).getY());
		}
		Town last = towns.get(towns.size()-1);
		gr.drawString(last.getName(), last.getX(), last.getY());
		drawing=false;
    }
    
}