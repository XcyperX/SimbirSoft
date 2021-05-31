package com.simbir_soft.persistence.service;

import com.simbir_soft.Dto.UserDTO;
import com.simbir_soft.model.User;
import com.simbir_soft.repository.UserRepository;
import com.simbir_soft.service.UserService;
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
public class UserServiceImplIntegrationTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private final static String USER_ONE = "test1";
    private final static String USER_TWO = "test2";
    private final static String USER_THREE = "test3";
    private final static Long ID = 1L;

    @Before
    public void setUp() {
        User user1 = new User(USER_ONE);
        user1.setId(ID);
        User user2 = new User(USER_TWO);
        User user3 = new User(USER_THREE);

        List<User> userList = Arrays.asList(user1, user2, user3);

        Mockito.when(userRepository.findByLogin(user1.getLogin())).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findByLogin(user2.getLogin())).thenReturn(Optional.of(user2));
        Mockito.when(userRepository.findByLogin(user3.getLogin())).thenReturn(Optional.of(user3));
        Mockito.when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findAll()).thenReturn(userList);
    }

    @Test
    public void whenValidName_thenUserShouldBeFound() {
        UserDTO found = userService.findByLogin(USER_ONE);

        assertThat(found.getLogin()).isEqualTo(USER_ONE);
    }

    @Test
    public void whenValidId_thenUserShouldBeFound() {
        UserDTO found = userService.getById(ID);

        assertThat(found.getLogin()).isEqualTo(USER_ONE);
    }

    @Test
    public void whenValidAllUsers_thenUserShouldBeFound() {
        List<UserDTO> userDTOS = userService.findAll();

        assertThat(userDTOS.size()).isEqualTo(3);
    }

    @Test
    public void whenValidAllUsersName_thenUserShouldBeFound() {
        User user1 = new User(USER_ONE);
        User user2 = new User(USER_TWO);
        User user3 = new User(USER_THREE);

        List<UserDTO> userDTOS = userService.findAll();
        assertThat(userDTOS).hasSize(3).extracting(UserDTO::getLogin).contains(user1.getLogin(), user2.getLogin(), user3.getLogin());
    }
}
