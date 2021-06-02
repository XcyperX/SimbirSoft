package com.simbir_soft.service.commands.user;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.Role;
import com.simbir_soft.model.User;
import com.simbir_soft.repository.UserRepository;
import com.simbir_soft.service.CheckServiceByCommand;
import com.simbir_soft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModeratorUserCommand implements CheckServiceByCommand {
    private final UserRepository userRepository;

    private static final String MODERATOR = "moderator";

    @Override
    public Boolean checkCommand(String command) {
        return command.equals(MODERATOR);
    }

    @Override
    public void applyService(Message message) {
        String[] commands = message.getText().split(" ");
        User updateUser = userRepository.findByLogin(commands[2]).get();
        if (commands[3].equals("-n")) {
            updateUser.setRole(Role.MODERATOR);
        } else if (commands[3].equals("-d")) {
            updateUser.setRole(Role.USER);
        }
        userRepository.save(updateUser);
    }
}
