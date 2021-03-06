package com.simbir_soft.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "message")
@Data
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    public Message() {
    }

    public Message(Long id) {
        this.id = id;
    }

    public Message(String text) {
        this.text = text;
    }

    public Message(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
