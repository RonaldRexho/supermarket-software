package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import service.ProductService;
import util.DBUtil;

import java.awt.SystemColor;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;

import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

import org.junit.platform.commons.function.Try;

import model.Product;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Inventory extends JFrame {

	private JPanel contentPane;
	private DefaultListModel model;
	private JList list;
	private JTextField nameText;
	private JTextField codeText;
	private JTextField quantityText;
	private JTextField priceText;
	private ProductService productService;
	private JButton clearBtn;
	private int currentRow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inventory frame = new Inventory();
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
	public Inventory() {
		productService = new ProductService();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 714, 533);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Product product = new Product();
				product.setName(nameText.getText());
				product.setCode(codeText.getText());
				product.setPrice(Double.parseDouble(priceText.getText()));
				product.setQuantity(Integer.parseInt(quantityText.getText()));
				try {
					productService.insert(product);
					model.addElement(product);
					nameText.setText("");
					codeText.setText("");
					priceText.setText("");
					quantityText.setText("");
				} catch (RuntimeException e2) {
					showErrorMessage(e2.getMessage());
				}
			}
		});

		list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

		        JList theList = (JList) e.getSource();
		        
		          int index = theList.locationToIndex(e.getPoint());
		          if (index >= 0) {
		            Product o = (Product)theList.getModel().getElementAt(index);
		            System.out.println("Double-clicked on: " + o.toString());
		          nameText.setText(o.getName());
		          codeText.setText(o.getCode());
		          quantityText.setText(String.valueOf(o.getQuantity()));
		          priceText.setText(String.valueOf(o.getPrice()));
		          currentRow=index;
		          
		        }
		      
				
			}
		});
		model = new DefaultListModel();
		List<Product> products = productService.getProducts();
		model.addAll(products);
		list.setModel(model);

		nameText = new JTextField();
		nameText.setColumns(10);

		codeText = new JTextField();
		codeText.setColumns(10);

		quantityText = new JTextField();
		quantityText.setColumns(10);

		priceText = new JTextField();
		priceText.setColumns(10);

		JLabel lblNewLabel = new JLabel("Name");
		
		JButton updateBtb = new JButton("Update");
		updateBtb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Product product = new Product();
				product.setName(nameText.getText());
				product.setCode(codeText.getText());
				product.setPrice(Double.parseDouble(priceText.getText()));
				product.setQuantity(Integer.parseInt(quantityText.getText()));
			    
				try {
					productService.update(product);
					Product currentProduct = (Product)model.get(currentRow);
					currentProduct.setName(product.getName());
					currentProduct.setPrice(product.getPrice());
					currentProduct.setQuantity(product.getQuantity());
					list.repaint();
					nameText.setText("");
					quantityText.setText("");
					priceText.setText("");
					codeText.setText("");
					showMessage("Product Updated succesfully");
				} catch (RuntimeException e2) {
					nameText.setText("");
					quantityText.setText("");
					priceText.setText("");
					codeText.setText("");
					showErrorMessage(e2.getMessage());
				}
			}
		});
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String code = codeText.getText();
					productService.delete(code);
					nameText.setText("");
					quantityText.setText("");
					priceText.setText("");
					codeText.setText("");
					model.remove(currentRow);
					list.repaint();
                    showMessage("Delete succesfully");
				} catch (RuntimeException e2) {
					nameText.setText("");
					quantityText.setText("");
					priceText.setText("");
					codeText.setText("");
					showErrorMessage(e2.getMessage());

				}
			}
		});
		
		clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameText.setText("");
				quantityText.setText("");
				priceText.setText("");
				codeText.setText("");
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(22)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 463, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(18)
									.addComponent(lblNewLabel)
									.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(priceText, 124, 124, 124)
										.addComponent(quantityText, 124, 124, 124)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(nameText, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
											.addComponent(codeText))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnNewButton, 0, 0, Short.MAX_VALUE)))
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(updateBtb)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addComponent(clearBtn)
									.addComponent(deleteBtn)))
							.addGap(57))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(67)
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(87)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(nameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(codeText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(quantityText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(priceText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnNewButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(updateBtb)
							.addGap(18)
							.addComponent(deleteBtn)
							.addGap(27)
							.addComponent(clearBtn)))
					.addContainerGap(74, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);

	}

	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	private void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
	}
}