package com.simbir_soft.persistence.service;

import com.simbir_soft.Dto.MessageDTO;
import com.simbir_soft.model.Message;
import com.simbir_soft.repository.MessageRepository;
import com.simbir_soft.service.MessageService;
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
public class MessageServiceImplIntegrationTest {
    @Autowired
    private MessageService messageService;

    @MockBean
    private MessageRepository messageRepository;

    private final static String MESSAGE_ONE = "test1";
    private final static String MESSAGE_TWO = "test2";
    private final static String MESSAGE_THREE = "test3";
    private final static Long ID = 1L;

    @Before
    public void setUp() {
        Message message1 = new Message(MESSAGE_ONE);
        message1.setId(ID);
        Message message2 = new Message(MESSAGE_TWO);
        Message message3 = new Message(MESSAGE_THREE);

        List<Message> userList = Arrays.asList(message1, message2, message3);

        Mockito.when(messageRepository.findById(message1.getId())).thenReturn(Optional.of(message1));
        Mockito.when(messageRepository.findAll()).thenReturn(userList);
    }

    @Test
    public void whenValidId_thenUserShouldBeFound() {
        MessageDTO found = messageService.getById(ID);

        assertThat(found.getText()).isEqualTo(MESSAGE_ONE);
    }

    @Test
    public void whenValidAllUsers_thenUserShouldBeFound() {
        List<MessageDTO> userDTOS = messageService.findAll();

        assertThat(userDTOS.size()).isEqualTo(3);
    }

    @Test
    public void whenValidAllUsersName_thenUserShouldBeFound() {
        Message message1 = new Message(MESSAGE_ONE);
        Message message2 = new Message(MESSAGE_TWO);
        Message message3 = new Message(MESSAGE_THREE);

        List<MessageDTO> userDTOS = messageService.findAll();
        assertThat(userDTOS).hasSize(3).extracting(MessageDTO::getText).contains(message1.getText(), message2.getText(), message3.getText());
    }
}
