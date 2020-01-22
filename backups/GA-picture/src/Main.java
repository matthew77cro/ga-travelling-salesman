import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
		
		BufferedImage img = null, img2 = null;
		try {
			img = ImageIO.read(new File("image.jpg"));
			img2 = ImageIO.read(new File("image.jpg"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		frame.setSize(img.getWidth(), img.getHeight());
		
		for(int i=0; i<img.getHeight(); i++) {
			for(int j=0; j<img.getWidth()-1; j++) {
				if(Math.abs(img.getRGB(j,i)-img.getRGB(j+1,i))>threshold)
					img.setRGB(j, i, Color.black.getRGB());
				else
					img.setRGB(j, i, Color.white.getRGB());
			}
		}
		
		for(int i=0; i<img.getWidth(); i++) {
			for(int j=0; j<img.getHeight()-1; j++) {
				if(Math.abs(img2.getRGB(i,j)-img2.getRGB(i,j+1))>threshold)
					img.setRGB(i, j, Color.black.getRGB());
			}
		}

		JLabel l = new JLabel(new ImageIcon(img));
		frame.add(l);
		frame.setVisible(true);
	}

}
