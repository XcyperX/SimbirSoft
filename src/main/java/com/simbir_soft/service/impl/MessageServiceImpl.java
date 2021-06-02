package com.simbir_soft.service.impl;

import com.simbir_soft.model.Message;
import com.simbir_soft.repository.MessageRepository;
import com.simbir_soft.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    private static final String YBOT = "//yBot";

    @Override
    public Message getById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ошибка, нет такого сообщения!"));
    }

    @Override
    public Message save(Message message) {
        if (Objects.isNull(message)) {
            throw new RuntimeException("Ошибка, сообщение пустое!");
        }
        return messageRepository.save(message);
    }

    @Override
    public Message update(Message message) {
        getById(message.getId());
        return messageRepository.save(message);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        messageRepository.deleteById(id);
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }
}
