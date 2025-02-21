package model;

import java.io.Serializable;

public class Employ implements Serializable{
    private int id;
    private String employno;
    private String name;
    private String username;
    private String password;
    private String role;

    // Constructors
    public Employ() {}

    public Employ(String employno, String name, String username, String password, String role) {
        this.employno = employno;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployno() {
        return employno;
    }

    public void setEmployno(String employno) {
        this.employno = employno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}