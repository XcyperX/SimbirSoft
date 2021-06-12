package com.simbir_soft.controller;

import com.simbir_soft.dto.MessageDTO;
import com.simbir_soft.model.Message;
import com.simbir_soft.model.User;
import com.simbir_soft.security.SecurityUtils;
import com.simbir_soft.service.commands.threads.*;
import com.simbir_soft.service.MessageService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageApiController {
    private final MessageService messageService;
    private final ChoiceService choiceService;

    private final MapperFacade mapper;

    private final MonitorForCommands monitor;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @PostConstruct
    void startCheck() {
        StartCommandInQueue startCommandInQueue = new StartCommandInQueue(monitor);
        new Thread(startCommandInQueue).start();
    }

    @PostMapping("/message")
    public void createMessage(@RequestBody @Valid MessageDTO messageDTO) {
//        User user = SecurityUtils.getUserFromContext();
//        if (messageDTO.getText().split(" ")[0].contains("//")) {
//            AddCommandInQueue addCommandInQueue = new AddCommandInQueue(mapper.map(messageDTO, Message.class), monitor, user);
//            new Thread(addCommandInQueue).start();
//        }
        if (messageDTO.getText().split(" ")[0].contains("//")) {
            executorService.submit(() -> {
                choiceService.checkMessage(mapper.map(messageDTO, Message.class));
            });
//            Thread thread = new Thread(new CheckThread(choiceService, mapper.map(messageDTO, Message.class)));
//            executorService.submit(thread);
        }
        if (messageDTO.getUser().getId() != null) {
            messageService.save(mapper.map(messageDTO, Message.class));
        }
    }

    @PutMapping("/message/{id}")
    public Message updateMessage(@PathVariable("id") Long id, @RequestBody MessageDTO messageDTO) {
        messageDTO.setId(id);
        return messageService.update(mapper.map(messageDTO, Message.class));
    }

    @DeleteMapping("/message/{id}")
    public void deleteMessage(@PathVariable("id") Long id) {
        messageService.delete(id);
    }

    @GetMapping("/message")
    public ResponseEntity<?> getMessage() {
        return ResponseEntity.ok(messageService.findAll());
    }
}
