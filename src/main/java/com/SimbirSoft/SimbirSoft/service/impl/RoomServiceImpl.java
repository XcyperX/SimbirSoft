package com.SimbirSoft.SimbirSoft.service.impl;

import com.SimbirSoft.SimbirSoft.Dto.RoomDTO;
import com.SimbirSoft.SimbirSoft.Dto.RoomDTO;
import com.SimbirSoft.SimbirSoft.model.Room;
import com.SimbirSoft.SimbirSoft.repository.RoomRepository;
import com.SimbirSoft.SimbirSoft.repository.UserRepository;
import com.SimbirSoft.SimbirSoft.service.RoomService;
import com.SimbirSoft.SimbirSoft.service.UserService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final MapperFacade mapperFacade;

    @Override
    public RoomDTO getById(Long id) {
        if (roomRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такой комнаты!");
        }
        return mapperFacade.map(roomRepository.findById(id).get(), RoomDTO.class);
    }

    @Override
    public RoomDTO save(RoomDTO roomDTO) {
        if (roomDTO != null) {
            Room room = roomRepository.save(mapperFacade.map(roomDTO, Room.class));
            return mapperFacade.map(room, RoomDTO.class);
        }
        return null;
    }

    @Override
    public RoomDTO update(RoomDTO roomDTO) {
        if (roomRepository.findById(roomDTO.getId()).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такой комнаты!");
        }
        Room room = roomRepository.save(mapperFacade.map(roomDTO, Room.class));
        return mapperFacade.map(room, RoomDTO.class);
    }

    @Override
    public void delete(Long id) {
        if (roomRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такой комнаты!");
        }
        roomRepository.deleteById(id);
    }

    @Override
    public List<RoomDTO> findAll() {
        return mapperFacade.mapAsList(roomRepository.findAll(), RoomDTO.class);
    }

    @Override
    public List<RoomDTO> findAllByUserId(Long id) {
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
        return mapperFacade.mapAsList(rooms, RoomDTO.class);
    }

    @Override
    public RoomDTO findAllByName(String name) {
        return mapperFacade.map(roomRepository.findRoomByName(name), RoomDTO.class);
    }
}
