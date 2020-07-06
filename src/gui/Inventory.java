package gui;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import exception.SupermarketException;
import model.Product;
import service.ProductService;

public class Inventory extends JFrame {

	private static final long serialVersionUID = 1L;
	private ProductService productService;

	private JPanel contentPane;
	private DefaultListModel<Product> model;
	private JList<Product> list;
	private JLabel lblNewLabel;
	private JTextField nameField;
	private JTextField codeField;
	private JTextField quantityField;
	private JTextField priceField;

	private JButton deleteButton;
	private JButton addButton;
	private JButton updateButton;
	private JButton clearButton;


	public Inventory() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 714, 533);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		addButton = new JButton("Add");
		addButton.addActionListener(new AddButtonListener());

		list = new JList<>();
		list.addMouseListener(new ListMouseListener());
		model = new DefaultListModel<>();
		addProducts(productService.getProducts());
		list.setModel(model);

		nameField = new JTextField();
		nameField.setColumns(10);

		codeField = new JTextField();
		codeField.setColumns(10);

		quantityField = new JTextField();
		quantityField.setColumns(10);

		priceField = new JTextField();
		priceField.setColumns(10);

		lblNewLabel = new JLabel("Name");

		updateButton = new JButton("Update");
		updateButton.addActionListener(new UpdatedButtonListener());

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteButtonListener());

		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ClearButtonListener());
		
		setLayout();

	}

	private void setLayout() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(22)
				.addComponent(list, GroupLayout.PREFERRED_SIZE, 463, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
						.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(18).addComponent(lblNewLabel)
										.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addComponent(priceField, 124, 124, 124)
												.addComponent(quantityField, 124, 124, 124)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(nameField, GroupLayout.PREFERRED_SIZE, 122,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(codeField))))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(addButton, 0, 0, Short.MAX_VALUE)))
						.addContainerGap()).addGroup(
								gl_contentPane.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(updateButton)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
														.addComponent(clearButton).addComponent(deleteButton)))
										.addGap(57)))));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(67).addComponent(list,
								GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(87)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(codeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(quantityField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(priceField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(addButton).addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(updateButton).addGap(18).addComponent(deleteButton).addGap(27)
								.addComponent(clearButton)))
				.addContainerGap(74, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}

	private void showMessage(String message, String title, int type) {
		JOptionPane.showMessageDialog(this, message, title, type);
	}

	private void clear() {
		nameField.setText("");
		codeField.setText("");
		priceField.setText("");
		quantityField.setText("");
	}

	private void refresh() {
		contentPane.repaint();
	}

	private void addProducts(List<Product> products) {
		for (Product product : products) {
			model.addElement(product);
		}
	}

	private Product createProductFromFields() {
		Product product = new Product();
		product.setName(nameField.getText());
		product.setCode(codeField.getText());
		product.setPrice(Double.parseDouble(priceField.getText()));
		product.setQuantity(Integer.parseInt(quantityField.getText()));
		return product;
	}

	private void addToList(Product product) {
		model.add(0, product);
	}

	private void updateToList(Product product) {
		Product currentProduct = getFromList(product.getCode());
		currentProduct.setName(product.getName());
		currentProduct.setPrice(product.getPrice());
		currentProduct.setQuantity(product.getQuantity());
	}

	private void removeToList(String code) {
		Product product = getFromList(code);
		model.removeElement(product);
	}

	private Product getFromList(String code) {
		for (int i = 0; i < model.size(); i++) {
			if (model.get(i).getCode().equals(code)) {
				return model.get(i);
			}
		}
		return null;
	}

	class AddButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Product product = createProductFromFields();
				productService.insert(product);
				addToList(product);
				clear();
				showMessage("Inserted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
				refresh();
			} catch (SupermarketException ex) {
				showMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class UpdatedButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				Product product = createProductFromFields();
				productService.update(product);
				updateToList(product);
				clear();
				showMessage("Updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
				refresh();
			} catch (SupermarketException ex) {
				showMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class DeleteButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				String code = codeField.getText();
				productService.delete(code);
				clear();
				removeToList(code);
				showMessage("Removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
				refresh();
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

	class ListMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			int index = list.locationToIndex(e.getPoint());
			Product product = model.get(index);
			nameField.setText(product.getName());
			codeField.setText(product.getCode());
			quantityField.setText(String.valueOf(product.getQuantity()));
			priceField.setText(String.valueOf(product.getPrice()));
		}
	}

}