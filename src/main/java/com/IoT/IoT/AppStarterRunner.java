package com.IoT.IoT;

import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AppStarterRunner implements CommandLineRunner {

    private LivingRoomController lamp = new LivingRoomController();

    private Runnable foo() {
        return ()-> lamp.turnOnOff();
    }


    @Override
    public void run(String... args) throws ParseException {



        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(5);
        taskScheduler.initialize();

        List<String> lampSchedules = Arrays.asList(
            "0 00 19 * * MON-FRI", "0 30 4 * * *"
        );

        lampSchedules.forEach(
                schedule -> taskScheduler.schedule(
                        foo(),
                        new CronTrigger("0 36 21 * * *")
                )
        );

        taskScheduler.getScheduledThreadPoolExecutor().shutdown();
    }
}
