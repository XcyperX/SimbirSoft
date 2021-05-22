package com.SimbirSoft.SimbirSoft.repository;

import com.SimbirSoft.SimbirSoft.model.Message;
import com.SimbirSoft.SimbirSoft.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
