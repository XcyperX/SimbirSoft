package com.SimbirSoft.SimbirSoft.repository;

import com.SimbirSoft.SimbirSoft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
