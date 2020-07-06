package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import gui.login.LoginView;
import util.View;

public class Runner {

	private static JFrame current;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {	
			current = new LoginView();
			current.setVisible(true);
		});
	}

	public static void nextView(View nextView) {
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
			break;
		}
		current.setVisible(true);	
	}
}
