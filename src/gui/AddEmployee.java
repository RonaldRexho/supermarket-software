package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Product;
import model.Role;
import model.User;
import service.UserService;
import util.DBUtil;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class AddEmployee extends JFrame {

	private JPanel contentPane;
	private JTextField firstName;
	private JTextField lastname;
	private JTextField username;
	private JPasswordField password;
	private JTextField phone;
	private JList list;
	private UserService userService;
	private DefaultListModel model;
	private JTextField email;
	int currentRow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddEmployee frame = new AddEmployee();
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
	public AddEmployee() {
		userService = new UserService();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 458);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("First Name");

		JLabel lblNewLabel_1 = new JLabel("Last Name");

		JLabel lblNewLabel_3 = new JLabel("Username");

		firstName = new JTextField();
		firstName.setColumns(10);

		lastname = new JTextField();
		lastname.setColumns(10);

		username = new JTextField();
		username.setColumns(10);

		JButton btnNewButton = new JButton("Add");
		btnNewButton.setBackground(SystemColor.activeCaption);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = new User();
				user.setFirstName(firstName.getText());
				user.setLastName(lastname.getText());
				user.setUsername(username.getText());
				user.setEmail(email.getText());
				user.setPassword(password.getText());
				user.setPhone(phone.getText());
				try {
					userService.insert(user);
					model.addElement(user);
					clear();
					showMessage("User Inserted");
				} catch (RuntimeException e2) {
					showErrorMessage(e2.getMessage());
				}

			}

		});

		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.setBackground(SystemColor.activeCaption);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Employees().setVisible(true);
				dispose();
			}
		});

		JLabel lblNewLabel_4 = new JLabel("Password");

		password = new JPasswordField();

		JLabel lblNewLabel_5 = new JLabel("Phone");

		phone = new JTextField();
		phone.setColumns(10);

		list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				JList theList = (JList) e.getSource();

				int index = theList.locationToIndex(e.getPoint());
				if (index >= 0) {
					User o = (User) theList.getModel().getElementAt(index);
					fill(o);

				}

			}
		});
		model = new DefaultListModel();
		List<User> products = userService.getUsers();
		model.addAll(products);
		list.setModel(model);

		list.setBorder(UIManager.getBorder("ScrollPane.border"));
		list.setBackground(SystemColor.menu);
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 2) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						Object o = theList.getModel().getElementAt(index);
						System.out.println("Double-clicked on: " + o.toString());
					}
				}
			}
		};
		list.addMouseListener(mouseListener);

		JButton updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User usr = (User)model.get(currentRow);
				User user = new User();
				user.setFirstName(firstName.getText());
				user.setLastName(lastname.getText());
				user.setUsername(username.getText());
				user.setEmail(email.getText());
				user.setPhone(phone.getText());
				user.setPassword(password.getText());
				try {
					userService.update(user);
					model.remove(currentRow);
					model.add(currentRow, user);
					list.repaint();
					showMessage("User inserted successfully");
					clear();
				} catch (RuntimeException e2) {
					showErrorMessage(e2.getMessage());
				}

			}
		});

		JButton deletBtn = new JButton("Delete");
		deletBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key = username.getText();
				try {
					userService.delete(key);
					model.remove(currentRow);
					list.repaint();
					showMessage("User Deleted");
					clear();
				} catch (Exception e2) {
					showErrorMessage(e2.getMessage());
				}
			}
		});

		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});

		email = new JTextField();
		email.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Email");

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(15)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel).addComponent(lblNewLabel_1)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addComponent(lblNewLabel_3)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
														.addComponent(lblNewLabel_5).addComponent(lblNewLabel_4)))
										.addComponent(lblNewLabel_2))
								.addGap(57)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(username).addComponent(lastname).addComponent(firstName)
										.addComponent(password)
										.addComponent(phone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(90).addComponent(btnNewButton)
								.addGap(53).addComponent(btnNewButton_1))
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(updateBtn)
								.addGap(18).addComponent(deletBtn).addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(clearBtn)))
				.addPreferredGap(ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
				.addComponent(list, GroupLayout.PREFERRED_SIZE, 855, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(26)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel).addComponent(firstName, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel_1).addComponent(lastname, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(12)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_2))
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel_3).addComponent(username, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel_4).addComponent(password, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel_5).addComponent(phone, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(27)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnNewButton).addComponent(btnNewButton_1))
								.addGap(41)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(updateBtn)
										.addComponent(deletBtn).addComponent(clearBtn)))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(19).addComponent(list,
								GroupLayout.PREFERRED_SIZE, 380, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
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

	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
	}

	private void fill(User user) {
		firstName.setText(user.getFirstName());
		lastname.setText(user.getLastName());
		username.setText(user.getUsername());
		email.setText(user.getEmail());
		phone.setText(user.getPhone());
	}
}
