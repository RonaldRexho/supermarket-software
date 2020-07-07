package gui.employee;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.User;

public class EmployeeTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "FIRST_NAME", "LAST_NAME", "EMAIL", "USERNAME", "PHONE", "BIRTHDAY", "ROLE" };
	private List<User> employees;

	public EmployeeTableModel(List<User> employees) {
		this.employees = employees;
	}

	@Override
	public int getRowCount() {
		if (employees == null) {
			return 0;
		}
		return employees.size();
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
			value = employees.get(row).getFirstName();
		} else if (col == 1) {
			value = employees.get(row).getLastName();
		} else if (col == 2) {
			value = employees.get(row).getEmail();
		} else if (col == 3) {
			value = employees.get(row).getUsername();
		} else if (col == 4) {
			value = employees.get(row).getPhone();
		} else if (col == 5) {
			value = employees.get(row).getBirthdayString();
		} else {
			value = employees.get(row).getRole();
		}
		return value;
	}

	public void add(User employee) {
		employees.add(0, employee);
		fireTableDataChanged();
	}

	public void update(User employee) {
		User currentEmployee = find(employee.getUsername());
		currentEmployee.setFirstName(employee.getFirstName());
		currentEmployee.setLastName(employee.getLastName());
		currentEmployee.setEmail(employee.getEmail());
		currentEmployee.setPhone(employee.getPhone());
		currentEmployee.setBirthday(employee.getBirthday());
		currentEmployee.setPassword(employee.getPassword());
		fireTableDataChanged();
	}

	public void remove(String username) {
		User employee = find(username);
		employees.remove(employee);
		fireTableDataChanged();
	}

	public User get(int row) {
		return employees.get(row);
	}

	private User find(String username) {
		for (User employee : employees) {
			if (employee.hasUsername(username)) {
				return employee;
			}
		}
		return null;
	}

}
