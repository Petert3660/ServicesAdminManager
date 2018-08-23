package com.ptconsultancy.runners;

import com.ptconsultancy.admin.Admin;
import com.ptconsultancy.admin.Service;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class ScriptRunner extends Thread {

    private final String filename;
    private final Service service;
    private Admin admin;
    Process buildRun = null;

    @Autowired
    public ScriptRunner(String filename, Service service, Admin admin) {
        this.filename = filename;
        this.service = service;
        this.admin = admin;
    }

    public void run() {
        try {
            buildRun = Runtime.getRuntime().exec(filename);

            RestTemplate restTemplate = new RestTemplate();
            for (int i = 0; i < 10; i++) {
                String endpoint = service.getUrl() + "/healthcheck";
                String message;
                try {
                    message = "Healthcheck response:- " + restTemplate.getForObject(endpoint, String.class);
                } catch (Exception e1) {
                    message = "Still trying to connect....";
                }
                if (message.equals("Healthcheck response:- " + service.getName() + " is running OK")) {
                    admin.setServiceRunningByName(service.getName());
                    admin.outputServiceStatus();
                    break;
                }
                Thread.sleep(3000);
                if (i == 9) {
                    admin.setServiceUnableToStartByName(service.getName());
                    admin.outputServiceStatus();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
