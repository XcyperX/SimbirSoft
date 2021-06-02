package com.simbir_soft.service;


import com.simbir_soft.model.Message;

import java.util.List;

public interface MessageService extends CRUDService<Message, Long> {
    List<Message> findAll();
}
