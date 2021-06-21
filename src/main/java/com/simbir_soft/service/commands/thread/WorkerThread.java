package com.simbir_soft.service.commands.thread;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.User;
import com.simbir_soft.service.commands.ChoiceService;

public class WorkerThread implements Runnable {
    private final Message message;
    private final User user;
    private final ChoiceService choiceService;

    public WorkerThread(Message message, User user, ChoiceService choiceService) {
        this.message = message;
        this.user = user;
        this.choiceService = choiceService;
    }

    @Override
    public void run() {
        synchronized (choiceService) {
            choiceService.checkMessage(message, user);
        }
    }
}
