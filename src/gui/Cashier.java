package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Order;
import model.OrderItem;
import model.Product;
import model.User;
import service.OrderService;
import service.ProductService;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class Cashier extends JFrame {

	private ProductService productService;
	private JPanel contentPane;
	private JTextField codeTextField;
	private JTextField quantityTextField;
	private JList list;
	private JTextField totalTextField;
	private JLabel totalLabel;
	private JButton placeOrder;
	private JButton update;
	private JButton delete;
	private JButton clear;
	private Order order;
	private List<OrderItem> items;
	private DefaultListModel model;
	private JList productList;
	private DefaultListModel productModel;
	private List<Product> products;
	private OrderService orderService;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cashier frame = new Cashier();
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
	public Cashier() {
		productService = new ProductService();
		orderService = new OrderService();
		order = new Order();
		items = new ArrayList<OrderItem>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 474);
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
				String code = codeTextField.getText();
				int quantity = Integer.parseInt(quantityTextField.getText());
				try {
					Product product = productService.findByCode(code);
					if (quantity > product.getQuantity()) {
						showErrorMessage("Quantity not avaliable remaining" + product.getQuantity() + "items");
						return;
					}

					if (inCart(code)) {
						OrderItem item = getOrderItem(code);
						int newQuantity = item.getQuantity() + quantity;
						if (newQuantity > product.getQuantity()) {
							showErrorMessage("Quantity not avaliable remaining"
									+ (product.getQuantity() - item.getQuantity()) + "items");
							return;
						}
						item.setQuantity(newQuantity);
					} else {
						OrderItem item = productToOrderItem(product);
						items.add(item);
						model.addElement(item);
					}
					removeQuantity(code, quantity);
					calculateTotal();
					contentPane.repaint();
					clear();

				} catch (RuntimeException e2) {
					showErrorMessage(e2.getMessage());
				}

			}

		});

		codeTextField = new JTextField();
		codeTextField.setColumns(10);

		quantityTextField = new JTextField();
		quantityTextField.setColumns(10);

		list = new JList();
		model = new DefaultListModel();
		list.setModel(model);
		totalTextField = new JTextField();
		totalTextField.setColumns(10);

		totalLabel = new JLabel("Total");

		placeOrder = new JButton("Place Order");
		placeOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					orderService.insert(items);
					items.clear();
					model.clear();
					productModel.clear();
					totalTextField.setText("");
					products = productService.getProducts();
					productModel.addAll(products);
					contentPane.repaint();
					showMessage("Success");
				} catch (RuntimeException e2) {
					e2.printStackTrace();
					showErrorMessage(e2.getMessage());
				}
			}
		});

		update = new JButton("Update");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code = codeTextField.getText();
				int quantity = Integer.parseInt(quantityTextField.getText());

				if (inCart(code)) {
					OrderItem item = getOrderItem(code);
					Product product = getProduct(code);
					if (quantity > item.getQuantity()) {
						if (quantity > product.getQuantity() + item.getQuantity()) {
							showErrorMessage("Quantity not avaliable");
							return;
						}
						int difference = quantity - item.getQuantity();
						item.setQuantity(quantity);
						removeQuantity(code, difference);
					}

					if (quantity < item.getQuantity()) {
						int difference = item.getQuantity() - quantity;
						item.setQuantity(quantity);
						addQuantity(code, difference);
					}
					calculateTotal();
					contentPane.repaint();
					clear();
				} else {
					showErrorMessage("Product not in cart");
					return;
				}

			}

		});

		delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code = codeTextField.getText();
				if (inCart(code)) {
					OrderItem item = getOrderItem(code);
					items.remove(item);
					model.removeElement(item);
					addQuantity(code, item.getQuantity());
					calculateTotal();
					clear();
					contentPane.repaint();
				} else {
					showErrorMessage("Product not in cart");
				}

			}

		});

		clear = new JButton("Clear");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});

		productList = new JList();
		productModel = new DefaultListModel();
		products = productService.getProducts();
		productModel.addAll(products);
		productList.setModel(productModel);

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
				int index = theList.locationToIndex(mouseEvent.getPoint());
				Object element = theList.getModel().getElementAt(index);
				if (element instanceof OrderItem) {
					codeTextField.setText(((OrderItem) element).getCode());
				}
				if (element instanceof Product) {
					codeTextField.setText(((Product) element).getCode());
				}

			}
		};
		list.addMouseListener(mouseListener);
		productList.addMouseListener(mouseListener);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(44).addComponent(totalLabel).addGap(39)
								.addComponent(totalTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(72).addComponent(placeOrder))
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(list,
								GroupLayout.PREFERRED_SIZE, 439, GroupLayout.PREFERRED_SIZE)))
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
						.createSequentialGroup().addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(18).addComponent(btnNewButton))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(codeTextField,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup().addGap(27).addComponent(
										quantityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(67)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(delete)
										.addComponent(update).addComponent(clear))))
				.addGap(61).addComponent(productList, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE)
				.addGap(334)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(list, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
								.addComponent(productList, GroupLayout.PREFERRED_SIZE, 446, GroupLayout.PREFERRED_SIZE))
						.addGap(6)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(totalLabel)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(totalTextField, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(placeOrder)))
						.addGap(14))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(50)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel)
								.addComponent(codeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1)
								.addComponent(quantityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18).addComponent(btnNewButton).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(update).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(delete)
						.addGap(18).addComponent(clear).addContainerGap(242, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}

	private void clear() {
		quantityTextField.setText("");
		codeTextField.setText("");

	}

	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
	}

	private void fill(OrderItem item) {
		quantityTextField.setText(String.valueOf(item.getQuantity()));
		codeTextField.setText(item.getCode());

	}

	private OrderItem productToOrderItem(Product product) {
		OrderItem item = new OrderItem();
		item.setCode(product.getCode());
		item.setName(product.getName());
		item.setQuantity(Integer.parseInt(quantityTextField.getText()));
		item.setPrice(product.getPrice());
		item.setProductId(product.getId());

		return item;
	}

	private boolean inCart(String code) {
		for (Object o : items) {
			OrderItem item = (OrderItem) o;
			if (code.equals(item.getCode())) {
				return true;
			}
		}
		return false;

	}

	private OrderItem getOrderItem(String code) {
		for (Object o : items) {
			OrderItem item = (OrderItem) o;
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;

	}

	private void calculateTotal() {
		double sum = 0;
		for (OrderItem orderItem : items) {
			sum += orderItem.getPrice() * orderItem.getQuantity();

		}
		totalTextField.setText(String.valueOf(sum));
	}

	private void removeQuantity(String code, int quantity) {
		for (Product product : products) {
			if (product.getCode().equals(code)) {
				int newQuantity = product.getQuantity() - quantity;
				product.setQuantity(newQuantity);
			}

		}

	}

	private Product getProduct(String code) {
		for (Product product : products) {
			if (code.equals(product.getCode())) {
				return product;
			}
		}
		return null;
	}

	private void addQuantity(String code, int quantity) {
		for (Product product : products) {
			if (product.getCode().equals(code)) {
				int newQuantity = product.getQuantity() + quantity;
				product.setQuantity(newQuantity);
			}

		}

	}

}
