package com.simbir_soft.service.commands.ybot;

import com.simbir_soft.model.Message;
import com.simbir_soft.service.CheckServiceByCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelpYbotCommand implements CheckServiceByCommand {

    private static final String HELP = "help";

    @Override
    public Boolean checkCommand(String[] command) {
        return command[1].equals(HELP);
    }

    @Override
    public void applyService(Message entity) {
        System.out.println("yBot help");
    }
}
