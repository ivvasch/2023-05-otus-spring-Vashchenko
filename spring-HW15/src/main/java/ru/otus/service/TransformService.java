package ru.otus.service;

import ru.otus.model.autobot.Autobot;
import ru.otus.model.machine.Machine;

public interface TransformService {
    Autobot transform(Machine machine);
}
