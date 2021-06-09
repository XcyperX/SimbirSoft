package com.simbir_soft.model;

public enum Permission {
    ADMIN("Администратор"),
    MODERATOR("Модератор"),
    USER("Пользователь"),
    BAN_USER("user:ban"),
    RENAME_USER("user:rename"),
    MAKE_MODERATOR("user:moderator"),
    SEND_MESSAGE("message:send"),
    DELETE_MESSAGE("message:delete"),
    CREATE_ROOM("room:crete"),
    CONNECT_USER("room:connect"),
    // TODO Вопросы по владельцу комнаты
    DISCONNECT_USER("room:disconnect"),
    RENAME_ROOM("room:rename"),
    DELETE_ROOM("room:delete");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
