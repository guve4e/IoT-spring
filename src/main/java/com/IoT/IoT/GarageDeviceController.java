package com.IoT.IoT;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

class GarageDevice {
    Double temperature;
    Double humidity;
    String description = "Measures temperature and humidity and can open/close the garage door.";
    Date date;
    Double elapsedTime;

    public Double getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Double elapsedTime) {
        this.elapsedTime = elapsedTime;
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

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
}


@RestController
public class GarageDeviceController {

    @RequestMapping("/temperature")
    @ResponseBody
    public GarageDevice getTemperatureAndHumidity() {

        long startTime = System.nanoTime();

        final String uri = "http://192.168.0.10";
        RestTemplate restTemplate = new RestTemplate();

        return getGarageDevice(startTime, uri, restTemplate);
    }

    private GarageDevice getGarageDevice(long startTime, String uri, RestTemplate restTemplate) {
        GarageDevice device = restTemplate.getForObject(uri, GarageDevice.class);

        long elapsedTime = System.nanoTime() - startTime;

        device.elapsedTime = (double) TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
        device.date = new Date();
        return device;
    }

    @RequestMapping("/press-button")
    @ResponseBody
    public GarageDevice pushGarageDoorButton() throws Exception {

        long startTime = System.nanoTime();

        String uri = "http://192.168.0.10/H";
        RestTemplate restTemplate = new RestTemplate();

        return getGarageDevice(startTime, uri, restTemplate);
    }
}