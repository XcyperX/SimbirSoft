package com.simbir_soft.service.commands.user;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.User;
import com.simbir_soft.repository.UserRepository;
import com.simbir_soft.service.CheckServiceByCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BanUserCommand implements CheckServiceByCommand {
    private final UserRepository userRepository;

    private static final String BAN = "ban";

    @Override
    public Boolean checkCommand(String[] command) {
        return command[1].equals(BAN);
    }

    @Override
    public void applyService(Message message, User user) {
        User updateUser = userRepository.findByLogin(getLoginUserFromCommand(message)).get();
        updateUser.setBan(true);
        updateUser.setEndBanDate(LocalDateTime.now().plusMinutes(getMinutesBanUser(message)));
        userRepository.save(updateUser);
    }

    private Long getMinutesBanUser(Message message) {
        return Long.parseLong(message.getText().split("-m")[1].strip());
    }

    private String getLoginUserFromCommand(Message message) {
        return message.getText().split("-l")[1].split("-m")[0].strip();
    }
}
