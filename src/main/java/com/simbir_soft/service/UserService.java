package com.simbir_soft.service;

import com.simbir_soft.Dto.UserDTO;

import java.util.List;

public interface UserService extends CRUDService<UserDTO, Long> {
    List<UserDTO> findAll();
    UserDTO findByLogin(String login);
}
