package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.login.LoginView;

import util.Components;
import util.Flow;

public class SupermarketApplication {

	private static final long serialVersionUID = 1L;
	private static JFrame current;


	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			
			current = new LoginView();
			current.setVisible(true);
			

		});
	}

	public static void nextView(Flow nextView) {
		current.dispose();
		switch (nextView) {
		case LOGIN:
			current = new LoginView();
			break;
		case ADMIN:
			current = new Admin();
			break;
		case CASHIER:
			current = new Cashier();
		}
	
		current.setVisible(true);	
	}

}
