package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

import gui.login.LoginPanel;

public class Main {

	//Te gjitha komponentet swing duhet te konfigurohen
	//nepermjet event dispatch thread, jo nepermjet main
	//thread, pasi ky thread eshte pergjegjes per te kapur
	//eventet e tilla si ato te mouse dhe per te na njoftuar
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SupermarketFrame frame = new SupermarketFrame();
			//kur duam qe mbyllja e frame te bej stop edhe aplikacionin
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//qe frame te shfaqet duhet qe ta udhezojme
			
			frame.add(new LoginPanel(), BorderLayout.CENTER);
//			frame.getContentPane().add(Component);
			frame.setVisible(true);
			
			//ketu main thread ka mbaruar
		});
	}
}
