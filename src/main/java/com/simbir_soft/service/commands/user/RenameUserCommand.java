package com.simbir_soft.service.commands.user;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.User;
import com.simbir_soft.repository.UserRepository;
import com.simbir_soft.service.CheckServiceByCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RenameUserCommand implements CheckServiceByCommand {
    private final UserRepository userRepository;

    private static final String USER = "//user";
    private static final String RENAME = "rename";

    @Override
    public Boolean checkCommand(String[] command) {
        return command[0].equals(USER) && command[1].equals(RENAME);
    }

    @Override
    public void applyService(Message message) {
        String[] commands = message.getText().split(" ");
        User updateUser = userRepository.findByLogin(commands[2]).get();
        updateUser.setLogin(getLoginUserFromCommand(message));
        userRepository.save(updateUser);
    }

    private String getLoginUserFromCommand(Message message) {
        return message.getText().split("-l")[1].strip();
    }
}
