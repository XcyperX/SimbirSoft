package com.simbir_soft.service.commands.proxy;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.User;
import com.simbir_soft.service.MessageService;
import com.simbir_soft.service.commands.ChoiceService;
import com.simbir_soft.service.commands.thread.MyMonitorThread;
import com.simbir_soft.service.commands.thread.WorkerThread;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CommandInvocationHandler implements InvocationHandler {
    private final Message message;
    private final User user;
    private final ChoiceService choiceService;
    private final MessageService messageService;
    private final MyMonitorThread myMonitorThread;

    public CommandInvocationHandler(Message message, User user, ChoiceService choiceService, MessageService messageService, MyMonitorThread myMonitorThread) {
        this.message = message;
        this.user = user;
        this.choiceService = choiceService;
        this.messageService = messageService;
        this.myMonitorThread = myMonitorThread;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (message.getText().split(" ")[0].contains("//")) {
            Runnable thread = new WorkerThread(message, user, choiceService);
            myMonitorThread.addTask(thread);
        }
        return method.invoke(messageService, args);
    }
}
