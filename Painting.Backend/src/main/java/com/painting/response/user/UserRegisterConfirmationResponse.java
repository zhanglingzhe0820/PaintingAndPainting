package com.painting.response.user;

import com.painting.security.jwt.JwtRole;

import java.io.Serializable;
import java.util.Collection;

public class UserRegisterConfirmationResponse implements Serializable {
    private String token;
    private Collection<JwtRole> jwtRoles;
    private String email;

    public UserRegisterConfirmationResponse() {
    }

    public UserRegisterConfirmationResponse(String token, Collection<JwtRole> jwtRoles, String email) {
        this.token = token;
        this.jwtRoles = jwtRoles;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Collection<JwtRole> getJwtRoles() {
        return jwtRoles;
    }

    public void setJwtRoles(Collection<JwtRole> jwtRoles) {
        this.jwtRoles = jwtRoles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
