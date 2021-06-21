package com.simbir_soft.service.commands;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.User;
import com.simbir_soft.service.CheckServiceByCommand;
import com.simbir_soft.service.RoomService;
import com.simbir_soft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChoiceService {
    private final RoomService roomService;
    private final UserService userService;

    public synchronized void checkMessage(Message message, User user) {
        List<CheckServiceByCommand> checkServiceByCommands = List.of(userService, roomService);

        checkServiceByCommands.forEach(service -> {
            if (service.checkCommand(message.getText().split(" "))) {
                service.applyService(message, user);
            }
        });
    }
}
