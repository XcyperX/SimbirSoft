package com.simbir_soft.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role implements Serializable {
    ADMIN(Set.of(Permission.ADMIN, Permission.BAN_USER, Permission.MAKE_MODERATOR, Permission.SEND_MESSAGE,
            Permission.DELETE_MESSAGE, Permission.CREATE_ROOM, Permission.CONNECT_USER, Permission.DISCONNECT_USER,
            Permission.RENAME_ROOM)),

    MODERATOR(Set.of(Permission.MODERATOR, Permission.BAN_USER, Permission.SEND_MESSAGE,
            Permission.DELETE_MESSAGE, Permission.CREATE_ROOM, Permission.CONNECT_USER)),

    USER(Set.of(Permission.USER, Permission.SEND_MESSAGE, Permission.CREATE_ROOM,
            Permission.CONNECT_USER, Permission.DISCONNECT_USER,
            Permission.RENAME_ROOM));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthority() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority((permission.getPermission())))
                .collect(Collectors.toSet());
    }


}
