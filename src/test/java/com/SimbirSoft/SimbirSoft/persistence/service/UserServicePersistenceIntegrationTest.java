package com.SimbirSoft.SimbirSoft.persistence.service;

import com.SimbirSoft.SimbirSoft.Dto.UserDTO;
import com.SimbirSoft.SimbirSoft.model.User;
import com.SimbirSoft.SimbirSoft.service.UserService;
import config.H2JpaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO Не робит

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { H2JpaConfig.class }, loader = AnnotationConfigContextLoader.class)
@DirtiesContext
@Transactional
public class UserServicePersistenceIntegrationTest {
    @Autowired
    private UserService userService;

    private static final String NAMEUSER = "test";
    private static final String INCORRECTLOGIN = "Логин не совпадает";
    private static final String INCORRECTSIZE = "Размер массива не совпадает";

    @Test
    public final void testWhenUserSaved() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setLogin(NAMEUSER);
        UserDTO test = userService.save(userDTO);

        assertEquals(test.getLogin(), NAMEUSER, INCORRECTLOGIN);
    }
}
