package com.IoT.IoT;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

@Service
public class AppStarterRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws ParseException {
        //the Date and time at which you want to execute
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormatter .parse("2012-07-06 13:05:45");

        //Now create the time and schedule it
        Timer timer = new Timer();

        //Use this if you want to execute it once
        timer.schedule(new GarageDeviceTimeTask(), date);
    }
}
