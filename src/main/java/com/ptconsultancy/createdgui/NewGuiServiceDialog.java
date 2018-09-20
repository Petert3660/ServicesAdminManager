/* This file is auto-generated by the ScriptDirectedGui program.         */
/* Please do not modify directly, but feel free to copy into a project   */

package com.ptconsultancy.createdgui;

import static com.ptconsultancy.constants.ServiceAdminConstants.MAIN_HEADING;
import static com.ptconsultancy.constants.ServiceAdminConstants.TRUE;

import com.ptconsultancy.domain.guicomponents.FreeButton;
import com.ptconsultancy.domain.guicomponents.FreeCheckBox;
import com.ptconsultancy.domain.guicomponents.FreeLabel;
import com.ptconsultancy.domain.guicomponents.FreeLabelTextFieldPair;
import com.ptconsultancy.domain.utilities.FileUtilities;
import com.ptconsultancy.helpers.NewServiceHelper;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class NewGuiServiceDialog extends NewServiceHelper {

    private static final String PROJECT_TITLE = "Template Spring Boot";

    private static final String SUB_HEADING = " - Create New Desktop GUI Service";
    private static final String TITLE = MAIN_HEADING + SUB_HEADING;

    private static final int FRAME_X_SIZE = 550;
    private static final int FRAME_Y_SIZE = 350;
    private Color col = new Color(230, 255, 255);

    private NewGuiServiceDialog tg = this;
    private MainDialog mainDialog;

    private String mode;

    public NewGuiServiceDialog(MainDialog mainDialog) {

        this.mainDialog = mainDialog;
        this.mainDialog.setEnabled(false);

        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(col);

        File file = new File(PROJECT_PATH);
        if (!file.exists()) {
            file.mkdir();
        }

        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 30, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton(FreeButton.OK, 180, 250, 80);

        FreeButton b1 = new FreeButton(FreeButton.CANCEL, 290, 250, 80);

        FreeLabelTextFieldPair comp0 = new FreeLabelTextFieldPair(col, "Please enter the new service name:", 30, 90, 240);

        FreeLabelTextFieldPair comp1 = new FreeLabelTextFieldPair(col, "Please select a port for the new service:", 30, 140, 240);

        FreeCheckBox cb1 = new FreeCheckBox(col, "Check if this new service will be built in Jenkins", 30, 190, 300, 20);

        comp0.getTextField().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                b0.doClick();
            }
        });

        comp1.getTextField().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                b0.doClick();
            }
        });

        // This is the control for the OK button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                boolean isPortInteger = true;
                // Check that port number is an integer
                try {
                    if (!comp1.empty()) {
                        int portNum = Integer.parseInt(comp1.getText());
                    }
                } catch (NumberFormatException nfe) {
                    isPortInteger = false;
                }

                String filename = "C:/GradleTutorials/ServicesAdminManager/gitinit.bat";
                if (!comp0.empty() && isPortInteger) {
                    File targDir = new File(PROJECT_PATH + "/" + comp0.getText());
                    if (targDir.mkdir()) {
                        createNewServiceFiles(targDir, "C:/GradleTutorials/TemplateSpringBoot");

                        // Update build.gradle file with new details
                        updateBuildGradleFile(comp0, PROJECT_TITLE);

                        // Update application.properties to hold chosen server port
                        if (!comp1.empty()) {
                            updateApplicationPropsFile(comp0, comp1);
                        }

                        // Update banner.txt file
                        updateBannerFile(comp0);

                        // Update messages file
                        updateMessagesFile(comp0);

                        // Remove .git directory to break link to remote origin
                        removeGitDependency(comp0);
                        // Create new Git initialisation
                        if (cb1.isSelected()) {
                            updateGitAndJenkins(comp0, tg, TITLE);
                        }

                        JOptionPane.showMessageDialog(tg,
                            "Service: " + comp0.getText() + " has been successfully created",
                            TITLE, JOptionPane.INFORMATION_MESSAGE);

                        File file = new File(filename);
                        if (file.exists()) {
                            file.delete();
                        }

                        b1.doClick();
                    } else {
                        JOptionPane.showMessageDialog(tg, "Unable to create service: " + comp0.getText()
                                + " , there may already be a service with this name",
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                        comp0.clearAndFocus();
                    }
                } else {
                    if (comp0.empty()) {
                        JOptionPane.showMessageDialog(tg, "The name of the new service cannot be empty - please choose a name",
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                        comp0.clearAndFocus();
                    }
                    if (!isPortInteger) {
                        JOptionPane.showMessageDialog(tg, "The new service port must be an integer - please re-enter or leave empty for default value",
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                        comp1.clearAndFocus();
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
        p1.add(comp1.getPanel());
        p1.add(cb1);
        p1.add(l0);
        this.add(p1);
    }

    private void updateApplicationPropsFile(FreeLabelTextFieldPair comp0, FreeLabelTextFieldPair comp1) {
        final String APP_PROPS_FILE = PROJECT_PATH + "/" + comp0.getText() + "/src/main/resources/application.properties";
        File appPropFile = new File(APP_PROPS_FILE);

        try {
            String allAppPropContents = FileUtilities.writeFileToString(APP_PROPS_FILE);
            if (appPropFile.exists()) {
                appPropFile.delete();
            }
            allAppPropContents = allAppPropContents.replace("server.port=8180",
                "server.port=" + comp1.getText());
            FileUtilities.writeStringToFile(APP_PROPS_FILE, allAppPropContents);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void updateBannerFile(FreeLabelTextFieldPair comp0) {
        final String BANNER_FILE = PROJECT_PATH + "/" + comp0.getText() + "/src/main/resources/banner.txt";
        File bannerFile = new File(BANNER_FILE);

        try {
            String allBannerContents = FileUtilities.writeFileToString(BANNER_FILE);
            if (bannerFile.exists()) {
                bannerFile.delete();
            }
            allBannerContents = allBannerContents.replace(PROJECT_TITLE,
                comp0.getText());
            FileUtilities.writeStringToFile(BANNER_FILE, allBannerContents);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void updateMessagesFile(FreeLabelTextFieldPair comp0) {
        final String MESSAGES_FILE = PROJECT_PATH + "/" + comp0.getText() + "/src/main/resources/messages.properties";
        File bannerFile = new File(MESSAGES_FILE);

        try {
            String allMessagesContents = FileUtilities.writeFileToString(MESSAGES_FILE);
            if (bannerFile.exists()) {
                bannerFile.delete();
            }
            allMessagesContents = allMessagesContents.replace(PROJECT_TITLE,
                comp0.getText());
            FileUtilities.writeStringToFile(MESSAGES_FILE, allMessagesContents);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
