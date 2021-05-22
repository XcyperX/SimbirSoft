package com.SimbirSoft.SimbirSoft.repository;

import com.SimbirSoft.SimbirSoft.Dto.RoomDTO;
import com.SimbirSoft.SimbirSoft.model.Room;
import com.SimbirSoft.SimbirSoft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByUserId(Long id);
    Room findRoomByName(String name);
}
