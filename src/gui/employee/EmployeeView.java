package gui.employee;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import exception.SupermarketException;
import gui.Runner;
import model.User;
import service.UserService;
import util.View;

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
	private JTextField birthday;

	JButton addButton;
	JButton backButton;
	JButton updateButton;
	JButton deleteButton;
	JButton clearButton;

	private JTable employeeTable;
	private EmployeeTableModel employees;
	
	private JRadioButton adminRadioButton;
	private JRadioButton cashierRadioButton;
	
	
	public static void main(String[] args) {
		new EmployeeView().setVisible(true);
	}

	public EmployeeView() {
		setTitle("Employees");
		userService = new UserService();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 544);
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
		birthday = new JTextField();
		birthday.setColumns(10);
		password = new JPasswordField();

		addButton = new JButton("Add");
		addButton.addActionListener(new AddButtonListener());
		backButton = new JButton("Back");
		backButton.addActionListener(new BackButtonListener());
		updateButton = new JButton("Update");
		updateButton.addActionListener(new UpdatedButtonListener());
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteButtonListener());
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ClearButtonListener());

		employees = new EmployeeTableModel(userService.getUsers());
		employeeTable = new JTable();

		employeeTable.setModel(employees);
		employeeTable.setShowVerticalLines(false);
		employeeTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		employeeTable.getSelectionModel().addListSelectionListener(new RowSelectionListener());
		
		cashierRadioButton = new JRadioButton("cashier");
		cashierRadioButton.setBackground(SystemColor.inactiveCaption);
		ActionListener radionListener = new RadioButtonListener();
		cashierRadioButton.addActionListener(radionListener );
		adminRadioButton = new JRadioButton("admin");
		adminRadioButton.setBackground(SystemColor.inactiveCaption);
		adminRadioButton.addActionListener(radionListener);

		setLayout();

	}

	private void clear() {
		firstName.setText("");
		lastname.setText("");
		email.setText("");
		username.setText("");
		password.setText("");
		phone.setText("");
		birthday.setText("");
		adminRadioButton.setSelected(false);
		cashierRadioButton.setSelected(false);
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
		if (!adminRadioButton.isSelected() && !cashierRadioButton.isSelected()) {
			throw new SupermarketException("Employee must have a role");
		}
		if (adminRadioButton.isSelected()) {
			employee.setRoleId(1);
		}else {
			employee.setRoleId(2);
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			employee.setBirthday(new Date(formatter.parse(birthday.getText()).getTime()));
		} catch (ParseException e) {
			throw new SupermarketException("Birthday format is dd/MM/yyyy");
		}
		return employee;
	}

	private void fill(User user) {
		clear();
		firstName.setText(user.getFirstName());
		lastname.setText(user.getLastName());
		username.setText(user.getUsername());
		email.setText(user.getEmail());
		phone.setText(user.getPhone());
		birthday.setText(user.getBirthdayString());
		if(user.isAdmin()) {
			adminRadioButton.setSelected(true);
		} else {
			cashierRadioButton.setSelected(true);
		}
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

	class BackButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Runner.nextView(View.ADMIN);
		}
	}
	
	class RadioButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String source = ((JRadioButton) e.getSource()).getText();
			if (source.equals("cashier")) {
				cashierRadioButton.setSelected(true);
				adminRadioButton.setSelected(false);
			} else {
				cashierRadioButton.setSelected(false);
				adminRadioButton.setSelected(true);
			}
			
		}
		
	}

	private void setLayout() {
		JLabel firstnameLabel = new JLabel("Firstname");
		JLabel lastnameLabel = new JLabel("Lastname");
		JLabel usernameLabel = new JLabel("Username");
		JLabel passwordLabel = new JLabel("Password");
		JLabel phoneLabel = new JLabel("Phone");
		JLabel emailLabel = new JLabel("Email");

		JScrollPane scrollPane = new JScrollPane(employeeTable);
		
		JLabel birtdayLabel = new JLabel("Birtday");

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(30)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(usernameLabel)
									.addComponent(passwordLabel)
									.addComponent(phoneLabel)
									.addComponent(birtdayLabel)
									.addComponent(emailLabel))
								.addComponent(lastnameLabel)
								.addComponent(firstnameLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(email, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
								.addComponent(username, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
								.addComponent(phone, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
								.addGroup(Alignment.TRAILING, gl_contentPane.createParallelGroup(Alignment.LEADING, false)
									.addComponent(lastname)
									.addComponent(firstName)
									.addComponent(adminRadioButton)
									.addComponent(cashierRadioButton, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(clearButton)))
								.addComponent(birthday, Alignment.TRAILING)
								.addComponent(password, Alignment.TRAILING))
							.addGap(31))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(updateButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(deleteButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(backButton, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
							.addGap(28)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 861, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(firstnameLabel)
								.addComponent(firstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lastname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lastnameLabel))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(emailLabel)
								.addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(12)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(usernameLabel)
								.addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(21)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(passwordLabel)
								.addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(21)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(phoneLabel)
								.addComponent(phone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(15)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(birtdayLabel)
									.addGap(24))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(birthday, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)))
							.addComponent(cashierRadioButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(adminRadioButton)
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(addButton)
								.addComponent(clearButton))
							.addGap(13)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(updateButton)
								.addComponent(deleteButton)
								.addComponent(backButton))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
