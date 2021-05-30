package com.SimbirSoft.SimbirSoft.service;

import com.SimbirSoft.SimbirSoft.Dto.UserDTO;
import com.SimbirSoft.SimbirSoft.base.CRUDService;

import java.util.List;

public interface UserService extends CRUDService<UserDTO, Long> {
    List<UserDTO> findAll();
    UserDTO findByLogin(String login);
}
