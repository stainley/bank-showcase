package com.salapp.bank.userservice.model;

public enum  Role {
    ADMIN("ROLE_ADMIN"),
    MODERATOR("ROLE_MODERATOR"),
    USER("ROLE_USER");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public  String getAuthority() {
        return authority;
    }
}
