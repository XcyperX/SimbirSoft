package com.simbir_soft.service.commands.room;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.Role;
import com.simbir_soft.model.Room;
import com.simbir_soft.model.User;
import com.simbir_soft.repository.RoomRepository;
import com.simbir_soft.service.CheckServiceByCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RemoveRoomCommand implements CheckServiceByCommand {
    private final RoomRepository roomRepository;

    private static final String REMOVE = "remove";

    @Override
    public Boolean checkCommand(String[] command) {
        return command[1].equals(REMOVE);
    }

    @Override
    public void applyService(Message message, User user) {
        if (Objects.isNull(user)) {
            throw new RuntimeException("Пользователь не авторизирован!");
        }
        String[] commands = message.getText().split(" ");
        Room room = roomRepository.findRoomByNameAndUserId(commands[2], user.getId());
        checkAccess(room, user);
        roomRepository.deleteById(room.getId());
    }

    private void checkAccess(Room room, User user) {
        if (Objects.isNull(user)) {
            throw new RuntimeException("Пользователь не авторизирован!");
        }
        if (!room.getUser().getId().equals(user.getId()) || !user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("У пользователя недостаточно прав!");
        }
    }
}
