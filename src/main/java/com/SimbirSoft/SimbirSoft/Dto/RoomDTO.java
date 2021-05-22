package com.SimbirSoft.SimbirSoft.Dto;

import com.SimbirSoft.SimbirSoft.model.Message;
import com.SimbirSoft.SimbirSoft.model.Room;
import com.SimbirSoft.SimbirSoft.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RoomDTO implements Serializable {
    @JsonProperty("room_id")
    private Long id;

    private String name;

    @JsonProperty("private_message")
    private Boolean privateMassage;

    @JsonProperty("user_id")
    private Long userId;

    private List<UserDTO> users = new ArrayList<>();

    private List<MessageDTO> messages = new ArrayList<>();
}
