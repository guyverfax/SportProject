package model;

public class PorderDetail {
	private String porderno;
	private String orderdate;
	private String memberno;
	private String membername;
	private String employno;
	private String employname;
	private String productno;
	private String productname;
	private int price;
	private int amount;
	private int subtotal;
	private int stockquantity;
	
	
	public PorderDetail() {
		super();
	}

	public PorderDetail(String porderno, String orderdate, String memberno, String membername, String employno,
			String employname, String productno, String productname, int price, int amount, int subtotal,
			int stockquantity) {
		super();
		this.porderno = porderno;
		this.orderdate = orderdate;
		this.memberno = memberno;
		this.membername = membername;
		this.employno = employno;
		this.employname = employname;
		this.productno = productno;
		this.productname = productname;
		this.price = price;
		this.amount = amount;
		this.subtotal = subtotal;
		this.stockquantity = stockquantity;
	}

	public String getPorderno() {
		return porderno;
	}

	public void setPorderno(String porderno) {
		this.porderno = porderno;
	}

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public String getMemberno() {
		return memberno;
	}

	public void setMemberno(String memberno) {
		this.memberno = memberno;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getEmployno() {
		return employno;
	}

	public void setEmployno(String employno) {
		this.employno = employno;
	}

	public String getEmployname() {
		return employname;
	}

	public void setEmployname(String employname) {
		this.employname = employname;
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}

	public int getStockquantity() {
		return stockquantity;
	}

	public void setStockquantity(int stockquantity) {
		this.stockquantity = stockquantity;
	}
    
	
}
