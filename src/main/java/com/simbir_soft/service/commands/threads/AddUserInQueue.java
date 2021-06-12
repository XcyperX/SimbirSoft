package com.simbir_soft.service.commands.threads;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.User;

public class AddUserInQueue implements Runnable{
    private final MonitorForCommands monitorForCommands;
    private final User user;

    public AddUserInQueue(MonitorForCommands monitorForCommands, User user) {
        this.monitorForCommands = monitorForCommands;
        this.user = user;
    }

    @Override
    public void run() {
        try {
            monitorForCommands.addUser(user);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
