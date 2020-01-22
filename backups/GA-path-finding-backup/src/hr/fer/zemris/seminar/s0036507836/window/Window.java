package hr.fer.zemris.seminar.s0036507836.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import hr.fer.zemris.seminar.s0036507836.genetic.IEntity;
import hr.fer.zemris.seminar.s0036507836.genetic.IPopulation;
import hr.fer.zemris.seminar.s0036507836.genetic.PopulationCreator;

public class Window extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int rows;
	private final int cols;
	
	private Board b;
	
	private JTextField popSize;
	private JTextField mutationRate;
	private JTextField startX;
	private JTextField startY;
	private JTextField targetX;
	private JTextField targetY;
	private JButton start;
	private JButton stop;
	private JButton simulate;
	private JTextArea status;
	private JLabel generationNo;
	
	private PopulationEvolver evolver;
	private PopulationCreator popCreator;
	private IPopulation population;
	
	private Thread t;

	public Window(int rows, int cols, PopulationCreator popCreator) {
		
		this.rows = rows;
		this.cols = cols;
		this.popCreator = Objects.requireNonNull(popCreator);
		
		setTitle("Genetic algorithms example 3 - path finding");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        init();
    }

	public void info(String msg) {
		Calendar d = Calendar.getInstance();
		String info = "[INFO " + d.get(Calendar.DAY_OF_MONTH) + "." + d.get(Calendar.MONTH) + "." + d.get(Calendar.YEAR) + " " + 
				+ d.get(Calendar.HOUR) + ":" + d.get(Calendar.MINUTE) + ":" + d.get(Calendar.SECOND)  + "] "
				+ msg + "%n";
		status.append(String.format(info));
	}
	
    private void init() {
    	
    	this.setLayout(new BorderLayout());
    	
    	JPanel top = new JPanel(new GridLayout(1,0));
    	
    	b = new Board(rows, cols);
    	top.add(b);
    	
    	JPanel controls = new JPanel();
    	controls.setLayout(new GridLayout(0,2));
    	
    	JLabel lPopSize = new JLabel();
    	lPopSize.setText("Population size : ");
    	controls.add(lPopSize);
    	
    	popSize = new JTextField();
    	popSize.setText("200");
    	controls.add(popSize);
    	
    	JLabel lMutationRate = new JLabel();
    	lMutationRate.setText("Mutation rate : ");
    	controls.add(lMutationRate);
    	
    	mutationRate = new JTextField();
    	mutationRate.setText("0.01");
    	controls.add(mutationRate);
    	
    	JLabel l1 = new JLabel();
    	l1.setText("Start X : ");
    	controls.add(l1);
    	
    	startX = new JTextField();
    	startX.setText("0");
    	controls.add(startX);
    	
    	JLabel l2 = new JLabel();
    	l2.setText("Start Y : ");
    	controls.add(l2);
    	
    	startY = new JTextField();
    	startY.setText("0");
    	controls.add(startY);
    	
    	JLabel l3 = new JLabel();
    	l3.setText("Target X : ");
    	controls.add(l3);
    	
    	targetX = new JTextField();
    	targetX.setText("15");
    	controls.add(targetX);
    	
    	JLabel l4 = new JLabel();
    	l4.setText("Target Y : ");
    	controls.add(l4);
    	
    	targetY = new JTextField();
    	targetY.setText("15");
    	controls.add(targetY);
    	
    	controls.add(new JLabel());
    	controls.add(new JLabel());
    	
    	start = new JButton();
    	start.setText("START");
    	controls.add(start);
    	
    	stop = new JButton();
    	stop.setText("STOP");
    	stop.setEnabled(false);
    	controls.add(stop);
    	
    	simulate = new JButton();
    	simulate.setText("SIMULATE");
    	simulate.setEnabled(false);
    	controls.add(simulate);
    	
    	generationNo = new JLabel();
    	generationNo.setText("<GenerationNO>");
    	generationNo.setHorizontalAlignment(JLabel.CENTER);
    	controls.add(generationNo);
    	
    	top.add(controls);
    	this.add(top, BorderLayout.CENTER);
    	
    	JPanel statusBar = new JPanel(new GridLayout(1,0));
    	statusBar.setPreferredSize(new Dimension(0,70));
    	
    	status = new JTextArea();
    	status.setLineWrap(true);
    	status.setEditable(false);
    	statusBar.add(status);
    	
    	JScrollPane scroll = new JScrollPane (status, 
    			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	statusBar.add(scroll);
    	
    	this.add(statusBar, BorderLayout.SOUTH);

        initActions();
        
        this.pack();
        
    }
    
    public void initActions() {
    	start.addActionListener((evt) -> {
        	
        t = new Thread(() -> {
        	
        	info("Started");
        	
        		int populationSize;
        		double mutation;
        		int startPositionX;
        		int startPositionY;
	        	int finishX;
	        	int finishY;
	        	
	        	try {
	        		populationSize = Integer.parseInt(popSize.getText());
	        	} catch(Exception e) {
	        		JOptionPane.showMessageDialog(this, "Population size must be integer!");
	        		return;
	        	}
	        	
	        	if(populationSize<=0) {
	        		JOptionPane.showMessageDialog(this, "Population size must be greater than 0!");
	        		return;
	        	}
	        	
	        	try {
	        		mutation = Double.parseDouble(mutationRate.getText());
	        	} catch(Exception e) {
	        		JOptionPane.showMessageDialog(this, "MutationRate size must be double!");
	        		return;
	        	}
	        	
	        	if(mutation<0 || mutation>1) {
	        		JOptionPane.showMessageDialog(this, "Mutation rate must be between 0 and 1!");
	        		return;
	        	}
	        	
	        	try {
	        		startPositionX = Integer.parseInt(startX.getText());
	        		startPositionY = Integer.parseInt(startY.getText());
	        	} catch(Exception e) {
	        		JOptionPane.showMessageDialog(this, "StartX and StartY must be integer numbers!");
	        		return;
	        	}
	        	
	        	if(startPositionX<0 || startPositionX>=cols || startPositionY<0 || startPositionY>=rows) {
	        		JOptionPane.showMessageDialog(this, "StartX and StartY must integers and elements of [0,30>!");
	        		return;
	        	}
	        	
	        	try {
		        	finishX = Integer.parseInt(targetX.getText());
		        	finishY = Integer.parseInt(targetY.getText());
	        	} catch(Exception e) {
	        		JOptionPane.showMessageDialog(this, "TargetX and TargetY must be integer numbers!");
	        		return;
	        	}
	        	
	        	if(finishX<0 || finishX>=cols || finishY<0 || finishY>=rows) {
	        		JOptionPane.showMessageDialog(this, "TargetX and TargetY must integers and elements of [0,30>!");
	        		return;
	        	}
	        	
	        	start.setEnabled(false);
	        	stop.setEnabled(true);
	        	simulate.setEnabled(false);
	        	
	        	population = popCreator.createPopulation(populationSize, mutation, rows, cols, finishX, finishY, startPositionX, startPositionY);
	        	b.setPopulation(population.getPopulation());
	        	b.setGoalX(population.getGoalX());
	        	b.setGoalY(population.getGoalY());
	        	evolver = new PopulationEvolver(population, b, generationNo);
	        	info(String.format("Created population of size %d with mutation %f. Goal is (%d,%d)", populationSize, mutation, finishX, finishY));
	        	
	        	evolver.run();
	        	if(evolver.isStopped()) return;
	        	
	        	info(String.format("Entity has been found in %d. generation with fitness %f.", population.getGeneration(), population.getBestForCurrentGeneration().getFitness()));
	        	info("Stopped");
	        	
	        	start.setEnabled(true);
	        	stop.setEnabled(false);
	        	simulate.setEnabled(true);
        	
        	});
        
        t.start();
        	
        });
        
        stop.addActionListener((evt) -> {
        	stop.setEnabled(false);
        	
        	new Thread(() -> {
	        	while(evolver==null);
	        	evolver.stop();
	        	while(t.isAlive());
	        	
	        	info("Algorithm has been interrupted.");
	        	start.setEnabled(true);
        	}).start();;
        });
        
        simulate.addActionListener((evt) -> {
        	start.setEnabled(false);
        	stop.setEnabled(false);
        	simulate.setEnabled(false);
        	info("Simulation has been started.");
        	
        	new Thread(() -> {
        		IEntity best = population.getBestForCurrentGeneration();
        		List<IEntity> l = new ArrayList<>();
        		l.add(best);
        		b.setPopulation(l);
        		best.reset();
        		b.repaint();
        		while(best.getX()!=population.getGoalX() || best.getY()!=population.getGoalY()) {
        			
        			try {
        				Thread.sleep(100);
        			} catch (InterruptedException e) {
        				System.out.println(e.getMessage());
        			}
        			
        			best.step();
        			b.repaint();
        		}
        		
        		start.setEnabled(true);
            	simulate.setEnabled(true);
            	info("Simulation finished.");
        	}).start();
        	
        });
    }
    
}