package gui.cashier;

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
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import exception.SupermarketException;
import gui.inventory.InventoryTableModel;
import model.OrderItem;
import model.Product;
import service.OrderService;
import service.ProductService;

public class CashierView extends JFrame {
	private static final long serialVersionUID = 1L;

	private ProductService productService;
	private OrderService orderService;

	private JPanel contentPane;

	private JButton addButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JSpinner quantityField;
	private JButton placeOrder;

	private JTextField codeField;
	private JTextField totalField;

	private JTable orderTable;
	private OrderTableModel items;

	private JTable productTable;
	private InventoryTableModel products;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CashierView frame = new CashierView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CashierView() {
		productService = new ProductService();
		orderService = new OrderService();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1220, 583);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		codeField = new JTextField();
		codeField.setColumns(10);
		totalField = new JTextField();
		totalField.setForeground(Color.BLACK);
		totalField.setBackground(Color.WHITE);
		totalField.setColumns(10);
		quantityField = new JSpinner();

		placeOrder = new JButton("ORDER");
		placeOrder.addActionListener(new PlaceOrderAction());
		addButton = new JButton("ADD");
		addButton.addActionListener(new AddButtonListener());
		updateButton = new JButton("UPDATE");
		updateButton.addActionListener(new UpdateButtonListener());
		deleteButton = new JButton("DELETE");
		deleteButton.addActionListener(new DeleteButtonListener());

		products = new InventoryTableModel(productService.getProducts());
		productTable = new JTable();
		productTable.setModel(products);
		productTable.setShowVerticalLines(false);
		productTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		productTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		productTable.getSelectionModel().addListSelectionListener(new ProductSelectionListener());

		items = new OrderTableModel();
		orderTable = new JTable();
		orderTable.setModel(items);
		orderTable.setShowVerticalLines(false);
		orderTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		orderTable.getSelectionModel().addListSelectionListener(new OrderSelectionListener());

		setLayout();
	}

	private void showMessage(String message, String title, int type) {
		JOptionPane.showMessageDialog(this, message, title, type);
	}

	private void fill(String code, int quantity) {
		codeField.setText(code);
		quantityField.setValue(quantity);
	}

	private OrderItem productToOrderItem(Product product) {
		OrderItem item = new OrderItem();
		item.setCode(product.getCode());
		item.setName(product.getName());
		item.setQuantity(Integer.parseInt(quantityField.getValue().toString()));
		item.setPrice(product.getPrice());
		item.setProductId(product.getId());
		return item;
	}
	
	private void recalculateTotal() {
		totalField.setText(String.valueOf(items.getTotalPrice()));
	}
	
	private void clear() {
		codeField.setText("");
		quantityField.setValue(0);
	}
	
	class AddButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String code = codeField.getText();
				int currentQuantity = Integer.parseInt(quantityField.getValue().toString());
				Product product = products.find(code);
				if (product.hasAvailableQuantity(currentQuantity)) {
					
					if (items.contains(code)) {
						items.addQuantity(code, currentQuantity);
					} else {
						OrderItem item = productToOrderItem(product);
						items.add(item);
					}
					
					product.setQuantity(product.getQuantity() - currentQuantity);
					products.update(product);
					productService.update(product);
					recalculateTotal();
					clear();
					showMessage("Inserted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
				} else {
					showMessage("Quantity not available", "Success", JOptionPane.ERROR_MESSAGE);
				}
				
			} catch (SupermarketException ex) {
				showMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class UpdateButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String code = codeField.getText();
				int currentQuantity = Integer.parseInt(quantityField.getValue().toString());
				Product product = products.find(code);
				//Product already inserted in cart
				if (items.contains(code)) {
					OrderItem item = items.find(code);

					if (item.quantityLessThan(currentQuantity)) {
						int newQuantity = currentQuantity + item.getQuantity();
						if (!product.hasAvailableQuantity(newQuantity)) {
							throw new SupermarketException("Quantity not available");
						}
						items.setQuantity(code, newQuantity);
					}

					if (item.quantityGreaterThan(currentQuantity)) {
						int difference = item.getQuantity() - currentQuantity;
						items.setQuantity(code, currentQuantity);
						product.setQuantity(product.getQuantity() + difference);
						products.update(product);
						productService.update(product);
					}
					
					recalculateTotal();
					clear();
					showMessage("Inserted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
				} else {
					showMessage("Product not selected", "Success", JOptionPane.ERROR_MESSAGE);
				}
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
				int currentQuantity = Integer.parseInt(quantityField.getValue().toString());
				Product product = products.find(code);
				if (items.contains(code)) {
					items.remove(code);
					product.setQuantity(product.getQuantity() + currentQuantity);
					products.update(product);
					productService.update(product);
					clear();
					showMessage("Removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
				} else {
					showMessage("Product not in cart", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SupermarketException ex) {
				showMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class PlaceOrderAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				orderService.insert(items.getItems());
				items.removeAll();
				totalField.setText("");
				clear();
				showMessage("Success", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch (SupermarketException ex) {
				showMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class OrderSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int row = orderTable.getSelectedRow();
			if (row >= 0) {
				OrderItem item = items.get(row);
				fill(item.getCode(), item.getQuantity());
			}
		}
	}

	class ProductSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int row = productTable.getSelectedRow();
			if (row >= 0) {
				Product product = products.get(row);
				fill(product.getCode(), 1);
			}
		}
	}

	private void setLayout() {
		JScrollPane orderScrollPane = new JScrollPane(orderTable);
		JScrollPane productScrollPane = new JScrollPane(productTable);
		JLabel totalLabel = new JLabel("TOTAL:");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(
								orderScrollPane, GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE))
						.addGroup(gl_contentPane
								.createSequentialGroup().addGap(215).addComponent(totalLabel).addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(placeOrder, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(totalField, Alignment.TRAILING, 183, 183, Short.MAX_VALUE))))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(codeField, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
								.addGap(7).addComponent(quantityField, GroupLayout.PREFERRED_SIZE, 46,
										GroupLayout.PREFERRED_SIZE))
						.addComponent(updateButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(addButton, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE).addComponent(
								deleteButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGap(18).addComponent(productScrollPane, GroupLayout.PREFERRED_SIZE, 499, GroupLayout.PREFERRED_SIZE)
				.addGap(10)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(51)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(codeField, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(quantityField, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(addButton)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(updateButton)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(deleteButton))
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
										.addComponent(orderScrollPane, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(totalField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(totalLabel))
						.addGap(18).addComponent(placeOrder).addGap(25))
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(productScrollPane, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE).addGap(107)));
		contentPane.setLayout(gl_contentPane);
	}
}
