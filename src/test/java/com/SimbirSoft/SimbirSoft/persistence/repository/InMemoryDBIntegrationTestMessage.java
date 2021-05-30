package com.SimbirSoft.SimbirSoft.persistence.repository;

import com.SimbirSoft.SimbirSoft.model.Message;
import com.SimbirSoft.SimbirSoft.repository.MessageRepository;
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
public class InMemoryDBIntegrationTestMessage {
    @Resource
    private MessageRepository messageRepository;

    private static final long ID = 1;
    private static final long SIZE_NULL = 0;
    private static final long SIZE_ALL = 3;
    private static final String TEST = "test";
    private static final String TEST_UPDATE = "test2";
    private static final String INCORRECT_TEXT = "Текст не совпадает";
    private static final String INCORRECT_SIZE = "Размер массива не совпадает";

    @Before
    public void cleanDB() {
        messageRepository.deleteAll();
    }

    @Test
    public void testWhenMessageSaved() {
        Message message = new Message(ID, TEST);
        Message messageSave = messageRepository.save(message);

        Message saveMessage = messageRepository.findById(messageSave.getId()).get();
        assertEquals(saveMessage.getText(), TEST, INCORRECT_TEXT);
    }

    @Test
    public void testWhenUpdateMessage() {
        Message message = new Message(ID, TEST);
        Message messageSave = messageRepository.save(message);

        Message messageUpdate = messageRepository.findById(messageSave.getId()).get();
        messageUpdate.setText(TEST_UPDATE);
        Message messageUpdateSave = messageRepository.save(messageUpdate);

        assertEquals(messageUpdateSave.getText(), TEST_UPDATE, INCORRECT_TEXT);
    }

    @Test
    public void testWhenDeleteMessage() {
        Message message = new Message(ID, TEST);
        Message messageSave = messageRepository.save(message);

        messageRepository.deleteById(messageSave.getId());

        assertEquals(messageRepository.findAll().size(), SIZE_NULL, INCORRECT_SIZE);
    }

    @Test
    public void testWhenGetMessage() {
        Message message = new Message(ID, TEST);
        Message messageSave = messageRepository.save(message);

        assertEquals(messageRepository.getById(messageSave.getId()).getId(), messageSave.getId(), INCORRECT_SIZE);
    }

    @Test
    public void testWhenMessagesList() {
        Message message1 = new Message(TEST);
        Message message2 = new Message(TEST);
        Message message3 = new Message(TEST);
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);

        assertEquals(messageRepository.findAll().size(), SIZE_ALL, INCORRECT_SIZE);
    }
}
