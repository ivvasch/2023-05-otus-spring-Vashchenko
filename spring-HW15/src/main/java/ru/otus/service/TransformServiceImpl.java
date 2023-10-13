package ru.otus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.model.autobot.*;
import ru.otus.model.machine.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
public class TransformServiceImpl implements TransformService {


    @Override
    public Autobot transform(Machine machine) {
        if (machine != null) {
            if (machine instanceof Camaro) {
                log.info("Start transform to Bumblebee");
                BumbleBee bumbleBee = new BumbleBee("BumbleBee", machine.getType(), machine.getWheels());
                bumbleBee.setWings("2");
                return bumbleBee;
            }
            if (machine instanceof Ducatti916) {
                log.info("Start transform to Arsi");
                Arsi arsi = new Arsi("Arsi", machine.getType(), machine.getWheels() + 2);
                arsi.setSniperSkills("100%");
                return arsi;
            }
            if (machine instanceof Peterbilt379) {
                log.info("Start transform to Optimus");
                OptimusPrime optimusPrime = new OptimusPrime("Optimus Prime", machine.getType(), 4);
                optimusPrime.setBrainStormSkills("100%");
                return optimusPrime;
            }
            if (machine instanceof Porsche911) {
                log.info("Start transform to Mirage");
                Mirage mirage = new Mirage("Mirage", machine.getType(), machine.getWheels());
                List<Integer> countOfMirage = Arrays.stream(IntStream.range(1, 100).toArray()).boxed().toList();
                mirage.setCountOfMirage(countOfMirage);
                return mirage;
            }
            if (machine instanceof VWType1) {
                log.info("Start transform to WheelJack");
                WheelJack wheelJack = new WheelJack("WheelJack", machine.getType(), machine.getWheels());
                wheelJack.setMechanicSkills("100%");
                return wheelJack;
            }

        }
        return null;
    }
}
