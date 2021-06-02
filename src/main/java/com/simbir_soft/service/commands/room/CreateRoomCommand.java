package com.simbir_soft.service.commands.room;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.Room;
import com.simbir_soft.model.User;
import com.simbir_soft.repository.RoomRepository;
import com.simbir_soft.security.SecurityUtils;
import com.simbir_soft.service.CheckServiceByCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreateRoomCommand implements CheckServiceByCommand {
    private final RoomRepository roomRepository;

    private static final String CREATE = "create";

    @Override
    public Boolean checkCommand(String[] command) {
        return command[1].equals(CREATE);
    }

    @Override
    public void applyService(Message message) {
        User user = SecurityUtils.getUserFromContext();
        if (Objects.isNull(user)) {
            throw new RuntimeException("Пользователь не авторизирован!");
        }
        String[] commands = message.getText().split(" ");
        Room room = new Room();
        room.setName(commands[2]);
        room.setUser(user);
        room.setPrivateMassage(false);
        List<User> userDTOS = new ArrayList<>();
        userDTOS.add(new User(user.getId()));
        room.setUsers(userDTOS);
        roomRepository.save(room);
    }
}
