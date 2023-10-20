package ru.otus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import ru.otus.model.autobot.Autobot;
import ru.otus.model.machine.*;
import ru.otus.service.TransformGateWay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@IntegrationComponentScan
@SpringBootApplication
public class SpringHw15Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringHw15Application.class, args);

        TransformGateWay gateWay = context.getBean(TransformGateWay.class);
        List<Machine> machineList = getMachineList();
        Collection<Autobot> result = gateWay.process(machineList);
        log.info("Transform to autobot finished");
        result.forEach(System.out::println);
    }

    private static List<Machine> getMachineList() {
        Machine camaro = new Camaro("BB", "auto", 4);
        Machine ducatti = new Ducatti916("A", "bike", 2);
        Machine peterbilt = new Peterbilt379("O", "track", 10);
        Machine porsche = new Porsche911("M", "racingCar", 4);
        Machine vwType1 = new VWType1("W", "wagon", 4);
        List<Machine> machineList = new ArrayList<>();
        machineList.add(camaro);
        machineList.add(ducatti);
        machineList.add(peterbilt);
        machineList.add(porsche);
        machineList.add(vwType1);
        return machineList;
    }
}
