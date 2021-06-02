package com.simbir_soft.service.commands;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.Role;
import com.simbir_soft.model.User;
import com.simbir_soft.repository.RoomRepository;
import com.simbir_soft.security.SecurityUtils;
import com.simbir_soft.service.CheckServiceByCommand;
import com.simbir_soft.service.UserService;
import com.simbir_soft.service.commands.user.BanUserCommand;
import com.simbir_soft.service.commands.user.ModeratorUserCommand;
import com.simbir_soft.service.commands.user.RenameUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChoiceUserCommands {
    private final RoomRepository roomRepository;

    private final BanUserCommand banUserCommand;
    private final ModeratorUserCommand moderatorUserCommand;
    private final RenameUserCommand renameUserCommand;

    public void checkCommand(Message message) {
        User user = SecurityUtils.getUserFromContext();
        if (Objects.isNull(user)) {
            throw new RuntimeException("Пользователь не авторизирован!");
        }
        if (checkAccessUser(message, user)) {
            choiceUserCommand(message);
        }
    }

    private void choiceUserCommand(Message message) {
        String[] action = message.getText().split(" ");
        List<CheckServiceByCommand> checkUserCommands = List.of(banUserCommand, moderatorUserCommand, renameUserCommand);

        checkUserCommands.forEach(commands -> {
            if (commands.checkCommand(action[1])) {
                commands.applyService(message);
            }
        });
    }

    private Boolean checkAccessUser(Message message, User user) {
        return roomRepository.findById(message.getRoom().getId()).orElseThrow().getUser().getId().equals(user.getId()) ||
                user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.MODERATOR);
    }
}
