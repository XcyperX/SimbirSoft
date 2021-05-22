package com.SimbirSoft.SimbirSoft.Dto;

import com.SimbirSoft.SimbirSoft.model.Message;
import com.SimbirSoft.SimbirSoft.model.Room;
import com.SimbirSoft.SimbirSoft.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MessageDTO implements Serializable {
    @JsonProperty("message_id")
    private Long id;

    private String text;

    private LocalDate date;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("room_id")
    private Long roomId;
}
