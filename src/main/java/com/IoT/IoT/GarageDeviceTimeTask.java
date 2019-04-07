package com.IoT.IoT;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.TimerTask;

public class GarageDeviceTimeTask extends TimerTask {

    @Autowired
    private GarageDeviceController garageDevice = new GarageDeviceController();


    @Override
    public void run() {

//        try {
//            garageDevice.pushGarageDoorButton();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
