package com.simbir_soft.service.commands.room;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.Room;
import com.simbir_soft.model.User;
import com.simbir_soft.repository.RoomRepository;
import com.simbir_soft.repository.UserRepository;
import com.simbir_soft.service.CheckServiceByCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ConnectRoomCommand implements CheckServiceByCommand {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    private static final String CONNECT = "connect";


    @Override
    public Boolean checkCommand(String[] command) {
        return command[1].equals(CONNECT);
    }

    @Override
    public void applyService(Message message, User user) {
        String[] commands = message.getText().split(" ");
        Room room = roomRepository.findRoomByName(commands[2]);
        checkAccess(room, user);
        room.getUsers().add(userRepository.findByLogin(getLoginUserFromCommand(message)).orElseThrow(
                () -> new RuntimeException("Пользователь не найден!")));
        roomRepository.save(room);
    }

    private String getLoginUserFromCommand(Message message) {
        return message.getText().split("-l")[1].strip();
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
