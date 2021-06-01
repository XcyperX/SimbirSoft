package com.simbir_soft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO implements Serializable {
    @JsonProperty("user_id")
    private Long id;

    @NotNull
    private String login;

    private String password;

    private String role;

    private Boolean ban;

    @JsonProperty("end_ban_date")
    private LocalDateTime endBanDate;
}
