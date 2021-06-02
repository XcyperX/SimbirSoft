package com.simbir_soft.service;

import com.simbir_soft.model.Message;
import com.simbir_soft.service.impl.MessageServiceImpl;
import com.simbir_soft.service.impl.RoomServiceImpl;
import com.simbir_soft.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChoiceService {
    private final RoomService roomService;
    private final UserService userService;

    public void run(Message message) {
        List<CheckServiceByCommand> checkServiceByCommands = List.of(userService, roomService);

        checkServiceByCommands.forEach(service -> {
            if (service.checkCommand(message.getText().split(" ")[0])) {
                service.applyService(message);
            }
        });
    }
}
