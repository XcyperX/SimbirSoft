package com.simbir_soft.repository;

import com.simbir_soft.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByUserId(Long id);
    Room findRoomByName(String name);
    Room findRoomByNameAndUserId(String name, Long id);
}
