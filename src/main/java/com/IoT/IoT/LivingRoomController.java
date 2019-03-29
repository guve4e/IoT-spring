package com.IoT.IoT;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

class Lamp {
    String success;
    String state;

    String description = "Turns On/Off Living Room Lamp";
    Date date;
    Double elapsedTime;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Double elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}

@RestController
public class LivingRoomController {

    @RequestMapping("/on-off-lamp")
    @ResponseBody
    public Lamp turnOnOff() {

        long startTime = System.nanoTime();

        String uri = "http://192.168.1.61/press-button";
        RestTemplate restTemplate = new RestTemplate();

        Lamp device = restTemplate.getForObject(uri, Lamp.class);

        long elapsedTime = System.nanoTime() - startTime;

        device.elapsedTime = (double) TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
        device.date = new Date();

        return device;
    }
}