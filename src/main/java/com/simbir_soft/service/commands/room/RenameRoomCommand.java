package com.simbir_soft.service.commands.room;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.Room;
import com.simbir_soft.repository.RoomRepository;
import com.simbir_soft.service.CheckServiceByCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
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
    public void applyService(Message message) {
//        User user = SecurityUtils.getUserFromContext();
//        if (Objects.isNull(user)) {
//            throw new RuntimeException("Пользователь не авторизирован!");
//        }
        String[] commands = message.getText().split(" ");
        Room room = roomRepository.findRoomByName(roomRepository.findById(message.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Комната не найдена!")).getName());
        room.setName(commands[2]);
        roomRepository.save(room);
    }
}
