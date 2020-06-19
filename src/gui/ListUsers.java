package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import repository.entity.User;
import service.UserService;

import javax.swing.ListSelectionModel;

public class ListUsers extends JFrame {
	
	private UserService userService = new UserService();

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListUsers frame = new ListUsers();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	
	public ListUsers() {
		setTitle("List of Users");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(getModel());
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(table, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(table, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private DefaultTableModel getModel() {
		List<User> users = userService.getUsers();
		int numberOfColumns = 5;
		int numberOfRows = users.size();
		
		String[] header = new String[] {
				"NAME", "USERNAME", "EMAIL", "PHONE", "BIRTHDAY"
			};
		
		String[][] data = new String[numberOfColumns][numberOfRows];
		for (int i=0; i<numberOfRows; i++) {
			User user = users.get(i);
			data[i] = new String[]{user.getName(), user.getUsername(), user.getEmail(), user.getPhone(), user.getBirthday().toString()};
			
		}
		return new DefaultTableModel(data, header);
	}
}
