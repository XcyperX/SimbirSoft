package com.SimbirSoft.SimbirSoft.service.impl;

import com.SimbirSoft.SimbirSoft.Dto.UserDTO;
import com.SimbirSoft.SimbirSoft.model.User;
import com.SimbirSoft.SimbirSoft.repository.UserRepository;
import com.SimbirSoft.SimbirSoft.service.UserService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MapperFacade mapperFacade;


    @Override
    public UserDTO getById(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такого пользователя!");
        }
        return mapperFacade.map(userRepository.findById(id).get(), UserDTO.class);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        if (userDTO != null) {
            User user = userRepository.save(mapperFacade.map(userDTO, User.class));
            return mapperFacade.map(user, UserDTO.class);
        }
        return null;
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        if (userRepository.findById(userDTO.getId()).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такого пользователя!");
        }
        User user = userRepository.save(mapperFacade.map(userDTO, User.class));
        return mapperFacade.map(user, UserDTO.class);
    }

    @Override
    public void delete(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такого пользователя!");
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> findAll() {
        return mapperFacade.mapAsList(userRepository.findAll(), UserDTO.class);
    }

    @Override
    public UserDTO findByLogin(String login) {
        if (userRepository.findByLogin(login).isEmpty()) {
            throw new RuntimeException("Ошибка, нет такого пользователя!");
        }
        return mapperFacade.map(userRepository.findByLogin(login).get(), UserDTO.class);
    }
}
