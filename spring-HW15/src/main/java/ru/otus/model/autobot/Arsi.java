package ru.otus.model.autobot;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Arsi extends Autobot{
    private String sniperSkills;
    public Arsi(String name, String type, int legs) {
        super(name, type, legs);
    }

    @Override
    public String toString() {
        return "AutoBot{" +
                "name = '" + getName() + '\'' +
                ", type = '" + getType() + '\'' +
                ", limbs = '" + getLimbs() + '\'' +
                ", sniperSkills = '" + sniperSkills + '\'' +
                '}';
    }
}
