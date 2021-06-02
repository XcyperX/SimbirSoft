package com.simbir_soft.service;

import com.simbir_soft.model.Message;

public interface CheckServiceByCommand {
    Boolean checkCommand(String[] command);
    void applyService(Message message);
}
