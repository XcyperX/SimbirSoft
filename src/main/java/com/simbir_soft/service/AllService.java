package com.simbir_soft.service;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AllService {
    private final MessageService messageService;
    private final RoomService roomService;
    private final UserService userService;

    public void checkService() {

    }
}
