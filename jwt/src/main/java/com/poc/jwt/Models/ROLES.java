package com.poc.jwt.Models;

public enum ROLES {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    MODERATOR("ROLE_MODERATOR"),
    SUPERVISOR("ROLE_SUPERVISOR");

    private final String role;

    ROLES(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
