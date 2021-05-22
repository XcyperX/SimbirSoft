package com.SimbirSoft.SimbirSoft.service;



import com.SimbirSoft.SimbirSoft.Dto.UserDTO;
import com.SimbirSoft.SimbirSoft.base.CRUDService;
import com.SimbirSoft.SimbirSoft.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends CRUDService<UserDTO, Long> {
    List<UserDTO> findAll();
    UserDTO findByLogin(String login);
}
