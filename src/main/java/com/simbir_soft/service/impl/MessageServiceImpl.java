package com.simbir_soft.service.impl;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.Role;
import com.simbir_soft.model.Room;
import com.simbir_soft.model.User;
import com.simbir_soft.repository.MessageRepository;
import com.simbir_soft.repository.RoomRepository;
import com.simbir_soft.security.SecurityUtils;
import com.simbir_soft.service.MessageService;
import com.simbir_soft.service.RoomService;
import com.simbir_soft.service.UserService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final UserService userService;
    private final RoomService roomService;
    private final MapperFacade mapperFacade;

    private static final String ROOM = "//room";
    private static final String USER = "//user";
    private static final String YBOT = "//yBot";

    private static final String CREATE = "create";
    private static final String REMOVE = "remove";
    private static final String CONNECT = "connect";
    private static final String DISCONNECT = "disconnect";

    private static final String RENAME = "rename";

    private static final String BAN = "ban";
    private static final String MODERATOR = "moderator1";

    private static final String FIND = "find";
    private static final String HELP = "help";

    @Override
    public Message getById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ошибка, нет такого сообщения!"));
    }

    @Override
    public Message save(Message message) {
        if (Objects.isNull(message)) {
            throw new RuntimeException("Ошибка, сообщение пустое!");
        }
        return messageRepository.save(message);
    }

    @Override
    public Message update(Message message) {
        getById(message.getId());
        return messageRepository.save(message);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        messageRepository.deleteById(id);
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public void command(Message message) {
        User userFromContext = SecurityUtils.getUserFromContext();
        String[] commands = message.getText().split(" ");
        if (userFromContext != null)
        switch (commands[0]) {
            case (ROOM):
                roomActions(commands, message, userFromContext);
                break;
            case (USER):
                userActions(commands, message, userFromContext);
                break;
            case (YBOT):
                yBotActions(commands, message, userFromContext);
                break;
        }
    }

    private void roomActions(String[] commands, Message message, User userFromContext) {
        switch (commands[1]) {
            case (CREATE):
                createRoomCommand(commands, userFromContext);
                break;
            case (REMOVE):
                removeRoomCommand(commands, userFromContext);
                break;
            case (RENAME):
                renameRoomCommand(commands, message);
                break;
            case (CONNECT):
                connectRoomCommand(commands, message);
                break;
            case (DISCONNECT):
                disconnectRoomCommand(commands, message);
                break;
        }
    }

    private void userActions(String[] commands, Message message, User userFromContext) {
        switch (commands[1]) {
            case (RENAME):
                renameUserCommand(commands, message, userFromContext);
                break;
            case (BAN):
                banUserCommand(message, userFromContext);
                break;
            case (MODERATOR):
                upAndDownRolesUser(commands, message, userFromContext);
                break;
        }
    }

    private void yBotActions(String[] commands, Message message, User userFromContext) {
        switch (commands[1]) {
            case (RENAME):
                break;
            case (BAN):
                break;
        }
    }

    private String getLoginUserFromCommand(Message message) {
        return message.getText().split("-l")[1].split("-m")[0].strip();
    }

    private Long getMinutesBanUser(Message message) {
        return Long.parseLong(message.getText().split("-m")[1].strip());
    }

    private Boolean checkAccessUser(Message message, User user) {
        return roomRepository.findById(message.getId()).get().getUser().getId().equals(user.getId()) ||
                user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.MODERATOR);
    }

    //TODO Работа с пользователями
    private void renameUserCommand(String[] commands, Message message, User user) {
        if (checkAccessUser(message, user)) {
            User updateUser = userService.findByLogin(commands[2]);
            updateUser.setLogin(getLoginUserFromCommand(message));
            userService.update(updateUser);
        }
    }

    private void banUserCommand(Message message, User user) {
        if (checkAccessUser(message, user)) {
            User updateUser = userService.findByLogin(getLoginUserFromCommand(message));
            updateUser.setBan(true);
            updateUser.setEndBanDate(LocalDateTime.now().plusMinutes(getMinutesBanUser(message)));
            userService.update(updateUser);
        }
    }

    private void upAndDownRolesUser(String[] commands, Message message, User user) {
        if (checkAccessUser(message, user)) {
            User updateUser = userService.findByLogin(commands[2]);
            if (commands[3].equals("-n")) {
                updateUser.setRole(Role.MODERATOR);
            } else if (commands[3].equals("-d")) {
                updateUser.setRole(Role.USER);
            }
            userService.update(updateUser);
        }
    }

    //TODO Работа с комнатами
    private void createRoomCommand(String[] commands, User user) {
        Room room = new Room();
        room.setName(commands[2]);
        room.setUser(user);
        room.setPrivateMassage(false);
        List<User> userDTOS = new ArrayList<>();
        userDTOS.add(userService.getById(user.getId()));
        room.setUsers(userDTOS);
        roomService.save(room);
    }

    private void removeRoomCommand(String[] commands, User user) {
        roomService.delete(roomRepository.findRoomByNameAndUserId(commands[2], user.getId()).getId());
    }

    private void renameRoomCommand(String[] commands, Message message) {
        Room room = roomService.findAllByName(roomRepository.findById(message.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Комната не найдена!")).getName());
        room.setName(commands[2]);
        roomService.update(room);
    }

    private void connectRoomCommand(String[] commands, Message message) {
        Room room = roomService.findAllByName(commands[2]);
        room.getUsers().add(userService.findByLogin(getLoginUserFromCommand(message)));
        roomService.update(room);
    }

    private void disconnectRoomCommand(String[] commands, Message message) {
        Room room = roomService.findAllByName(commands[2]);
        room.getUsers().remove(userService.findByLogin(getLoginUserFromCommand(message)));
        roomService.update(room);
    }
}
