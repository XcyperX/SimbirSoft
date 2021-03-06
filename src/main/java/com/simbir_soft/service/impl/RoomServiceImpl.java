package com.simbir_soft.service.impl;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.Room;
import com.simbir_soft.model.User;
import com.simbir_soft.repository.RoomRepository;
import com.simbir_soft.service.RoomService;
import com.simbir_soft.service.commands.ChoiceCommands;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final MapperFacade mapperFacade;
    private final ChoiceCommands choiceCommands;

    private static final String ROOM = "//room";

    @Override
    public Boolean checkCommand(String[] command) {

        return command[0].equals(ROOM);
    }

    @Override
    public void applyService(Message message, User user) {
        choiceCommands.checkCommand(message, user);
    }

    @Override
    public Room getById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ошибка, нет такой комнаты!"));
    }

    @Override
    public Room save(Room room) {
        if (Objects.isNull(room)) {
            throw new RuntimeException("Нет данных по комнате");
        }
        return roomRepository.save(room);
    }

    @Override
    public Room update(Room room) {
        getById(room.getId());
        return roomRepository.save(room);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        roomRepository.deleteById(id);
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> findAllByUserId(Long id) {
        List<Room> rooms = new ArrayList<>();
        roomRepository.findAll().forEach(room -> {
            if (room.getUser().getId().equals(id)) {
                rooms.add(room);
            } else {
                room.getUsers().forEach(user -> {
                    if (user.getId().equals(id)) {
                        rooms.add(room);
                    }
                });
            }
        });
        return rooms;
    }

    @Override
    public Room findAllByName(String name) {
        return roomRepository.findRoomByName(name);
    }
}
