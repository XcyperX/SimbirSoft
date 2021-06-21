package com.simbir_soft.persistence.repository;

import com.simbir_soft.model.User;
import com.simbir_soft.repository.UserRepository;
import config.H2JpaConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { H2JpaConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
public class InMemoryDBIntegrationTestUser {
    @Resource
    private UserRepository userRepository;

    private static final long ID = 1;
    private static final long SIZE_NULL = 0;
    private static final long SIZE_ALL = 3;
    private static final String NAME = "test";
    private static final String NAME_UPDATE = "test2";
    private static final String INCORRECT_LOGIN = "Логин не совпадает";
    private static final String INCORRECT_SIZE = "Размер массива не совпадает";

    @Before
    public void cleanDB() {
        userRepository.deleteAll();
    }

    @Test
    public void testWhenUserSaved() {
        User user = new User(ID, NAME);
        User userSave = userRepository.save(user);

        User saveUser = userRepository.findById(userSave.getId()).orElseThrow(() -> new RuntimeException());
        assertEquals(saveUser.getLogin(), NAME, INCORRECT_LOGIN);
    }

    @Test
    public void testWhenUpdateUser() {
        User user = new User(ID, NAME);
        User userSave = userRepository.save(user);

        User userUpdate = userRepository.findById(userSave.getId()).get();
        userUpdate.setLogin(NAME_UPDATE);
        User userUpdateSave = userRepository.save(userUpdate);

        assertEquals(userUpdateSave.getLogin(), NAME_UPDATE, INCORRECT_LOGIN);
    }

    @Test
    public void testWhenDeleteUser() {
        User user = new User(ID, NAME);
        User userSave = userRepository.save(user);

        userRepository.deleteById(userSave.getId());

        assertEquals(userRepository.findAll().size(), SIZE_NULL, INCORRECT_SIZE);
    }

    @Test
    public void testWhenGetUser() {
        User user = new User(ID, NAME);
        User userSave = userRepository.save(user);

        Assertions.assertEquals(userRepository.getById(userSave.getId()).getId(), userSave.getId(), INCORRECT_SIZE);
    }

    @Test
    public void testWhenUsersList() {
        User user1 = new User(NAME);
        User user2 = new User(NAME);
        User user3 = new User(NAME);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        assertEquals(userRepository.findAll().size(), SIZE_ALL, INCORRECT_SIZE);
    }
}
