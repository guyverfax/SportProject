package model;

import java.io.Serializable;

public class Porder implements Serializable{
	private int id;
	private String porderno;
	private String productno;
	private String memberno;
	private String employno;
	private int amount;
	private int sum;
	
	
	public Porder() {
		super();
	}

	public Porder(String porderno, String productno, String memberno, String employno, int amount) {
		super();
		this.porderno = porderno;
		this.productno = productno;
		this.memberno = memberno;
		this.employno = employno;
		this.amount = amount;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPorderno() {
		return porderno;
	}
	public void setPorderno(String porderno) {
		this.porderno = porderno;
	}
	public String getProductno() {
		return productno;
	}
	public void setProductno(String productno) {
		this.productno = productno;
	}
	public String getMemberno() {
		return memberno;
	}
	public void setMemberno(String memberno) {
		this.memberno = memberno;
	}
	public String getEmployno() {
		return employno;
	}
	public void setEmployno(String employno) {
		this.employno = employno;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	
}
