package repository.entity;

import java.sql.Date;
import java.time.LocalDate;

public class User {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	

	private String password;
	private String phone;
	private Date birthday;
	private String username;
	private double salary;
	private boolean active;
	private Role role;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
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
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isAdmin() {
		return role.getId() == 1;
	}
	
	public boolean isCashier() {
		return !isAdmin();
	}
	
	public String getRole() {
		return role.getName();
	}
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getName() {
		return firstName + " " + lastName;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", phone=" + phone + ", birthday=" + birthday + ", username=" + username
				+ ", salary=" + salary + ", active=" + active + ", role=" + role + "]";
	}
	
	
	
}
