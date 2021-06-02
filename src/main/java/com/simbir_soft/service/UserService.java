package com.simbir_soft.service;


import com.simbir_soft.model.User;

import java.util.List;

public interface UserService extends CRUDService<User, Long>, CheckServiceByCommand  {
    List<User> findAll();
    User findByLogin(String login);
}
