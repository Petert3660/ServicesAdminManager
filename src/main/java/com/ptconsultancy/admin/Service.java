package com.ptconsultancy.admin;

public class Service {

    private String name;
    private boolean running;

    public Service (String name, boolean running) {
        this.name = name;
        this.running = running;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
