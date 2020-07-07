package gui.cashier;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import exception.SupermarketException;
import model.OrderItem;

public class OrderTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "NAME", "QUANTITY", "PRICE" };
	private List<OrderItem> items = new ArrayList<>();

	@Override
	public int getRowCount() {
		return items.size();
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
			value = items.get(row).getName();
		} else if (col == 1) {
			value = items.get(row).getQuantity();
		} else {
			value = items.get(row).getPrice();
		}
		return value;
	}

	public void add(OrderItem item) {
		items.add(0, item);
		fireTableDataChanged();
	}

	public void setQuantity(String code, int quantity) {
		OrderItem item = find(code);
		item.setQuantity(quantity);
		fireTableDataChanged();
	}
	
	public void addQuantity(String code, int quantity) {
		OrderItem item = find(code);
		item.setQuantity(item.getQuantity() + quantity);
		fireTableDataChanged();
	}

	public void remove(String code) {
		OrderItem item = find(code);
		items.remove(item);
		fireTableDataChanged();
	}

	public OrderItem get(int row) {
		return items.get(row);
	}

	public boolean contains(String code) {
		for (OrderItem item : items) {
			if (item.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	public OrderItem find(String code) {
		for (OrderItem item : items) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		throw new SupermarketException("Order item not found");
	}
	
	public double getTotalPrice() {
		double sum  = 0;
		for (OrderItem item : items) {
			sum += item.getQuantity() * item.getPrice();
		}
		return sum;
	}
	
	public List<OrderItem> getItems(){
		return this.items;
	}
	
	public void removeAll() {
		items.clear();
		fireTableDataChanged();
	}
}
