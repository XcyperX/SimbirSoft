package com.simbir_soft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class MessageDTO implements Serializable {
    private Long id;

    private String text;

    private LocalDate date;

    private UserDTO user;

    @JsonProperty("room_id")
    private Long roomId;
}
