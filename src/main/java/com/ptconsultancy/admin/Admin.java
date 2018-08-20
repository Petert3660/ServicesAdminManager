package com.ptconsultancy.admin;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Admin {

    private ArrayList<String> allServices = new ArrayList<>();

    @Autowired
    public Admin() {
    }

    public boolean addService(String servicePath) {

        for (String service : allServices) {
            if (servicePath.equals(service)) {
                return false;
            }
        }

        allServices.add(servicePath);

        return true;
    }

    public void removeService(String servicePath) {
        allServices.remove(servicePath);
    }

    public void listAllActiveServices() {
        System.out.println("\n\n----------------------------------------------------------------");
        System.out.println("List of currently active services");
        System.out.println("----------------------------------------------------------------");
        for (String service : allServices) {
            System.out.println(service);
        }
        System.out.println("----------------------------------------------------------------\n\n");
    }

    public ArrayList<String> getAllServices() {
        return allServices;
    }
}
