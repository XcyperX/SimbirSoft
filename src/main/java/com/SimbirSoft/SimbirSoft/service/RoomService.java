package com.SimbirSoft.SimbirSoft.service;

import com.SimbirSoft.SimbirSoft.Dto.RoomDTO;
import com.SimbirSoft.SimbirSoft.base.CRUDService;

import java.util.List;

public interface RoomService extends CRUDService<RoomDTO, Long> {
    List<RoomDTO> findAll();
    List<RoomDTO> findAllByUserId(Long id);
    RoomDTO findAllByName(String name);
}
