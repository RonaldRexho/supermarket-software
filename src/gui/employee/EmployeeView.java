package gui.employee;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import exception.SupermarketException;
import model.User;
import service.UserService;

public class EmployeeView extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	
	private JPanel contentPane;
	
	private JTextField firstName;
	private JTextField lastname;
	private JTextField username;
	private JPasswordField password;
	private JTextField phone;
	private JTextField email;
	
	private JLabel firstnameLabel;
	private JLabel lastnameLabel;
	private JLabel passwordLabel;
	private JLabel phoneLabel;
	private JLabel usernameLabel;
	private JLabel emailLabel;
	
	private JTable employeeTable;
	private EmployeeTableModel employees;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeView frame = new EmployeeView();
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
	public EmployeeView() {
		userService = new UserService();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 458);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		firstName = new JTextField();
		firstName.setColumns(10);
		lastname = new JTextField();
		lastname.setColumns(10);
		username = new JTextField();
		username.setColumns(10);
		phone = new JTextField();
		phone.setColumns(10);
		email = new JTextField();
		email.setColumns(10);

		JButton addButton = new JButton("Add");
		addButton.setBackground(SystemColor.activeCaption);
		addButton.addActionListener(new AddButtonListener());
		JButton backButton = new JButton("Back");
		backButton.setBackground(SystemColor.activeCaption);
		backButton.addActionListener(null);	
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new UpdatedButtonListener());
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteButtonListener());
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ClearButtonListener());


		firstnameLabel = new JLabel("Firstname");
		lastnameLabel = new JLabel("Lastname");
		usernameLabel = new JLabel("Username");
		passwordLabel = new JLabel("Password");
		password = new JPasswordField();
		phoneLabel = new JLabel("Phone");
		emailLabel = new JLabel("Email");
		
		
		employees = new EmployeeTableModel(userService.getUsers());
		employeeTable = new JTable();
		
		employeeTable.setModel(employees);
		employeeTable.setShowVerticalLines(false);
		employeeTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		employeeTable.getSelectionModel().addListSelectionListener(new RowSelectionListener());


		JScrollPane scrollPane = new JScrollPane(employeeTable);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(15)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(firstnameLabel)
								.addComponent(lastnameLabel)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addComponent(usernameLabel)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(phoneLabel)
										.addComponent(passwordLabel)))
								.addComponent(emailLabel))
							.addGap(57)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(username)
								.addComponent(lastname)
								.addComponent(firstName)
								.addComponent(password)
								.addComponent(phone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(90)
							.addComponent(addButton)
							.addGap(53)
							.addComponent(backButton))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(updateButton)
							.addGap(18)
							.addComponent(deleteButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(clearButton)))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 861, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(46, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(26)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(firstnameLabel)
								.addComponent(firstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lastnameLabel)
								.addComponent(lastname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(12)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(emailLabel))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(usernameLabel)
								.addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(passwordLabel)
								.addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(phoneLabel)
								.addComponent(phone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(27)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(addButton)
								.addComponent(backButton))
							.addGap(41)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(updateButton)
								.addComponent(deleteButton)
								.addComponent(clearButton)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(36)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 324, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(49, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	private void clear() {
		firstName.setText("");
		lastname.setText("");
		email.setText("");
		username.setText("");
		password.setText("");
		phone.setText("");
	}

	private void showMessage(String message, String title, int type) {
		JOptionPane.showMessageDialog(this, message, title, type);
	}

	private User createUserFromFields() {
		User employee = new User();
		employee.setFirstName(firstName.getText());
		employee.setLastName(lastname.getText());
		employee.setUsername(username.getText());
		employee.setEmail(email.getText());
		employee.setPhone(phone.getText());
		employee.setPassword(password.getPassword());
		return employee;
	}

	private void fill(User user) {
		firstName.setText(user.getFirstName());
		lastname.setText(user.getLastName());
		username.setText(user.getUsername());
		email.setText(user.getEmail());
		phone.setText(user.getPhone());
	}
	
	class AddButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				User employee = createUserFromFields();
				userService.insert(employee);
				employees.add(employee);
				clear();
				showMessage("Inserted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch (SupermarketException ex) {
				showMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class UpdatedButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				User employee = createUserFromFields();
				userService.update(employee);
				employees.update(employee);
				clear();
				showMessage("Updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch (SupermarketException ex) {
				showMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class DeleteButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				userService.delete(username.getText());
				employees.remove(username.getText());
				clear();
				showMessage("Removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch (SupermarketException ex) {
				showMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

			}
		}
	}

	class ClearButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			clear();
		}
	}

	class RowSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int row = employeeTable.getSelectedRow();
			if (row >= 0) {
				User employee = employees.get(row);
				fill(employee);
			}
		}
	}
}
