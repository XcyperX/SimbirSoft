package com.simbir_soft.service.commands.threads;

import com.simbir_soft.model.Message;
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

    public void checkMessage(Message message) {
        List<CheckServiceByCommand> checkServiceByCommands = List.of(userService, roomService);

        checkServiceByCommands.forEach(service -> {
            if (service.checkCommand(message.getText().split(" "))) {
                service.applyService(message);
            }
        });
    }
}
