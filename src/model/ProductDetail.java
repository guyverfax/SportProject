package model;

public class ProductDetail {
	private String productno;
	private String productname;
	private int price;
	private int total_stock;
	private int ordered_quantity;
	private int available_stock;
		
	public ProductDetail() {
		super();
	}
	
	public ProductDetail(String productno, String productname, int price, int total_stock, int ordered_quantity,
			int available_stock) {
		super();
		this.productno = productno;
		this.productname = productname;
		this.price = price;
		this.total_stock = total_stock;
		this.ordered_quantity = ordered_quantity;
		this.available_stock = available_stock;
	}



	public String getProductno() {
		return productno;
	}
	public void setProductno(String productno) {
		this.productno = productno;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getTotal_stock() {
		return total_stock;
	}
	public void setTotal_stock(int total_stock) {
		this.total_stock = total_stock;
	}
	public int getOrdered_quantity() {
		return ordered_quantity;
	}
	public void setOrdered_quantity(int ordered_quantity) {
		this.ordered_quantity = ordered_quantity;
	}
	public int getAvailable_stock() {
		return available_stock;
	}
	public void setAvailable_stock(int available_stock) {
		this.available_stock = available_stock;
	}
	
	
	
}
