package com.simbir_soft.service;

import com.simbir_soft.model.Message;
import com.simbir_soft.model.User;

public interface CheckServiceByCommand {
    Boolean checkCommand(String[] command);
    void applyService(Message message);
}
