package com.simbir_soft.service.commands.threads;

import com.simbir_soft.model.Message;

public class CheckThread implements Runnable {

    final ChoiceService choiceService;
    final Message message;

    public CheckThread(ChoiceService choiceService, Message message) {
        this.choiceService = choiceService;
        this.message = message;
    }

    @Override
    public void run() {
        synchronized (choiceService) {
            choiceService.checkMessage(message);
        }
    }
}
