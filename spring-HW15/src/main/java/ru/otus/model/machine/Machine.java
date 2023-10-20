package ru.otus.model.machine;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Machine {
    private String name;
    private String type;
    private int wheels;
}
