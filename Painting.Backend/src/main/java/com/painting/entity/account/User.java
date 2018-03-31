package com.painting.entity.account;


import com.painting.entity.annotation.Column;
import com.painting.entity.annotation.ElementCollection;
import com.painting.entity.annotation.Id;
import com.painting.entity.annotation.Table;
import trapx00.tagx00.entity.Entity;
import trapx00.tagx00.entity.annotation.*;

import java.util.List;

@Table(name = "user")
public class User extends Entity {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @ElementCollection(targetClass = Role.class)
    @Column(name = "roles")
    private List<Role> roles;

    public User() {
    }

    public User(String username, String password, String email, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
