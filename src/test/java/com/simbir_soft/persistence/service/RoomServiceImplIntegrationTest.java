package com.simbir_soft.persistence.service;

import com.simbir_soft.Dto.RoomDTO;
import com.simbir_soft.model.Room;
import com.simbir_soft.repository.RoomRepository;
import com.simbir_soft.service.RoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomServiceImplIntegrationTest {
    @Autowired
    private RoomService roomService;

    @MockBean
    private RoomRepository roomRepository;

    private final static String ROOM_ONE = "test1";
    private final static String ROOM_TWO = "test2";
    private final static String ROOM_THREE = "test3";
    private final static Long ID = 1L;

    @Before
    public void setUp() {
        Room room1 = new Room(ROOM_ONE);
        room1.setId(ID);
        Room room2 = new Room(ROOM_TWO);
        Room room3 = new Room(ROOM_THREE);

        List<Room> userList = Arrays.asList(room1, room2, room3);

        Mockito.when(roomRepository.findRoomByName(room1.getName())).thenReturn(room1);
        Mockito.when(roomRepository.findRoomByName(room2.getName())).thenReturn(room2);
        Mockito.when(roomRepository.findRoomByName(room3.getName())).thenReturn(room3);
        Mockito.when(roomRepository.findById(room1.getId())).thenReturn(Optional.of(room1));
        Mockito.when(roomRepository.findAll()).thenReturn(userList);
    }

    @Test
    public void whenValidName_thenUserShouldBeFound() {
        RoomDTO found = roomService.findAllByName(ROOM_ONE);

        assertThat(found.getName()).isEqualTo(ROOM_ONE);
    }

    @Test
    public void whenValidId_thenUserShouldBeFound() {
        RoomDTO found = roomService.getById(ID);

        assertThat(found.getName()).isEqualTo(ROOM_ONE);
    }

    @Test
    public void whenValidAllUsers_thenUserShouldBeFound() {
        List<RoomDTO> userDTOS = roomService.findAll();

        assertThat(userDTOS.size()).isEqualTo(3);
    }

    @Test
    public void whenValidAllUsersName_thenUserShouldBeFound() {
        Room room1 = new Room(ROOM_ONE);
        Room room2 = new Room(ROOM_TWO);
        Room room3 = new Room(ROOM_THREE);

        List<RoomDTO> userDTOS = roomService.findAll();
        assertThat(userDTOS).hasSize(3).extracting(RoomDTO::getName).contains(room1.getName(), room2.getName(), room3.getName());
    }
}
