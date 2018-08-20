package com.ptconsultancy.runners;

import java.io.IOException;

public class ScriptRunner extends Thread {

    Process buildRun = null;

    public void run() {
        try {
            buildRun = Runtime.getRuntime().exec("run.bat");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
