package com.simbir_soft.service.commands.threads;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
public class MonitorForCommands {
    private final LinkedBlockingQueue<Message> commands = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<User> users = new LinkedBlockingQueue<>();
    private final ChoiceService choiceService;

    public synchronized void addCommand(Message command) throws InterruptedException {
        while (commands.size() >= 1) {
            wait();
        }
        commands.add(command);
        System.out.println("Команда добавленна в пул! " + command);
        notify();
    }

    public synchronized void addUser(User user) throws InterruptedException {
        while (commands.size() >= 1) {
            wait();
        }
        users.add(user);
        System.out.println("Пользователь добавлен в пул! " + user);
        notify();
    }

    public synchronized void startCommand() throws InterruptedException {
        while (commands.size() < 1) {
            wait();
        }
        Message message = commands.take();
        User user = users.take();
        choiceService.checkMessage(message);
        System.out.println("Команда выполенна! " + message.getText());
        notify();
    }
}
