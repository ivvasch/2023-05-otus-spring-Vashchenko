package ru.otus.model.autobot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Autobot {
    private String name;
    private String type;
    private int limbs;
}
