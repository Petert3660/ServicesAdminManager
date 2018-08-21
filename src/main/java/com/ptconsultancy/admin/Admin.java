package com.ptconsultancy.admin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Admin {

    private ArrayList<Service> allServices = new ArrayList<>();

    @Autowired
    public Admin() {
    }

    public boolean addService(String servicePath) {

        for (Service service : allServices) {
            if (servicePath.equals(service.getAbsolutePath())) {
                return false;
            }
        }

        Service service = new Service(servicePath, false);
        service.setUrl(findTheServiceUrl(service.getName()));
        allServices.add(service);

        return true;
    }

    private String findTheServiceUrl(String name) {

        HashMap<String, String> filePairs = (HashMap) getKeyValuePairsFromFile(name);
        String url = filePairs.get("spring.data.rest.base-path") + ":" + filePairs.get("server.port");

        return url;
    }

    private Map<String, String> getKeyValuePairsFromFile(String name) {

        HashMap<String, String> keyValuePairs = new HashMap<>();

        String filename = "c:/GradleTutorials/" + name + "/src/main/resources/application.properties";
        BufferedReader br = null;

        try {

            br = new BufferedReader(new FileReader(filename));

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.charAt(0) != '#') {
                    String[] temp = sCurrentLine.split("=");
                    keyValuePairs.put(temp[0], temp[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return keyValuePairs;
    }

    public void removeService(String serviceName) {
        allServices.remove(getServiceByName(serviceName));
    }

    public void listAllActiveServices() {
        System.out.println("\n\n----------------------------------------------------------------");
        System.out.println("List of currently active services");
        System.out.println("----------------------------------------------------------------");
        for (Service service : allServices) {
            System.out.print(service.getName());
            if (service.isRunning()) {
                System.out.println("  Status: Running");
            } else {
                System.out.println("  Status: Not Running");
            }
        }
        System.out.println("----------------------------------------------------------------\n\n");
    }

    private void sortAllServicesByName() {
            allServices.sort(new Comparator<Service>() {
            @Override
            public int compare(Service o1, Service o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public ArrayList<Service> getAllServicesByName() {
        sortAllServicesByName();

        return allServices;
    }

    public boolean noServices() {
        if (allServices.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean allServicesRunning() {
        int count = 0;
        for (Service service : allServices) {
            if (service.isRunning()) {
                count++;
            }
        }

        if (count == allServices.size()) {
            return true;
        } else {
            return false;
        }
    }

    public Service getServiceByName(String name) {

        for (Service service : allServices) {
            if (name.equals(service.getName())) {
                return service;
            }
        }

        return null;
    }

    public void setServiceRunningByName(String name) {

        for (Service service : allServices) {
            if (name.equals(service.getName())) {
                service.setRunning(true);
                break;
            }
        }
    }
}
