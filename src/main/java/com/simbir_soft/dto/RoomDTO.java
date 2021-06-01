package com.simbir_soft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoomDTO implements Serializable {
    @JsonProperty("room_id")
    private Long id;

    private String name;

    @JsonProperty("private_message")
    private Boolean privateMassage;

    @JsonProperty("user_id")
    private Long userId;
}
