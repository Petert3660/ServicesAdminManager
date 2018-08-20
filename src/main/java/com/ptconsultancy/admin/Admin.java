package com.ptconsultancy.admin;

import java.util.ArrayList;
import java.util.Comparator;
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
            if (servicePath.equals(service.getName())) {
                return false;
            }
        }

        Service service = new Service(servicePath, false);
        allServices.add(service);

        return true;
    }

    public void removeService(String servicePath) {
        allServices.remove(servicePath);
    }

    public void listAllActiveServices() {
        System.out.println("\n\n----------------------------------------------------------------");
        System.out.println("List of currently active services");
        System.out.println("----------------------------------------------------------------");
        for (Service service : allServices) {
            System.out.println(service.getName());
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
}
