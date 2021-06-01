package com.simbir_soft.model;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public enum Role implements GrantedAuthority, Serializable {
    ADMIN("Администратор"),
    MODERATOR("Модератор"),
    USER("Пользователь");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getNameRole() {
        return role;
    }

    @Override
    public String getAuthority() {
        return name();
    }

    public static String getById(Long id) {
        for (Role role : values()) {
            if (role.ordinal() == id) {
                return role.getNameRole();
            }
        }
        return "UNKNOWN";
    }
}
