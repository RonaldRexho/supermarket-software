package gui;

import java.awt.Color;
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
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import exception.SupermarketException;
import model.User;
import service.UserService;
import util.View;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField usernameField;
	private JLabel usernameLabel;
	private JPasswordField passwordField;
	private JLabel passwordLabel;
	private JButton loginButton;

	public LoginView() {
		setForeground(SystemColor.activeCaption);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		usernameField = new JTextField();
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBackground(SystemColor.text);

		loginButton = new JButton("Login");
		loginButton.addActionListener(new LoginActionListener());

		usernameLabel = new JLabel("username:");
		passwordLabel = new JLabel("password:");

		setLayout();
	}

	private void showMessage(String message, String title, int type) {
		JOptionPane.showMessageDialog(this, message, title, type);
	}

	private void setLayout() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(60)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(passwordLabel)
								.addComponent(usernameLabel))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(passwordField)
								.addComponent(usernameField).addComponent(loginButton, Alignment.TRAILING,
										GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
						.addGap(170)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(82)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(usernameLabel))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(passwordLabel))
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(loginButton)
						.addContainerGap(87, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}

	class LoginActionListener implements ActionListener {

		private String username;
		private char[] password;
		private UserService userService;

		public LoginActionListener() {
			userService = new UserService();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			username = usernameField.getText();
			password = passwordField.getPassword();

			try {

				User user = userService.findByUsername(username);
				char[] original = user.getPassword();
				if (match(password, original)) {
					showNextViewForUser(user);
				} else {
					showMessage("Incorrect password", "Login failed", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SupermarketException ex) {
				showMessage(ex.getMessage(), "Login failed", JOptionPane.ERROR_MESSAGE);
			}
		}

		private void showNextViewForUser(User user) {
			if (user.isAdmin()) {
				Runner.nextView(View.ADMIN);
			} else {
				Runner.nextView(View.CASHIER);
			}
		}

		private boolean match(char[] inserted, char[] original) {
			if (inserted.length != original.length) {
				return false;
			}
			for (int i = 0; i < original.length; i++) {
				if (inserted[i] != original[i]) {
					return false;
				}
			}
			return true;
		}
	}
}
