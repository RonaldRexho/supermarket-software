package gui.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import gui.SupermarketFrame;
import model.User;
import service.UserService;
import util.Flow;

public class LoginPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;

	private JLabel usernameLabel;
	private JTextField usernameTextField;

	private JLabel passwordLabel;
	private JPasswordField passwordTextField;

	private JButton submitButton;

	public LoginPanel() {

		this.userService = new UserService();
		this.setName("Login");
		
		initializeComponents();
		this.add(usernameLabel);
		this.add(usernameTextField);	
		this.add(passwordLabel);
		this.add(passwordTextField);
		this.add(submitButton);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String username = usernameTextField.getText();
		char[] password = passwordTextField.getPassword();
		//TODO> perfom validation once you get the username and password
		
		User user = userService.findByUsername(username);
		boolean success = false;
		
		if(user != null) {
			success = checkPassword(password, user.getPassword());
		}
		
		if(success) {
			SupermarketFrame.nextView(Flow.ADMIN);
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
	
	private void initializeComponents() {
		usernameLabel = new JLabel();
		usernameLabel.setBounds(10, 10, 80, 25);
		usernameLabel.setText("Username :");
		
		usernameTextField = new JTextField(20);
		usernameTextField.setBounds(100, 10, 160, 25);
		
		passwordLabel = new JLabel();
		passwordLabel.setText("Password :");
		passwordLabel.setBounds(10, 40, 80, 25);
		
		passwordTextField = new JPasswordField(20);
		passwordTextField.setBounds(100, 40, 160, 25);
		
		submitButton = new JButton("Submit");
		submitButton.setBounds(10, 80, 80, 25);
		submitButton.addActionListener(this);
	}

}
