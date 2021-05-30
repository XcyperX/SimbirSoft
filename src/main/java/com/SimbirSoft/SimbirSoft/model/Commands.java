package com.SimbirSoft.SimbirSoft.model;

public enum Commands {
    ROOM("//room"),
    USER("//user"),
    YBOT("//yBot"),
    HELP("//help"),
    CREATE("create"),
    REMOVE("remove"),
    RENAME("rename"),
    CONNECT("connect"),
    DISCONNECT("disconnect"),
    BAN("ban"),
    MODERATOR("moderator"),
    FIND("find");

    private final String command;

    Commands(String command) {
        this.command = command;
    }

    public String getNameCommand() {
        return command;
    }
}
