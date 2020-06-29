package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.login.LoginPanel;
import util.Components;
import util.Flow;

public class SupermarketFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static SupermarketFrame supermarket;

	public SupermarketFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(Components.LOGIN_COMPONENT);

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		this.setSize(screenWidth / 2, screenHeight / 2);

		int x = screenWidth / 2 - this.getSize().width / 2;
		int y = screenHeight / 2 - this.getSize().height / 2;
		this.setLocation(x, y);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			supermarket = new SupermarketFrame();
			supermarket.setVisible(true);	
			
			SupermarketFrame.nextView(Flow.LOGIN);
		});
	}

	public static void nextView(Flow next) {
			JPanel nextPanel = next(next);
			supermarket.getContentPane().removeAll();
			supermarket.getContentPane().add(nextPanel);
			supermarket.invalidate();
			supermarket.validate();
	}

	private static JPanel next(Flow nextView) {
		JPanel component = null;
		switch (nextView) {
		case LOGIN:
			component = new LoginPanel();
			break;
		case ADMIN:
			component = new JPanel();
			JLabel label = new JLabel();
			label.setText("Password :");
			component.add(label);
			break;

		}
		return component;
	}

}
