package com.SimbirSoft.SimbirSoft.controller;

import com.SimbirSoft.SimbirSoft.Dto.MessageDTO;
import com.SimbirSoft.SimbirSoft.Dto.RoomDTO;
import com.SimbirSoft.SimbirSoft.Dto.UserDTO;
import com.SimbirSoft.SimbirSoft.service.MessageService;
import com.SimbirSoft.SimbirSoft.service.RoomService;
import com.SimbirSoft.SimbirSoft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestApiController {
    private final UserService userService;
    private final RoomService roomService;
    private final MessageService messageService;

    @PostMapping("/registrations")
    public UserDTO registrationUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @PutMapping("/users/{id}")
    public UserDTO updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        return userService.update(userDTO);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }


    @PostMapping("/rooms")
    public RoomDTO createRoom(@RequestBody @Valid RoomDTO roomDTO) {
        return roomService.save(roomDTO);
    }

    @PutMapping("/rooms/{id}")
    public RoomDTO updateRoom(@PathVariable("id") Long id, @RequestBody RoomDTO roomDTO) {
        roomDTO.setId(id);
        return roomService.update(roomDTO);
    }

    @DeleteMapping("/rooms/{id}")
    public void deleteRoom(@PathVariable("id") Long id) {
        roomService.delete(id);
    }

    @GetMapping("/rooms")
    public ResponseEntity<?> getRooms() {
        return ResponseEntity.ok(roomService.findAll());
    }


    @PostMapping("/message")
    public MessageDTO createMessage(@RequestBody @Valid MessageDTO messageDTO) {
        if (messageDTO.getText().split(" ")[0].contains("//")) {
            messageService.command(messageDTO);
        }
        return messageService.save(messageDTO);
    }

    @PutMapping("/message/{id}")
    public MessageDTO updateMessage(@PathVariable("id") Long id, @RequestBody MessageDTO messageDTO) {
        messageDTO.setId(id);
        return messageService.update(messageDTO);
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
