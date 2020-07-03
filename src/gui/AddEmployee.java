package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class AddEmployee extends JFrame {

	private JPanel contentPane;
	private JTextField firstName;
	private JTextField lastname;
	private JTextField birthday;
	private JTextField username;
	private JPasswordField password;
	private JTextField phone;
	private JList list;


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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 542, 402);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("First Name");

		JLabel lblNewLabel_1 = new JLabel("Last Name");

		JLabel lblNewLabel_2 = new JLabel("Birthday");

		JLabel lblNewLabel_3 = new JLabel("Username");

		firstName = new JTextField();
		firstName.setColumns(10);

		lastname = new JTextField();
		lastname.setColumns(10);

		birthday = new JTextField();
		birthday.setColumns(10);

		username = new JTextField();
		username.setColumns(10);

		JButton btnNewButton = new JButton("Add");
		btnNewButton.setBackground(SystemColor.activeCaption);
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				/*
				 * 
				 * String name = firstName.getText(); String lastName= lastname.getText();
				 * String userName = username.getText(); String birthDay= birthday.getText();
				 * String passWord =password.getText(); String phone1=phone.getText();
				 * 
				 * 
				 * try (PreparedStatement st = DBUtil.connect().prepareStatement(INSERT,
				 * Statement.RETURN_GENERATED_KEYS)) { st.setString(1, firstName.getText());
				 * st.setString(2, lastname.getText());
				 * 
				 * st.executeUpdate(); ResultSet rs = st.getGeneratedKeys(); rs.next();
				 * 
				 * } catch (Exception e2) { e2.printStackTrace(); }
				 * 
				 */
				DefaultListModel demoList = new DefaultListModel();
				demoList.addElement(firstName.getText() + lastname.getText());
				list.setModel(demoList);
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
		

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
						.createSequentialGroup().addGap(15)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(lblNewLabel)
								.addComponent(lblNewLabel_1).addComponent(lblNewLabel_2)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblNewLabel_3)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(lblNewLabel_5).addComponent(lblNewLabel_4))))
						.addGap(57)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false).addComponent(username)
								.addComponent(birthday).addComponent(lastname).addComponent(firstName)
								.addComponent(password).addComponent(phone, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(90).addComponent(btnNewButton)
								.addGap(53).addComponent(btnNewButton_1)))
				.addPreferredGap(ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
				.addComponent(list, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE).addContainerGap()));
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
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel_2).addComponent(birthday, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
										.addComponent(btnNewButton).addComponent(btnNewButton_1)))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(19).addComponent(list,
								GroupLayout.PREFERRED_SIZE, 297, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(37, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}
}
