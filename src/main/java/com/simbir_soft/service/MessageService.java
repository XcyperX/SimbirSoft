package com.simbir_soft.service;

import com.simbir_soft.Dto.MessageDTO;

import java.util.List;

public interface MessageService extends CRUDService<MessageDTO, Long> {
    List<MessageDTO> findAll();
    void command(MessageDTO messageDTO);
}
