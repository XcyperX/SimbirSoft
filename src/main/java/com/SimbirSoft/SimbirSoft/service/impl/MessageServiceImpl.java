package com.SimbirSoft.SimbirSoft.service.impl;

import com.SimbirSoft.SimbirSoft.Dto.MessageDTO;
import com.SimbirSoft.SimbirSoft.Dto.RoomDTO;
import com.SimbirSoft.SimbirSoft.Dto.UserDTO;
import com.SimbirSoft.SimbirSoft.model.Message;
import com.SimbirSoft.SimbirSoft.model.User;
import com.SimbirSoft.SimbirSoft.repository.MessageRepository;
import com.SimbirSoft.SimbirSoft.repository.RoomRepository;
import com.SimbirSoft.SimbirSoft.repository.UserRepository;
import com.SimbirSoft.SimbirSoft.security.SecurityUtils;
import com.SimbirSoft.SimbirSoft.service.MessageService;
import com.SimbirSoft.SimbirSoft.service.RoomService;
import com.SimbirSoft.SimbirSoft.service.UserService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

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
                break;
        }
    }

    private String getLoginUserFromCommand(MessageDTO messageDTO) {
        return messageDTO.getText().split("-l")[1].strip();
    }

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
