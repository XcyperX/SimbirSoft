package com.simbir_soft.service;


import com.simbir_soft.model.Room;

import java.util.List;

public interface RoomService extends CRUDService<Room, Long> {
    List<Room> findAll();
    List<Room> findAllByUserId(Long id);
    Room findAllByName(String name);
}
