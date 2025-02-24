package model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String productno;
    private String productname;
    private int price;
    private int quantity;

    // Constructors
    public Product() {}

    public Product(String productno, String productname, int price, int quantity) {
        this.productno = productno;
        this.productname = productname;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}