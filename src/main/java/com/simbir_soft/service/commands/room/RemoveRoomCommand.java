package com.simbir_soft.service.commands.room;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.User;
import com.simbir_soft.repository.RoomRepository;
import com.simbir_soft.security.SecurityUtils;
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
    public void applyService(Message message) {
        User user = SecurityUtils.getUserFromContext();
        if (Objects.isNull(user)) {
            throw new RuntimeException("Пользователь не авторизирован!");
        }
        String[] commands = message.getText().split(" ");
        roomRepository.deleteById(roomRepository.findRoomByNameAndUserId(commands[2], user.getId()).getId());
    }
}
