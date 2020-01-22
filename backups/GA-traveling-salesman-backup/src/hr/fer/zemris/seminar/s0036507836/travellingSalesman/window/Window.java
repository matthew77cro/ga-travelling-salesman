package hr.fer.zemris.seminar.s0036507836.travellingSalesman.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import hr.fer.zemris.seminar.s0036507836.genetic.IAlphabet;
import hr.fer.zemris.seminar.s0036507836.genetic.IPopulation;
import hr.fer.zemris.seminar.s0036507836.travellingSalesman.bruteforce.TravelingSalesmanBruteforce;
import hr.fer.zemris.seminar.s0036507836.travellingSalesman.City;
import hr.fer.zemris.seminar.s0036507836.travellingSalesman.genetic.GeneticImpl;

public class Window extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int width;
	private final int height;
	
	private Board b;
	private PopulationEvolver evolver;
	
	private JTextField popSize;
	private JTextField mutationRate;
	private JTextField startTown;
	private JTextField towns;
	private JButton start;
	private JButton stop;
	private JTextArea status;
	private JLabel generationNo;
	private JLabel pathLength;
	private JCheckBox simulate;
	private JRadioButton genetic;
	private JRadioButton bruteForce;
	
	private boolean isBruteForce = false;
	private AtomicBoolean stopped;
	private AtomicBoolean isBruteForceRunning;

	public Window(int width, int height) {
		
		this.width = width;
		this.height = height;
		this.stopped = new AtomicBoolean(false);
		this.isBruteForceRunning = new AtomicBoolean(false);
		
		setTitle("Genetic algorithm - TRAVELING SALESMAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    	
    	b = new Board(width, height);
    	top.add(b);
    	
    	JPanel controls = new JPanel();
    	controls.setLayout(new GridLayout(0,2));
    	
    	ButtonGroup bruteForceToggle = new ButtonGroup();
    	genetic = new JRadioButton("Genetic algorithm");
    	bruteForce = new JRadioButton("Brute force");
    	bruteForceToggle.add(genetic);
    	bruteForceToggle.add(bruteForce);
    	controls.add(genetic);
    	controls.add(bruteForce);
    	genetic.setSelected(true);
    	
    	JLabel lPopSize = new JLabel();
    	lPopSize.setText("Population size : ");
    	controls.add(lPopSize);
    	
    	popSize = new JTextField();
    	popSize.setText("30");
    	controls.add(popSize);
    	
    	JLabel lMutationRate = new JLabel();
    	lMutationRate.setText("Mutation rate : ");
    	controls.add(lMutationRate);
    	
    	mutationRate = new JTextField();
    	mutationRate.setText("0.1");
    	controls.add(mutationRate);
    	
    	JLabel l1 = new JLabel();
    	l1.setText("Start town : ");
    	controls.add(l1);
    	
    	startTown = new JTextField();
    	startTown.setText("(450,100,OS)");
    	controls.add(startTown);
    	    	
    	JLabel l2 = new JLabel();
    	l2.setText("Towns (without start town) : ");
    	controls.add(l2);
    	
    	towns = new JTextField();
    	towns.setText("");
    	controls.add(towns);
    	
    	JLabel l3 = new JLabel();
    	l3.setText("Slow the time : ");
    	controls.add(l3);
    	
    	simulate = new JCheckBox();
    	controls.add(simulate);
    	
    	start = new JButton();
    	start.setText("START");
    	controls.add(start);
    	
    	stop = new JButton();
    	stop.setText("STOP");
    	stop.setEnabled(false);
    	controls.add(stop);
    	
    	generationNo = new JLabel();
    	generationNo.setText("<GenerationNO>");
    	generationNo.setHorizontalAlignment(JLabel.CENTER);
    	controls.add(generationNo);
    	
    	pathLength = new JLabel();
    	pathLength.setText("<LENGTH>");
    	pathLength.setHorizontalAlignment(JLabel.CENTER);
    	controls.add(pathLength);
    	
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
    	this.pack();
        initActions();
        
        towns.setText("(200,80,ZG);(20,150,PU);(250,20,VA);" + 
        		"(440,125,DJ);(50,125,RI);(75,300,ZD);(350,125,PZ);(320,150,NG);(150,450,DU);"
        		+ "(400,150,SB);(125,100,KA);(220,90,VG);"
        		+ "(300,75,Virovitica);(275,50,Koprivnica);(15,120,Porec);"
        		+ "(90,135,OG);(225,125,SI);"
        		+ "(105,375,ST);(430,365,Sarajevo);(280,200,Banja Luka);"
        		+ "(250,250,RandomCity1);(250,450,RandomCity2)"
        		);
        
    }
    
   public void initActions() {
	   
	   	genetic.addActionListener((e) -> {
   			isBruteForce = false;
	   	});
   		bruteForce.addActionListener((e) -> {
   			isBruteForce = true;
   		});
	   
   		start.addActionListener((evt) -> {
        	
    		new Thread(() -> {
	        	
        		int populationSize;
        		double mutation;
        		City startTown;
        		List<City> cities = new ArrayList<City>();
	        	
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
	        		String[] parsed = this.startTown.getText().replaceAll("[(]", "").replaceAll("[)]", "").replaceAll(";", "").split(",");
	        		if(parsed.length!=3) throw new IllegalArgumentException();
	        		int x = Integer.parseInt(parsed[0]);
	        		int y = Integer.parseInt(parsed[1]);
	        		startTown = new City(parsed[2], x, y);
	        		cities.add(startTown);
	        	} catch(Exception e) {
	        		JOptionPane.showMessageDialog(this, "StartTown coordinates not formated correctly!");
	        		return;
	        	}
	        	
	        	try {
	        		
	        		String[] parsed = this.towns.getText().split(";");
	        		
	        		for(String s : parsed) {
	        			String[] str = s.replaceAll("[(]", "").replaceAll("[)]", "").split(",");
	        			if(str.length!=3) throw new IllegalArgumentException();
	        			int x = Integer.parseInt(str[0]);
		        		int y = Integer.parseInt(str[1]);
		        		cities.add(new City(str[2], x, y));
	        		}
	        		
	        	} catch(Exception e) {
	        		JOptionPane.showMessageDialog(this, "Towns coordinates not formated correctly!");
	        		return;
	        	}
	        	
	        	start.setEnabled(false);
	        	stop.setEnabled(true);
	        	genetic.setEnabled(false);
	        	bruteForce.setEnabled(false);
	        	
	        	if(isBruteForce) {
	        		
	        		info("Started brute force method.");
	        		isBruteForceRunning.set(true);
	        		TravelingSalesmanBruteforce tsbf = new TravelingSalesmanBruteforce(cities);
					for(List<City> next : tsbf) {
						if(stopped.get()) break;
						b.setCities(next);
						b.repaint();
						while(b.isDrawing().get());
					}
					isBruteForceRunning.set(false);
					info("Brute force finished");
	        		
	        	} else {
	        		
	        		info("Started genetic algorithm method.");
	        		IPopulation<City> population;
		        	IAlphabet<City> alphabet = new IAlphabet<>(cities);
		        	population = new IPopulation<City>(populationSize, mutation, alphabet, 
		        			GeneticImpl.crossoverFunction, 
		        			GeneticImpl.mutationFunction, 
		        			GeneticImpl.fitnessFunction, 
		        			GeneticImpl.randomDnaGenerator);
		        	b.setCities(population.getBestForCurrentGeneration().getDna().getGenes());
		        	
		        	evolver = new PopulationEvolver(population, b, this);		        	
		        	evolver.run();
		        	
	        	}
	        	
	        	start.setEnabled(true);
	        	stop.setEnabled(false);
	        	genetic.setEnabled(true);
	        	bruteForce.setEnabled(true);
        	
	        }).start();
        	
        });
        
        stop.addActionListener((evt) -> {
        	stop.setEnabled(false);
        	
        	new Thread(() -> {	        	
        		stopped.set(true);
        		if(!isBruteForce)
        			while(evolver.isRunning().get());
        		else
        			while(isBruteForceRunning.get());
	        	info("Algorithm has been stopped.");
	        	start.setEnabled(true);
	        	stopped.set(false);
	        	genetic.setEnabled(true);
	        	bruteForce.setEnabled(true);
        	}).start();;
        });
        
   }
   
   public AtomicBoolean isStopped() {
	   return stopped;
   }
   
   public JLabel getGenerationNo() {
	   return generationNo;
   }

   public JLabel getPathLength() {
	   return pathLength;
   }
   
   public boolean isBruteForce() {
	   return isBruteForce;
   }
    
}