package com.ptconsultancy.admin;

import com.ptconsultancy.admin.adminsupport.Credentials;

public class Service {

    private String name;
    private String absolutePath;
    private boolean running;
    private boolean unableToStart = false;
    private String url = "http://";
    private Credentials credentials;

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

    public void setUrl(String url) {
        this.url = this.url + url;
    }

    public String getUrl() {
        return url;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public boolean isUnableToStart() {
        return unableToStart;
    }

    public void setUnableToStart(boolean unableToStart) {
        this.unableToStart = unableToStart;
    }
}
