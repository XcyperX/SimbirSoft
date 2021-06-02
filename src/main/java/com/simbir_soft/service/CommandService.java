package com.simbir_soft.service;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.User;

public interface CommandService {
    void renameUserCommand(String[] commands, Message message, User user);
    void banUserCommand(Message message, User user);
    void upAndDownRolesUser(String[] commands, Message message, User user);
    void createRoomCommand(String[] commands, User user);
    void removeRoomCommand(String[] commands, User user);
    void renameRoomCommand(String[] commands, Message message);
    void connectRoomCommand(String[] commands, Message message);
    void disconnectRoomCommand(String[] commands, Message message);
}
