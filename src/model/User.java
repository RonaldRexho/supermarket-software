package model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class User {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private char[] password;
	private String phone;
	private Date birthday;
	private String username;
	private int roleId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public String Phone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAdmin() {
		return roleId == 1;
	}

	public String getRoleName() {
		if (roleId == 1) {
			return "Admin";
		}
		return "Cashier";
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getRoleId() {
		return roleId;
	}

	public String getBirthdayString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(birthday);
	}

	public String getName() {
		return firstName + " " + lastName;
	}

	public boolean hasUsername(String username) {
		return this.username.equals(username);
	}

	public String getPhone() {
		return phone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
