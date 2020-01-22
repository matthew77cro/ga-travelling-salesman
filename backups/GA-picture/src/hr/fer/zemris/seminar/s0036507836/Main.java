package hr.fer.zemris.seminar.s0036507836;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {

	public static int threshold = 1_000_000;
	
	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		
		BufferedImage original = null, blackAndWhite = null;
		try {
			original = ImageIO.read(new File("image4.jpg"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		frame.setSize(original.getWidth(), original.getHeight());
		blackAndWhite = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		for(int i=0; i<original.getHeight(); i++) {
			for(int j=0; j<original.getWidth()-1; j++) {
				if(Math.abs(original.getRGB(j,i)-original.getRGB(j+1,i))>threshold)
					blackAndWhite.setRGB(j, i, Color.black.getRGB());
				else
					blackAndWhite.setRGB(j, i, Color.white.getRGB());
			}
		}
		
		for(int i=0; i<original.getWidth(); i++) {
			for(int j=0; j<original.getHeight()-1; j++) {
				if(Math.abs(original.getRGB(i,j)-original.getRGB(i,j+1))>threshold)
					blackAndWhite.setRGB(i, j, Color.black.getRGB());
			}
		}

		JLabel l = new JLabel();
		frame.add(l);
		frame.setVisible(true);
		
		int[] target = blackAndWhite.getRGB(0, 0, blackAndWhite.getWidth(), blackAndWhite.getHeight(), null, 0, blackAndWhite.getWidth());
		
		Population p = new Population(10, 0.01, new DNA(target), 
				new DNA(new int[] {0,-1}));
		
		BufferedImage im = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
		im.setRGB(0, 0, im.getWidth(), im.getHeight(), p.getPopulation().get(0).getDna().getGenes(), 0, im.getWidth());
		l.setIcon(new ImageIcon(im));
		
		do {
			p.nextGeneration();
			BufferedImage img = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
			img.setRGB(0, 0, img.getWidth(), img.getHeight(), p.getPopulation().get(0).getDna().getGenes(), 0, img.getWidth());
			l.setIcon(new ImageIcon(img));
		} while(!Arrays.equals(target, p.getPopulation().get(0).getDna().getGenes()));
		
	}
	
}
