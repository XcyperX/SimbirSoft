package com.SimbirSoft.SimbirSoft.Dto;

import com.SimbirSoft.SimbirSoft.model.Message;
import com.SimbirSoft.SimbirSoft.model.Role;
import com.SimbirSoft.SimbirSoft.model.Room;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO implements Serializable {
    @JsonProperty("user_id")
    private Long id;

    private String login;

    private String password;

    private String role;

    private List<RoomDTO> rooms = new ArrayList<>();

    private List<MessageDTO> messages = new ArrayList<>();
}
