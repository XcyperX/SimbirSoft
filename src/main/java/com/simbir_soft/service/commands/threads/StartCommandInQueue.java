package com.simbir_soft.service.commands.threads;

public class StartCommandInQueue implements Runnable{
    private final MonitorForCommands monitorForCommands;

    public StartCommandInQueue(MonitorForCommands monitorForCommands) {
        this.monitorForCommands = monitorForCommands;
    }

    @Override
    public void run() {
        try {
            monitorForCommands.startCommand();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
