package com.simbir_soft.controller;

import com.simbir_soft.dto.RoomDTO;
import com.simbir_soft.model.Room;
import com.simbir_soft.service.MessageService;
import com.simbir_soft.service.RoomService;
import com.simbir_soft.service.UserService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoomApiController {
    private final RoomService roomService;

    private final MapperFacade mapper;

    @PostMapping("/rooms")
    public Room createRoom(@RequestBody @Valid RoomDTO roomDTO) {
        return roomService.save(mapper.map(roomDTO, Room.class));
    }

    @PutMapping("/rooms/{id}")
    public Room updateRoom(@PathVariable("id") Long id, @RequestBody RoomDTO roomDTO) {
        roomDTO.setId(id);
        return roomService.update(mapper.map(roomDTO, Room.class));
    }

    @DeleteMapping("/rooms/{id}")
    public void deleteRoom(@PathVariable("id") Long id) {
        roomService.delete(id);
    }

    @GetMapping("/rooms")
    public ResponseEntity<?> getRooms() {
        return ResponseEntity.ok(roomService.findAll());
    }
}
