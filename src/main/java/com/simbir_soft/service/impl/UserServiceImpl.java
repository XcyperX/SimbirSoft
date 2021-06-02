package com.simbir_soft.service.impl;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.User;
import com.simbir_soft.repository.UserRepository;
import com.simbir_soft.service.UserService;
import com.simbir_soft.service.commands.ChoiceUserCommands;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MapperFacade mapperFacade;
    private final ChoiceUserCommands choiceUserCommands;

    private static final String USER = "//user";

    @Override
    public Boolean checkCommand(String command) {

        return command.equals(USER);
    }

    @Override
    public void applyService(Message message) {
        choiceUserCommands.checkCommand(message);
    }

    @Override
    public User getById(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такого пользователя!");
        }
        return userRepository.findById(id).get();
    }

    @Override
    public User save(User user) {
        if (Objects.isNull(user)) {
            throw new RuntimeException("Ошибка, нет данных пользователя!");
        }
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        if (userRepository.findById(user.getId()).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такого пользователя!");
        }
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByLogin(String login) {
        if (userRepository.findByLogin(login).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такого пользователя!");
        }
        return userRepository.findByLogin(login).get();
    }
}
