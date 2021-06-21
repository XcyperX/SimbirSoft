package com.simbir_soft.controller;

import com.simbir_soft.dto.MessageDTO;
import com.simbir_soft.model.Message;
import com.simbir_soft.model.User;
import com.simbir_soft.service.commands.ChoiceService;
import com.simbir_soft.service.MessageService;
import com.simbir_soft.service.commands.proxy.CommandInvocationHandler;
import com.simbir_soft.service.commands.proxy.ProxyFactory;
import com.simbir_soft.service.commands.thread.MyMonitorThread;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageApiController {
    private final MessageService messageService;
    private final ChoiceService choiceService;

    private final MapperFacade mapper;

    private MyMonitorThread myMonitorThread;

    @PostConstruct
    private void startMonitor() throws InterruptedException {
        myMonitorThread = new MyMonitorThread();
    }


    @PostMapping("/message")
    public void createMessage(@AuthenticationPrincipal User user, @RequestBody @Valid MessageDTO messageDTO) {
        Message message = mapper.map(messageDTO, Message.class);
        ProxyFactory.newInstance(messageService, new CommandInvocationHandler(message, user, choiceService, messageService, myMonitorThread)).save(message);
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
