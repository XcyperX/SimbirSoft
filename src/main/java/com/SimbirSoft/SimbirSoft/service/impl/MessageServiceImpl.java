package com.SimbirSoft.SimbirSoft.service.impl;

import com.SimbirSoft.SimbirSoft.Dto.MessageDTO;
import com.SimbirSoft.SimbirSoft.model.Message;
import com.SimbirSoft.SimbirSoft.repository.MessageRepository;
import com.SimbirSoft.SimbirSoft.service.MessageService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final MapperFacade mapperFacade;

    @Override
    public MessageDTO getById(Long id) {
        if (messageRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такого сообщения!");
        }
        return mapperFacade.map(messageRepository.findById(id).get(), MessageDTO.class);
    }

    @Override
    public MessageDTO save(MessageDTO messageDTO) {
        if (messageDTO != null) {
            Message message = messageRepository.save(mapperFacade.map(messageDTO, Message.class));
            return mapperFacade.map(message, MessageDTO.class);
        }
        return null;
    }

    @Override
    public MessageDTO update(MessageDTO messageDTO) {
        if (messageRepository.findById(messageDTO.getId()).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такого сообщения!");
        }
        Message message = messageRepository.save(mapperFacade.map(messageDTO, Message.class));
        return mapperFacade.map(message, MessageDTO.class);
    }

    @Override
    public void delete(Long id) {
        if (messageRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такого сообщения!");
        }
        messageRepository.deleteById(id);
    }

    @Override
    public List<MessageDTO> findAll() {
        return mapperFacade.mapAsList(messageRepository.findAll(), MessageDTO.class);
    }

}
