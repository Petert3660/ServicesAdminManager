package com.ptconsultancy.admin;

public class Service {

    private String name;
    private String absolutePath;
    private boolean running;

    public Service (String absolutePath, boolean running) {
        this.absolutePath = absolutePath;
        this.running = running;
        name = absolutePath.substring(absolutePath.lastIndexOf('\\') + 1);
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

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
