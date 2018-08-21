/* This file is auto-generated by the ScriptDirectedGui program.                  */
/* Please do not modify directly as the code cannot then be guaranteed to operate */
/* correctly. However, please feel free to copy the source into a project.        */

package com.ptconsultancy.createdgui;

import com.ptconsultancy.admin.Admin;
import com.ptconsultancy.admin.Service;
import com.ptconsultancy.guicomponents.FreeButton;
import com.ptconsultancy.guicomponents.FreeLabel;
import com.ptconsultancy.guicomponents.FreeLabelComboBoxPair;
import com.ptconsultancy.runners.ScriptRunner;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.springframework.beans.factory.annotation.Autowired;

public class StartServiceDialog extends JFrame {

    private static final String MAIN_HEADING = "Services Admin Manager";
    private static final String SUB_HEADING = "Start Service";
    private static final String TITLE = MAIN_HEADING + " - " + SUB_HEADING;
    private static final int FRAME_X_SIZE = 550;
    private static final int FRAME_Y_SIZE = 250;
    private Color col = new Color(230, 255, 255);

    private StartServiceDialog tg = this;

    private MainDialog mainDialog;

    private Admin admin;

    @Autowired
    public StartServiceDialog(MainDialog mainDialog, Admin admin) {
        this.admin = admin;
        this.mainDialog = mainDialog;
        mainDialog.setEnabled(false);

        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(col);

        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 30, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton("OK", 180, 150, 80);

        FreeButton b1 = new FreeButton("Cancel", 290, 150, 80);


        ArrayList<String> items0 = new ArrayList<String>();
        items0.add("--Select");
        for (Service service : admin.getAllServicesByName()) {
            if (!service.isRunning()) {
                items0.add(service.getName());
            }
        }
        FreeLabelComboBoxPair comp0 = new FreeLabelComboBoxPair(col, "Please select service to start:", 30, 90, 240, items0);

        // This is the control for the OK button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prepareAndExecuteOutputFile(admin.getServiceByName(comp0.getSelectedItem()));
                JOptionPane.showMessageDialog(tg, "Service " + comp0.getSelectedItem() + " has been successfully started",
                    TITLE, JOptionPane.INFORMATION_MESSAGE);
                admin.setServiceRunningByName(comp0.getSelectedItem());
                admin.outputServiceStatus();
                b1.doClick();
            }
        });

        // This is the control for the Cancel-implement button
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainDialog.setEnabled(true);
                tg.dispose();
            }
        });

        p1.add(b0);
        p1.add(b1);
        p1.add(comp0.getPanel());
        p1.add(l0);
        this.add(p1);
    }

    private void prepareAndExecuteOutputFile(Service service) {
        File file = new File("run.bat");
        if (file.exists()) {
            file.delete();
        }
        try {
            RandomAccessFile fout = new RandomAccessFile("run.bat", "rw");
            fout.writeBytes("cd\\\n");
            fout.writeBytes("cd " + service.getAbsolutePath() + "\n\n");
            fout.writeBytes("start /min java -jar " + service.getName() + ".jar\n");
            fout.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ScriptRunner sr = new ScriptRunner();
        sr.start();
    }
}
