package com.IoT.IoT;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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


interface Request {
    void sendCommand();
}

class HttpRequest implements Request {

    @Override
    public void sendCommand() {

    }
}

class SerialRequest implements Request {

    @Override
    public void sendCommand() {

    }
}

class DeviceConnector {

    private String address;

    private Device device;
    private Request request;

    public DeviceConnector(Device device, Request request)
    {
        this.address = device.address;
        this.device = device;
        this.request = request;
    }

    public void sendCommnad()
    {
        long startTime = System.nanoTime();

        this.request.sendCommand();

        final String uri = "http://192.168.0.11";

        RestTemplate restTemplate = new RestTemplate();

        GarageDevice device = restTemplate.getForObject(uri, GarageDevice.class);

        long elapsedTime = System.nanoTime() - startTime;

        device.elapsedTime = (double) TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);

        device.date = new Date();
    }
}

interface Device
{
    String address = "";
}


class GarageDevice implements Device {
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

    private boolean garageDorIsOpening = false;

    @RequestMapping("/temperature")
    //@ConditionalOnProperty(prefix = "spring.social.", value = "auto-connection-views")
    @ResponseBody
    public GarageDevice getTemperatureAndHumidity() throws InterruptedException {


        long startTime = System.nanoTime();

        final String uri = "http://192.168.0.11";
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

        if (!garageDorIsOpening)
        {
            try
            {
                // we are about to start the
                // garage door opening process
                garageDorIsOpening = true;

                String uri = "http://192.168.0.11/H";
                RestTemplate restTemplate = new RestTemplate();

                return getGarageDevice(startTime, uri, restTemplate);
            }
            finally {
                // lets simulate garage door opening
                // so requests don't queue
                TimeUnit.SECONDS.sleep(15);

                // we are finished with the garage door opening
                garageDorIsOpening = false;
            }
        }

        throw new Exception("Garage Door is in process of opening!");
    }
}