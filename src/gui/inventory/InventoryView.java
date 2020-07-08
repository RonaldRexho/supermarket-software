package gui.inventory;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import exception.SupermarketException;
import model.Product;
import service.ProductService;

public class InventoryView extends JFrame {

	private static final long serialVersionUID = 1L;
	private ProductService productService;

	private JPanel contentPane;

	private JLabel nameLabel;
	private JLabel codeLabel;
	private JLabel quantityLabel;
	private JLabel priceLabel;

	private JTextField nameField;
	private JTextField codeField;
	private JTextField quantityField;
	private JTextField priceField;

	private JButton deleteButton;
	private JButton addButton;
	private JButton updateButton;
	private JButton clearButton;
	private JTable inventoryTable;
	private InventoryTableModel inventory;
	private JScrollPane scrollPane;
	
	public static void main(String[] args) {
		new InventoryView().setVisible(true);
	}

	public InventoryView() {
		productService = new ProductService();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1029, 528);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		nameField = new JTextField();
		nameField.setColumns(10);
		codeField = new JTextField();
		codeField.setColumns(10);
		quantityField = new JTextField();
		quantityField.setColumns(10);
		priceField = new JTextField();
		priceField.setColumns(10);

		nameLabel = new JLabel("Name:");
		codeLabel = new JLabel("Code:");
		quantityLabel = new JLabel("Quantity:");
		priceLabel = new JLabel("Price:");

		addButton = new JButton("Add");
		addButton.addActionListener(new AddButtonListener());

		updateButton = new JButton("Update");
		updateButton.addActionListener(new UpdatedButtonListener());

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteButtonListener());

		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ClearButtonListener());

		inventory = new InventoryTableModel(productService.getProducts());
		inventoryTable = new JTable();

		inventoryTable.setModel(inventory);
		inventoryTable.setShowVerticalLines(false);
		inventoryTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		inventoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		inventoryTable.getSelectionModel().addListSelectionListener(new RowSelectionListener());

		scrollPane = new JScrollPane(inventoryTable);

		setLayout();
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

	private Product createProductFromFields() {
		Product product = new Product();
		product.setName(nameField.getText());
		product.setCode(codeField.getText());
		try {
			product.setPrice(Double.parseDouble(priceField.getText()));
			product.setQuantity(Integer.parseInt(quantityField.getText()));
		} catch (NumberFormatException e) {
			throw new SupermarketException("Check price or quantity value");
		}
		
		return product;
	}

	class AddButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Product product = createProductFromFields();
				productService.insert(product);
				inventory.add(product);
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
				Product product = createProductFromFields();
				productService.update(product);
				inventory.update(product);
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
				String code = codeField.getText();
				productService.delete(code);
				clear();
				inventory.remove(code);
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
			int row = inventoryTable.getSelectedRow();
			if (row >= 0) {
				Product product = inventory.get(row);
				nameField.setText(product.getName());
				codeField.setText(product.getCode());
				priceField.setText(String.valueOf(product.getPrice()));
				quantityField.setText(String.valueOf(product.getQuantity()));
			}
		}
	}
	
	private void setLayout() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(quantityLabel)
						.addComponent(priceLabel)
						.addComponent(nameLabel)
						.addComponent(codeLabel, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(nameField, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
							.addGap(20))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(codeField, Alignment.LEADING)
								.addComponent(clearButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(deleteButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(updateButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(addButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(priceField, Alignment.LEADING)
								.addComponent(quantityField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
							.addGap(18)))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 662, GroupLayout.PREFERRED_SIZE)
					.addGap(316))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(74)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(nameLabel)
								.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(codeLabel)
								.addComponent(codeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(13)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(quantityLabel)
								.addComponent(quantityField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(19)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(priceLabel)
								.addComponent(priceField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(20)
							.addComponent(addButton)
							.addGap(17)
							.addComponent(updateButton)
							.addGap(17)
							.addComponent(deleteButton)
							.addGap(21)
							.addComponent(clearButton))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(57)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(74, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

}