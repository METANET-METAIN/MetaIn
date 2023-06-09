package com.metain.web.domain;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {

    ROLE_SW("ROLE_SW"),
    ROLE_DR("ROLE_DR"),
    ROLE_GJ("ROLE_GJ"),
    ROLE_CJ("ROLE_CJ"),
    ROLE_HR("ROLE_HR"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }

    @Override
    public String getAuthority(){
        return role;
    }

}
