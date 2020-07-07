package gui.admin;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import gui.Runner;
import util.View;

public class AdminView extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private JButton employeesButton;
	private JButton inventoryButton;

	public AdminView() {
		setTitle("Administrator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(666, 100, 1002, 177);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		ActionListener choiceListener = new AdminChoinceListener();
		employeesButton = new JButton("Employees");
		employeesButton.setBackground(SystemColor.activeCaption);
		employeesButton.setFont(new Font("Tahoma", Font.BOLD, 25));
		employeesButton.addActionListener(choiceListener);
		
		inventoryButton = new JButton("Inventory");
		inventoryButton.setBackground(SystemColor.activeCaption);
		inventoryButton.setFont(new Font("Tahoma", Font.BOLD, 25));
		inventoryButton.addActionListener(choiceListener);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(45)
					.addComponent(employeesButton, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
					.addComponent(inventoryButton, GroupLayout.PREFERRED_SIZE, 443, GroupLayout.PREFERRED_SIZE)
					.addGap(40))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(employeesButton, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
						.addComponent(inventoryButton, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
					.addGap(39))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	class AdminChoinceListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String source = ((JButton) e.getSource()).getText();
			if(source.equals("Employees")) {
				Runner.nextView(View.EMPLOYEES);
			} else {
				Runner.nextView(View.INVENTORY);
			}
		}
	}
}
