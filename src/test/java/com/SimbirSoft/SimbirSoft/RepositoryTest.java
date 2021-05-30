package com.SimbirSoft.SimbirSoft;

import com.SimbirSoft.SimbirSoft.Dto.UserDTO;
import com.SimbirSoft.SimbirSoft.model.Role;
import com.SimbirSoft.SimbirSoft.model.User;
import com.SimbirSoft.SimbirSoft.repository.UserRepository;
import com.SimbirSoft.SimbirSoft.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//TODO Какая-то хрень

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {
    @Autowired
    private UserService userService;

//    @Before
//    public void cleanDB() {
//        userRepository.deleteAll();
//    }

    @Test
    public void whenFindByName_thenReturnUser() {
        UserDTO user = new UserDTO();
        user.setLogin("test");
        user.setPassword("test");
//
        UserDTO userSave = userService.save(user);

        Assertions.assertNotNull(userService.getById(userSave.getId()));

//        User saveUser = userRepository.save(user);
//        User foundUser = userRepository.findOne(user.getId());
    }
}
