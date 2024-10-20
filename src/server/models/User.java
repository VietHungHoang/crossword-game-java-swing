package server.models;

import java.io.Serializable;

public class User implements Serializable {
    private Long id;
    private String email;
    private String password;
    private String phone;
    private String role;
    private String userName;

    public User(Long id, String email, String password, String phone, String role, String userName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}