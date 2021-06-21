package com.simbir_soft.service.commands.room;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.Room;
import com.simbir_soft.model.User;
import com.simbir_soft.repository.RoomRepository;
import com.simbir_soft.service.CheckServiceByCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class RenameRoomCommand implements CheckServiceByCommand {
    private final RoomRepository roomRepository;

    private static final String ROOM = "//room";
    private static final String RENAME = "rename";

    @Override
    public Boolean checkCommand(String[] command) {
        return command[0].equals(ROOM) && command[1].equals(RENAME);
    }

    @Override
    public void applyService(Message message, User user) {
        String[] commands = message.getText().split(" ");
        Room room = roomRepository.findRoomByName(roomRepository.findById(message.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Комната не найдена!")).getName());
        checkAccess(room, user);
        room.setName(commands[2]);
        roomRepository.save(room);
    }

    private void checkAccess(Room room, User user) {
        if (Objects.isNull(user)) {
            throw new RuntimeException("Пользователь не авторизирован!");
        }
        if (!room.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("У пользователя недостаточно прав!");
        }
    }
}
