package com.painting.vo.user;

import java.io.Serializable;

public class JwtAuthenticationRequestVo implements Serializable {
    private String username;
    private String password;

    public JwtAuthenticationRequestVo() {
    }

    public JwtAuthenticationRequestVo(String username, String password) {
        this.username = username;
        this.password = password;
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
}
