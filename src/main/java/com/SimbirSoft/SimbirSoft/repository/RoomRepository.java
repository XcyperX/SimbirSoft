package com.SimbirSoft.SimbirSoft.repository;

import com.SimbirSoft.SimbirSoft.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByUserId(Long id);
    Room findRoomByName(String name);
    Room findRoomByNameAndUserId(String name, Long id);
}
