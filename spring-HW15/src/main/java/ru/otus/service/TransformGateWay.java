package ru.otus.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.model.autobot.Autobot;
import ru.otus.model.machine.Machine;

import java.util.Collection;

@MessagingGateway
public interface TransformGateWay {

    @Gateway(requestChannel = "machineChanel.input")
    Collection<Autobot> process(Collection<Machine> machines);
}
