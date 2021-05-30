package com.SimbirSoft.SimbirSoft.service;

import com.SimbirSoft.SimbirSoft.Dto.MessageDTO;
import com.SimbirSoft.SimbirSoft.base.CRUDService;

import java.util.List;

public interface MessageService extends CRUDService<MessageDTO, Long> {
    List<MessageDTO> findAll();
    void command(MessageDTO messageDTO);
}
