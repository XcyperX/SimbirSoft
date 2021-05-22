package com.SimbirSoft.SimbirSoft.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CommandsBotDTO implements Serializable {
    private String command;
}
