package model;

public class PorderSummary {
	private String porderno;
	private String orderdate;
	private String memberno;
	private String membername;
	private String employno;
	private String employname;
	private String products;
	private int totalprice;
	
	
	public PorderSummary() {
		super();
	}
    
	public PorderSummary(String porderno, String orderdate, String memberno, String membername, String employno,
			String employname, String products, int totalprice) {
		super();
		this.porderno = porderno;
		this.orderdate = orderdate;
		this.memberno = memberno;
		this.membername = membername;
		this.employno = employno;
		this.employname = employname;
		this.products = products;
		this.totalprice = totalprice;
	}

	public PorderSummary(String porderno, String memberno, String membername, String employno, String employname,
			String products, int totalprice) {
		super();
		this.porderno = porderno;
		this.memberno = memberno;
		this.membername = membername;
		this.employno = employno;
		this.employname = employname;
		this.products = products;
		this.totalprice = totalprice;
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
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	public int getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}	
}
