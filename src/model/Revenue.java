package model;

public class Revenue {
	private int year;
	private int month;
	private int revenue;
	
	public Revenue() {
		super();
	}

	public Revenue(int year, int month, int revenue) {
		super();
		this.year = year;
		this.month = month;
		this.revenue = revenue;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getRevenue() {
		return revenue;
	}
	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}
	

}
