package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Product;
import service.ProductService;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class Cashier extends JFrame {
	
	private ProductService productService;
	private JPanel contentPane;
	private JTextField codeTextField;
	private JTextField quantityTextField;
	private JList items;
	private JTextField totalTextField;
	private JLabel totalLabel;


	/**
	 * Create the frame.
	 */
	public Cashier() {
		productService = new ProductService();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 515, 385);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Code\r\n");
		
		JLabel lblNewLabel_1 = new JLabel("Quantity\r\n");
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setBackground(SystemColor.activeCaption);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code=codeTextField.getText();
				Product product = productService.findByCode(code);
				boolean succes=true;
				if (product == null){
					succes=false;
					showErrorMessage("Product does't exist");
				}
				int quantity = Integer.parseInt(quantityTextField.getText());
				if (quantity>product.getQuantity()) {
					showErrorMessage("Quantity not avaliable remaining" + product.getQuantity() +"items");
					succes=false;
				}
				if (succes) {
					
					DefaultListModel demoList = new DefaultListModel();
					
					
					demoList.addElement("{"+ product.getName()+"}" +"{" + product.getCode() + "}" + "{" + quantity+ "}");
					 //JList listd = new JList(demoList);
					 codeTextField.setText("");
					 quantityTextField.setText("");
				
					 items.setModel(demoList);
					
					
				}
			}
		});
		
		codeTextField = new JTextField();
		codeTextField.setColumns(10);
		
		quantityTextField = new JTextField();
		quantityTextField.setColumns(10);
		
		items = new JList();
		
		totalTextField = new JTextField();
		totalTextField.setColumns(10);
		
		totalLabel = new JLabel("Total");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(52)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel)
									.addGap(24)
									.addComponent(codeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblNewLabel_1)
									.addGap(18)
									.addComponent(quantityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnNewButton))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(188)
									.addComponent(totalLabel)
									.addGap(18)
									.addComponent(totalTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(76)
							.addComponent(items, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(81, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(37)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(codeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1)
						.addComponent(quantityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(items, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(totalTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(totalLabel))
					.addGap(32))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
