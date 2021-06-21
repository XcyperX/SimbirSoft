package com.simbir_soft.service.commands.ybot;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.User;
import com.simbir_soft.service.CheckServiceByCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindYbotCommand implements CheckServiceByCommand {

    private static final String FIND = "find";

    @Override
    public Boolean checkCommand(String[] command) {
        return command[1].equals(FIND);
    }

    @Override
    public void applyService(Message entity, User user) {
        System.out.println("yBot find");
    }
}
