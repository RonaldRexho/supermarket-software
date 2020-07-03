package model;

import java.sql.Date;

public class Product {

	private int id;
	private String name;
	private String code;
	private int supplierId;
	private int category;
	private int quantity;
	private double price;
	private double purchesedPrice;
	private Date expiredOn;
	private Date purchasedDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPurchesedPrice() {
		return purchesedPrice;
	}

	public void setPurchesedPrice(double purchesedPrice) {
		this.purchesedPrice = purchesedPrice;
	}

	public Date getExpiredOn() {
		return expiredOn;
	}

	public void setExpiredOn(Date expiredOn) {
		this.expiredOn = expiredOn;
	}

	public Date getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(Date purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", code=" + code + ", quantity=" + quantity + ", price=" + price + "]";
	}


	
	
}
