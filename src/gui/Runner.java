package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import gui.admin.AdminView;
import gui.employee.EmployeeView;
import gui.inventory.InventoryView;
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
			current = new AdminView();
			break;
		case CASHIER:
			current = new Cashier();
			break;
		case EMPLOYEES:
			current = new EmployeeView();
			break;
		case INVENTORY:
			current = new InventoryView();
		}
		current.setVisible(true);	
	}
}
