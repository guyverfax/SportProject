package model;

import java.io.Serializable;

public class Member implements Serializable{
    private int id;
    private String memberno;
    private String name;
    private String phone;
    private String address;

    // Constructors
    public Member() {}

    public Member(String memberno, String name, String phone, String address) {
        this.memberno = memberno;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemberno() {
        return memberno;
    }

    public void setMemberno(String memberno) {
        this.memberno = memberno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}