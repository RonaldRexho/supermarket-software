package repository.entity;

public enum Role {

	CASHIER(1, "Cashier"), ADMIN(2, "Administrator");
	
	private int id;
	private String name;
	
	Role(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Role getRole(int id) {
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
