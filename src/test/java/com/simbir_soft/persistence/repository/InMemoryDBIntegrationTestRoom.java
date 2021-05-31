package com.simbir_soft.persistence.repository;

import com.simbir_soft.model.Room;
import com.simbir_soft.repository.RoomRepository;
import config.H2JpaConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { H2JpaConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
public class InMemoryDBIntegrationTestRoom {
    @Resource
    private RoomRepository roomRepository;

    private static final long ID = 1;
    private static final long SIZE_NULL = 0;
    private static final long SIZE_ALL = 3;
    private static final String NAME = "test";
    private static final String NAME_UPDATE = "test2";
    private static final String INCORRECT_NAME = "Имя комнаты не совпадает";
    private static final String INCORRECT_SIZE = "Размер массива не совпадает";

    @Before
    public void cleanDB() {
        roomRepository.deleteAll();
    }

    @Test
    public void testWhenRoomSaved() {
        Room room = new Room(ID, NAME);
        Room roomSave = roomRepository.save(room);

        Room saveRoom = roomRepository.findById(roomSave.getId()).get();
        assertEquals(saveRoom.getName(), NAME, INCORRECT_NAME);
    }

    @Test
    public void testWhenUpdateRoom() {
        Room room = new Room(ID, NAME);
        Room roomSave = roomRepository.save(room);

        Room roomUpdate = roomRepository.findById(roomSave.getId()).get();
        roomUpdate.setName(NAME_UPDATE);
        Room userUpdateSave = roomRepository.save(roomUpdate);

        assertEquals(userUpdateSave.getName(), NAME_UPDATE, INCORRECT_NAME);
    }

    @Test
    public void testWhenDeleteRoom() {
        Room room = new Room(ID, NAME);
        Room roomSave = roomRepository.save(room);

        roomRepository.deleteById(roomSave.getId());

        assertEquals(roomRepository.findAll().size(), SIZE_NULL, INCORRECT_SIZE);
    }

    @Test
    public void testWhenGetRoom() {
        Room room = new Room(ID, NAME);
        Room roomSave = roomRepository.save(room);

        assertEquals(roomRepository.getById(roomSave.getId()).getId(), roomSave.getId(), INCORRECT_SIZE);
    }

    @Test
    public void testWhenRoomsList() {
        Room room1 = new Room(NAME);
        Room room2 = new Room(NAME);
        Room room3 = new Room(NAME);
        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);

        assertEquals(roomRepository.findAll().size(), SIZE_ALL, INCORRECT_SIZE);
    }
}
