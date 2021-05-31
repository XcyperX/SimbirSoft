package com.simbir_soft.service.impl;

import com.simbir_soft.Dto.MessageDTO;
import com.simbir_soft.Dto.RoomDTO;
import com.simbir_soft.Dto.UserDTO;
import com.simbir_soft.model.Message;
import com.simbir_soft.model.Role;
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
    private static final String MODERATOR = "moderator";

    private static final String FIND = "find";
    private static final String HELP = "help";

    @Override
    public MessageDTO getById(Long id) {
        if (messageRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такого сообщения!");
        }
        return mapperFacade.map(messageRepository.findById(id).get(), MessageDTO.class);
    }

    @Override
    public MessageDTO save(MessageDTO messageDTO) {
        if (messageDTO != null) {
            Message message = messageRepository.save(mapperFacade.map(messageDTO, Message.class));
            return mapperFacade.map(message, MessageDTO.class);
        }
        return null;
    }

    @Override
    public MessageDTO update(MessageDTO messageDTO) {
        if (messageRepository.findById(messageDTO.getId()).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такого сообщения!");
        }
        Message message = messageRepository.save(mapperFacade.map(messageDTO, Message.class));
        return mapperFacade.map(message, MessageDTO.class);
    }

    @Override
    public void delete(Long id) {
        if (messageRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такого сообщения!");
        }
        messageRepository.deleteById(id);
    }

    @Override
    public List<MessageDTO> findAll() {
        return mapperFacade.mapAsList(messageRepository.findAll(), MessageDTO.class);
    }

    @Override
    public void command(MessageDTO messageDTO) {
        User userFromContext = SecurityUtils.getUserFromContext();
        String[] commands = messageDTO.getText().split(" ");
        if (userFromContext != null)
        switch (commands[0]) {
            case (ROOM):
                roomActions(commands, messageDTO, userFromContext);
                break;
            case (USER):
                userActions(commands, messageDTO, userFromContext);
                break;
            case (YBOT):
                yBotActions(commands, messageDTO, userFromContext);
                break;
        }
    }

    private void roomActions(String[] commands, MessageDTO messageDTO, User userFromContext) {
        switch (commands[1]) {
            case (CREATE):
                createRoomCommand(commands, userFromContext);
                break;
            case (REMOVE):
                removeRoomCommand(commands, userFromContext);
                break;
            case (RENAME):
                renameRoomCommand(commands, messageDTO);
                break;
            case (CONNECT):
                connectRoomCommand(commands, messageDTO);
                break;
            case (DISCONNECT):
                disconnectRoomCommand(commands, messageDTO);
                break;
        }
    }

    private void userActions(String[] commands, MessageDTO messageDTO, User userFromContext) {
        switch (commands[1]) {
            case (RENAME):
                renameUserCommand(commands, messageDTO, userFromContext);
                break;
            case (BAN):
                banUserCommand(messageDTO, userFromContext);
                break;
            case (MODERATOR):
                upAndDownRolesUser(commands, messageDTO, userFromContext);
                break;
        }
    }

    private void yBotActions(String[] commands, MessageDTO messageDTO, User userFromContext) {
        switch (commands[1]) {
            case (RENAME):
                break;
            case (BAN):
                break;
        }
    }

    private String getLoginUserFromCommand(MessageDTO messageDTO) {
        return messageDTO.getText().split("-l")[1].split("-m")[0].strip();
    }

    private Long getMinutesBanUser(MessageDTO messageDTO) {
        return Long.parseLong(messageDTO.getText().split("-m")[1].strip());
    }

    private Boolean checkAccessUser(MessageDTO messageDTO, User user) {
        return roomRepository.findById(messageDTO.getRoomId()).get().getUser().getId().equals(user.getId()) ||
                user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.MODERATOR);
    }

    //TODO Работа с пользователями
    private void renameUserCommand(String[] commands, MessageDTO messageDTO, User user) {
        if (checkAccessUser(messageDTO, user)) {
            UserDTO updateUserDTO = userService.findByLogin(commands[2]);
            updateUserDTO.setLogin(getLoginUserFromCommand(messageDTO));
            userService.update(updateUserDTO);
        }
    }

    private void banUserCommand(MessageDTO messageDTO, User user) {
        if (checkAccessUser(messageDTO, user)) {
            UserDTO updateUserDTO = userService.findByLogin(getLoginUserFromCommand(messageDTO));
            updateUserDTO.setBan(true);
            updateUserDTO.setEndBanDate(LocalDateTime.now().plusMinutes(getMinutesBanUser(messageDTO)));
            userService.update(updateUserDTO);
        }
    }

    private void upAndDownRolesUser(String[] commands, MessageDTO messageDTO, User user) {
        if (checkAccessUser(messageDTO, user)) {
            UserDTO updateUserDTO = userService.findByLogin(commands[2]);
            if (commands[3].equals("-n")) {
                updateUserDTO.setRole(Role.MODERATOR.name());
            } else if (commands[3].equals("-d")) {
                updateUserDTO.setRole(Role.USER.name());
            }
            userService.update(updateUserDTO);
        }
    }

    //TODO Работа с комнатами
    private void createRoomCommand(String[] commands, User user) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName(commands[2]);
        roomDTO.setUserId(user.getId());
        roomDTO.setPrivateMassage(false);
        List<UserDTO> userDTOS = new ArrayList<>();
        userDTOS.add(userService.getById(user.getId()));
        roomDTO.setUsers(userDTOS);
        roomService.save(roomDTO);
    }

    private void removeRoomCommand(String[] commands, User user) {
        roomService.delete(roomRepository.findRoomByNameAndUserId(commands[2], user.getId()).getId());
    }

    private void renameRoomCommand(String[] commands, MessageDTO messageDTO) {
        RoomDTO roomDTO = roomService.findAllByName(roomRepository.findById(messageDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Комната не найдена!")).getName());
        roomDTO.setName(commands[2]);
        roomService.update(roomDTO);
    }

    private void connectRoomCommand(String[] commands, MessageDTO messageDTO) {
        RoomDTO roomDTO = roomService.findAllByName(commands[2]);
        roomDTO.getUsers().add(userService.findByLogin(getLoginUserFromCommand(messageDTO)));
        roomService.update(roomDTO);
    }

    private void disconnectRoomCommand(String[] commands, MessageDTO messageDTO) {
        RoomDTO roomDTO = roomService.findAllByName(commands[2]);
        roomDTO.getUsers().remove(userService.findByLogin(getLoginUserFromCommand(messageDTO)));
        roomService.update(roomDTO);
    }
}
