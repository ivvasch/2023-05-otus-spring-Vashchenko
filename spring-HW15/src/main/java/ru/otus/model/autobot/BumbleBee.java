package ru.otus.model.autobot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BumbleBee extends Autobot{
    private String wings;

    public BumbleBee(String name, String type, int legs) {
        super(name, type, legs);
    }

    @Override
    public String toString() {
        return "AutoBot{" +
                "name = '" + getName() + '\'' +
                ", type = '" + getType() + '\'' +
                ", limbs = '" + getLimbs() + '\'' +
                ", wings = '" + wings + '\'' +
                '}';
    }
}
