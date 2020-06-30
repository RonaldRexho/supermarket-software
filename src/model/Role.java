package model;

public enum Role {

	CASHIER(2, "Cashier"), ADMIN(1, "Administrator");
	
	private int id;
	private String name;
	
	Role(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public static Role getRole(int id) {
		if(id == 1) {
			return CASHIER;
		}
		return ADMIN;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	
	
}
