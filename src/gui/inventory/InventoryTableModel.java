package gui.inventory;

import java.util.List;

import model.Product;

import javax.swing.table.AbstractTableModel;

import exception.SupermarketException;

public class InventoryTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "NAME", "CODE", "QUANTITY", "PRICE" };
	private List<Product> products;

	public InventoryTableModel(List<Product> products) {
		this.products = products;
	}

	@Override
	public int getRowCount() {
		if (products == null) {
			return 0;
		}
		return products.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		Object value;
		if (col == 0) {
			value = products.get(row).getName();
		} else if (col == 1) {
			value = products.get(row).getCode();
		} else if (col == 2) {
			value = products.get(row).getQuantity();
		} else {
			value = products.get(row).getPrice();
		}
		return value;
	}
	
	public void add(Product product) {
		products.add(0, product);
		fireTableDataChanged();
	}
	
	public void update(Product product) {
		Product currentProduct = find(product.getCode());
		currentProduct.setName(product.getName());
		currentProduct.setPrice(product.getPrice());
		currentProduct.setQuantity(product.getQuantity());
		fireTableDataChanged();
	}

	public void remove(String code) {
		Product product = find(code);
		products.remove(product);
		fireTableDataChanged();
	}
	
	public Product get(int row) {
		return products.get(row);
	}
	
	public Product find(String code) {
		for(Product product : products) {
			if (product.hasCode(code)) {
				return product;
			}
		}
		throw new SupermarketException("Product does not exist");
	}
}
