package com.simbir_soft.service;

import com.simbir_soft.Dto.RoomDTO;

import java.util.List;

public interface RoomService extends CRUDService<RoomDTO, Long> {
    List<RoomDTO> findAll();
    List<RoomDTO> findAllByUserId(Long id);
    RoomDTO findAllByName(String name);
}
