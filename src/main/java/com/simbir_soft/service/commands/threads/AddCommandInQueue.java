package com.simbir_soft.service.commands.threads;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.User;

public class AddCommandInQueue implements Runnable{
    private final Message message;
    private final MonitorForCommands monitorForCommands;
    private final User user;

    public AddCommandInQueue(Message message, MonitorForCommands monitorForCommands, User user) {
        this.message = message;
        this.monitorForCommands = monitorForCommands;
        this.user = user;
    }

    @Override
    public void run() {
        try {
            monitorForCommands.addCommand(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
