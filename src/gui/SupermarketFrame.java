package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

//Cdo frame qe krijohet ka by default madhesine 0x0px
public class SupermarketFrame extends JFrame {

	private static final long serialVersionUID = 1L;

//	private static final int DEFAULT_WIDTH = 500;
//	private static final int DEFAULT_HEIGHT = 500;

	public SupermarketFrame() {
		
		//Duhen te dim madhesine e ekranit perpara
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		
		this.setSize(screenWidth/2, screenHeight/2);
		
		int x = screenWidth/2-this.getSize().width/2;
		int y = screenHeight/2-this.getSize().height/2;
		this.setLocation(x, y);
		
		this.setTitle("Login");
		
		
		// set frame icon
		// Image img = new ImageIcon("icon.gif").getImage();
		// setIconImage(img);

	}
}
