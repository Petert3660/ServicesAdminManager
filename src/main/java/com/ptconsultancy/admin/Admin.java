package com.ptconsultancy.admin;

import java.util.ArrayList;
import java.util.Comparator;
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

    private void sortAllServices() {
            allServices.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    public ArrayList<String> getAllServices() {
        sortAllServices();
        return allServices;
    }
}
