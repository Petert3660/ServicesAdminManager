/* This file is auto-generated by the ScriptDirectedGui program.                  */
/* Please do not modify directly as the code cannot then be guaranteed to operate */
/* correctly. However, please feel free to copy the source into a project.        */

package com.ptconsultancy.createdgui;

import static com.ptconsultancy.constants.FileSystemConstants.GIT_INIT;
import static com.ptconsultancy.constants.FileSystemConstants.LOCAL_SRC;
import static com.ptconsultancy.constants.InformationMessages.GIT_PUSH;
import static com.ptconsultancy.constants.InformationMessages.NO_SERVICE_SELECT;
import static com.ptconsultancy.constants.InformationMessages.SERVICE_PORT_INTEGER;
import static com.ptconsultancy.constants.ServiceAdminConstants.MAIN_HEADING;
import static com.ptconsultancy.constants.ServiceAdminConstants.STANDARD_DROPDOWN_SELECT;
import static com.ptconsultancy.constants.ServiceAdminConstants.TRUE;

import com.ptconsultancy.admin.Admin;
import com.ptconsultancy.admin.Service;
import com.ptconsultancy.domain.guicomponents.FreeButton;
import com.ptconsultancy.domain.guicomponents.FreeLabel;
import com.ptconsultancy.domain.guicomponents.FreeLabelComboBoxPair;
import com.ptconsultancy.domain.guicomponents.FreeLabelTextFieldPair;
import com.ptconsultancy.domain.utilities.FileUtilities;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.springframework.beans.factory.annotation.Autowired;

public class UpdateServicesPortDialog extends JFrame {

    private static final String TITLE = "Update Service Port";
    private static final int FRAME_X_SIZE = 550;
    private static final int FRAME_Y_SIZE = 300;
    private Color col = new Color(230, 255, 255);

    private UpdateServicesPortDialog tg = this;

    private MainDialog mainDialog;

    private Admin admin;

    @Autowired
    public UpdateServicesPortDialog(MainDialog mainDialog, Admin admin) {
        this.admin = admin;
        this.mainDialog = mainDialog;
        mainDialog.setEnabled(false);

        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(col);

        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 30, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeLabelTextFieldPair tf0 = new FreeLabelTextFieldPair(col, "Please enter the port:", 30, 140, 240);

        FreeButton b0 = new FreeButton(FreeButton.OK, 180, 200, 80);

        FreeButton b1 = new FreeButton(FreeButton.CANCEL, 290, 200, 80);

        ArrayList<String> items0 = new ArrayList<String>();
        items0.add(STANDARD_DROPDOWN_SELECT);
        for (Service service : admin.getAllServicesByName()) {
            if (!service.isRunning()) {
                items0.add(service.getName());
            }
        }
        FreeLabelComboBoxPair comp0 = new FreeLabelComboBoxPair(col, "Please select service to update:", 30, 90, 240, items0);

        comp0.getComboBox().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!comp0.isFirstItemSelected()) {
                    Service service = admin.getServiceByName(comp0.getSelectedItem());
                    tf0.setText(service.getUrl().substring(service.getUrl().lastIndexOf(":") + 1));
                }
            }
        });

        // This is the control for the OK button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                boolean isPortInteger = true;
                // Check that port number is an integer
                try {

                    int portNum = Integer.parseInt(tf0.getText());

                } catch (NumberFormatException nfe) {
                    isPortInteger = false;
                }

                if (!comp0.isFirstItemSelected() && isPortInteger) {
                    Service service = admin.getServiceByName(comp0.getSelectedItem());
                    updateApplicationPropsFile(comp0, tf0, service.getUrl().substring(service.getUrl().lastIndexOf(":") + 1));
                    admin.removeService(service.getName());
                    String newUrl = "http://localhost:" + tf0.getText();
                    service.setUrl(newUrl);
                    admin.addService(service.getAbsolutePath());
                    updateGit(comp0);
                    JOptionPane.showMessageDialog(tg, GIT_PUSH, TITLE, JOptionPane.INFORMATION_MESSAGE);
                    admin.outputServiceStatus();
                    b1.doClick();
                } else {
                    if (comp0.isFirstItemSelected()) {
                        JOptionPane.showMessageDialog(tg, NO_SERVICE_SELECT, TITLE, JOptionPane.INFORMATION_MESSAGE);
                    }
                    if (!isPortInteger) {
                        JOptionPane.showMessageDialog(tg, SERVICE_PORT_INTEGER, TITLE, JOptionPane.INFORMATION_MESSAGE);
                        tf0.clearAndFocus();
                    }
                }
            }
        });

        // This is the control for the Cancel-implement button
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainDialog.setEnabled(TRUE);
                tg.dispose();
            }
        });

        p1.add(b0);
        p1.add(b1);
        p1.add(comp0.getPanel());
        p1.add(l0);
        p1.add(tf0.getPanel());
        this.add(p1);
    }

    private void updateApplicationPropsFile(FreeLabelComboBoxPair comp0, FreeLabelTextFieldPair tf0, String oldPort) {
        final String APP_PROPS_FILE = LOCAL_SRC + "/" + comp0.getSelectedItem() + "/src/main/resources/application.properties";
        File appPropFile = new File(APP_PROPS_FILE);

        try {
            String allAppPropContents = FileUtilities.writeFileToString(APP_PROPS_FILE);
            if (appPropFile.exists()) {
                appPropFile.delete();
            }
            allAppPropContents = allAppPropContents.replace("server.port=" + oldPort,
                "server.port=" + tf0.getText());
            FileUtilities.writeStringToFile(APP_PROPS_FILE, allAppPropContents);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void updateGit(FreeLabelComboBoxPair comp0) {
        try {
            FileUtilities.writeStringToFile(GIT_INIT, "cd\\\n");
            FileUtilities.appendStringToFile(GIT_INIT, "cd C:\\GradleTutorials\\" + comp0.getSelectedItem() + "\n\n");
            FileUtilities.appendStringToFile(GIT_INIT, "git add *\n");
            FileUtilities.appendStringToFile(GIT_INIT, "git commit -m \"Changing the application port\"\n");
//            FileUtilities.appendStringToFile(GIT_INIT, "git remote add origin https://github.com/Petert3660/" + comp0.getText() + ".git\n");
//            FileUtilities.appendStringToFile(GIT_INIT, "git push -u origin master");
            Process process = Runtime.getRuntime().exec(GIT_INIT);

            while (process.isAlive()) {
                int x = 0;
            }

            File file = new File(GIT_INIT);
            if (file.exists()) {
                file.delete();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
