package com.SimbirSoft.SimbirSoft.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO implements Serializable {
    @JsonProperty("user_id")
    private Long id;

    @NotNull
    private String login;

    private String password;

    private String role;

    private List<RoomDTO> rooms = new ArrayList<>();

    private List<MessageDTO> messages = new ArrayList<>();
}
