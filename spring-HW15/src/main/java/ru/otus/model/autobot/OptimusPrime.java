package ru.otus.model.autobot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptimusPrime extends Autobot {
    private String brainStormSkills;
    public OptimusPrime(String name, String type, int legs) {
        super(name, type, legs);
    }

    @Override
    public String toString() {
        return "AutoBot{" +
                "name = '" + getName() + '\'' +
                ", type = '" + getType() + '\'' +
                ", limbs = '" + getLimbs() + '\'' +
                ", brainStormSkills = '" + brainStormSkills + '\'' +
                '}';
    }
}
