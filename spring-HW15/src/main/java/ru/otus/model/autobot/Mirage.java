package ru.otus.model.autobot;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Mirage extends Autobot {
    private List<Integer> countOfMirage;
    public Mirage(String name, String type, int legs) {
        super(name, type, legs);
    }

    @Override
    public String toString() {
        return "AutoBot{" +
                "name = '" + getName() + '\'' +
                ", type = '" + getType() + '\'' +
                ", limbs = '" + getLimbs() + '\'' +
                ", countOfMirage = '" + countOfMirage + '\'' +
                '}';
    }
}
