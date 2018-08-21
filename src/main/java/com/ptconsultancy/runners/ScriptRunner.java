package com.ptconsultancy.runners;

import java.io.IOException;

public class ScriptRunner extends Thread {

    private final String filename;
    Process buildRun = null;

    public ScriptRunner(String filename) {
        this.filename = filename;
    }

    public void run() {
        try {
            buildRun = Runtime.getRuntime().exec(filename);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
