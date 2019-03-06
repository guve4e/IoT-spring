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

class GarageDevice {
    Double temperature;
    Double humidity;
    String description;
    Date date;

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

        final String uri = "http://192.168.0.8";
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(uri, GarageDevice.class);
    }

    @RequestMapping("/push-button")
    @ResponseBody
    public GarageDevice pushGarageDoorButton() throws Exception {

        String uri = "http://192.168.0.8/H";
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Object> entity = new HttpEntity<Object>(new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful())
        {
            // lets wait for a second so the device can respond
            Thread.sleep(1000);

            uri = "http://192.168.0.8/L";
            restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        }
        else
        {
            throw new Exception("Was not able to connect to the device!");
        }

        return restTemplate.getForObject(uri, GarageDevice.class);
    }
}
//http://webapi.ddns.net/index.php/tempsensor