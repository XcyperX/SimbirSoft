package com.SimbirSoft.SimbirSoft.repository;

import com.SimbirSoft.SimbirSoft.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
