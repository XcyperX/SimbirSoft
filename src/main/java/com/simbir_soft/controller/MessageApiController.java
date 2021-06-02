package com.simbir_soft.controller;

import com.simbir_soft.dto.MessageDTO;
import com.simbir_soft.model.Message;
import com.simbir_soft.service.ChoiceService;
import com.simbir_soft.service.MessageService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageApiController {
    private final MessageService messageService;
    private final ChoiceService choiceService;

    private final MapperFacade mapper;

    @PostMapping("/message")
    public void createMessage(@RequestBody @Valid MessageDTO messageDTO) {
        choiceService.run(mapper.map(messageDTO, Message.class));
//        if (messageDTO.getText().split(" ")[0].contains("//")) {
//            messageService.command(mapper.map(messageDTO, Message.class));
//        }
//        if (messageDTO.getUser().getId() != null) {
//            messageService.save(mapper.map(messageDTO, Message.class));
//        }
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
