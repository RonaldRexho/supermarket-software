package gui.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import gui.Admin;
import gui.SupermarketApplication;
import model.User;
import service.UserService;
import util.Flow;

public class LoginView extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private UserService userService;

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	public LoginView() {
		userService = new UserService();
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);

		textField = new JTextField();
		textField.setColumns(10);

		passwordField = new JPasswordField();

		JButton btnNewButton = new JButton("LogIn");
		btnNewButton.addActionListener(this);

		JLabel lblNewLabel = new JLabel("Username");

		JLabel lblNewLabel_1 = new JLabel("Password");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup().addGap(32)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
														.addComponent(lblNewLabel).addComponent(lblNewLabel_1))
												.addGap(46)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(passwordField, Alignment.LEADING)
														.addComponent(textField, Alignment.LEADING)))
										.addGroup(gl_contentPane.createSequentialGroup().addGap(133).addComponent(
												btnNewButton, GroupLayout.PREFERRED_SIZE, 69,
												GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(214, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(82)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1))
						.addPreferredGap(ComponentPlacement.RELATED, 52, Short.MAX_VALUE).addComponent(btnNewButton)
						.addGap(43)));
		contentPane.setLayout(gl_contentPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String username = textField.getText();
		char[] password = passwordField.getPassword();
		// TODO> perfom validation once you get the username and password

		User user = userService.findByUsername(username);
		boolean success = false;

		if (user != null) {
			success = checkPassword(password, user.getPassword());
		}

		if (success) {

			if (user.isAdmin()) {
				SupermarketApplication.nextView(Flow.ADMIN);
			} else {
				SupermarketApplication.nextView(Flow.CASHIER);

			}

		} else {
			showErrorMessage();
		}

	}

	private void showErrorMessage() {
		JOptionPane.showMessageDialog(this, "Wrong username or passowrd!", "Login error", JOptionPane.ERROR_MESSAGE);
	}

	private boolean checkPassword(char[] actual, String expected) {
		return expected.equals(String.valueOf(actual));
	}

}
