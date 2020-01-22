package hr.fer.zemris.seminar.s0036507836.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import hr.fer.zemris.seminar.s0036507836.genetic.IEntity;

public class Board extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int rows;
	private int cols;
	private int boardWidth;
	private int boardHeight;
	private List<IEntity> population;
	private int goalX;
	private int goalY;
	private static final int sizeOfBoxX = 10;
	private static final int sizeOfBoxY = 10;
	private static final int sizeOfMobX = sizeOfBoxX-2;
	private static final int sizeOfMobY = sizeOfBoxY-2;
	
	private boolean drawing;
	
	public Board(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.boardWidth = cols*sizeOfBoxX;
		this.boardHeight = rows*sizeOfBoxY;
		this.setPreferredSize(new Dimension(boardWidth, boardHeight));
		
		this.setBackground(Color.LIGHT_GRAY);
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	public int getBoardWidth() {
		return boardWidth;
	}
	
	public int getBoardHeight() {
		return boardHeight;
	}

	public static int getSizeofboxX() {
		return sizeOfBoxX;
	}

	public static int getSizeofboxY() {
		return sizeOfBoxY;
	}

	public static int getSizeofmobX() {
		return sizeOfMobX;
	}

	public static int getSizeofmobY() {
		return sizeOfMobY;
	}
	
	public List<IEntity> getPopulation() {
		return population;
	}
	
	public void setPopulation(List<IEntity> population) {
		while(drawing);
		if(this.population==null) this.population = new ArrayList<>();
		this.population.clear();
		this.population.addAll(population);
	}
	
	public int getGoalX() {
		return goalX;
	}
	
	public int getGoalY() {
		return goalY;
	}
	public void setGoalX(int goalX) {
		this.goalX = goalX;
	}
	
	public void setGoalY(int goalY) {
		this.goalY = goalY;
	}
	
	@Override
    public void paintComponent(Graphics g) {
		drawing=true;
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        
        if(population!=null)
        	doDrawing(g2d);
        drawing=false;
    }
    
    private void doDrawing(Graphics2D g2d) {
    	List<IEntity> e = population;
    	
    	drawMob(goalX, goalY, Color.GREEN, g2d);
    	
    	for(IEntity ent : e) {
    		drawMob(ent.getX(), ent.getY(), Color.BLACK, g2d);
    	}
    }
    
    private void drawMob(int x, int y, Color c, Graphics2D g2d) {
    	Paint p = g2d.getPaint();
    	g2d.setPaint(c);
    	g2d.fillRect(sizeOfBoxX*x+1, sizeOfBoxY*y+1, sizeOfMobX, sizeOfMobY);
    	g2d.setPaint(p);
    }
    
}