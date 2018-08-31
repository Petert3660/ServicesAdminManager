/* This file is auto-generated by the ScriptDirectedGui program.         */
/* Please do not modify directly, but feel free to copy into a project   */

package com.ptconsultancy.createdgui;

import static com.ptconsultancy.constants.ServiceAdminConstants.MAIN_HEADING;
import static com.ptconsultancy.constants.ServiceAdminConstants.TRUE;

import com.ptconsultancy.guicomponents.FreeButton;
import com.ptconsultancy.guicomponents.FreeLabel;
import com.ptconsultancy.guicomponents.FreeLabelTextFieldPair;
import com.ptconsultancy.utilities.FileUtilities;
import com.ptconsultancy.utilities.GenerateRandomKeys;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class NewServiceDialog extends JFrame {

    private static final String PROJECT_PATH = "C:/GradleTutorials";

    private static final String SUB_HEADING = " - Create New REST/API Service";
    private static final String TITLE = MAIN_HEADING + SUB_HEADING;

    private static final int FRAME_X_SIZE = 550;
    private static final int FRAME_Y_SIZE = 300;
    private Color col = new Color(230, 255, 255);

    private NewServiceDialog tg = this;
    private MainDialog mainDialog;

    private String mode;

    public NewServiceDialog(MainDialog mainDialog) {

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

        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 20, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton(FreeButton.OK, 180, 200, 80);

        FreeButton b1 = new FreeButton(FreeButton.CANCEL, 290, 200, 80);

        FreeLabelTextFieldPair comp0 = new FreeLabelTextFieldPair(col, "Please enter the new service name:", 30, 90, 240);

        FreeLabelTextFieldPair comp1 = new FreeLabelTextFieldPair(col, "Please select a port for the new service:", 30, 140, 240);

        comp0.getTextField().addActionListener(new ActionListener() {
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

                if (!comp0.empty() && isPortInteger) {
                    File targDir = new File(PROJECT_PATH + "/" + comp0.getText());
                    if (targDir.mkdir()) {
                        createNewServiceFiles(targDir);

                        // Remove .git directory to break link to remote origin
                        removeGitDependency(comp0);

                        // Update authentication file with new credentials
                        updateAuthPropsFile(comp0);

                        // Update build.gradle file with new details
                        updateBuildGradleFile(comp0);

                        // Update application.properties to hold chosen server port
                        if (!comp1.empty()) {
                            updateApplicationPropsFile(comp0, comp1);
                        }

                        JOptionPane.showMessageDialog(tg,
                            "Service: " + comp0.getText() + " has been successfully created",
                            TITLE, JOptionPane.INFORMATION_MESSAGE);

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
                        JOptionPane.showMessageDialog(tg, "The new service port must be an integer - please re-enter or leav empty for default value",
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
        p1.add(l0);
        this.add(p1);
    }

    private void createNewServiceFiles(File targDir) {
        File srcDir = new File("C:/GradleTutorials/SkeletonSpringBootProject");
        final String SETTINGS_GRADLE = "/settings.gradle";
        final String RUN_BAT = "/run.bat";
        try {
            FileUtilities.copyAllFilesFromSrcDirToTargetDir(srcDir.getAbsolutePath(),
                targDir.getAbsolutePath());
            FileUtilities.deleteFile(targDir.getAbsolutePath() + SETTINGS_GRADLE);
            FileUtilities.writeStringToFile(
                targDir.getAbsolutePath() + SETTINGS_GRADLE,
                "rootProject.name = '" + targDir.getName() + "'\n");
            FileUtilities.deleteFile(targDir.getAbsolutePath() + RUN_BAT);
            FileUtilities.writeStringToFile(targDir.getAbsolutePath() + RUN_BAT,
                "cd build/libs\n");
            FileUtilities.appendStringToFile(targDir.getAbsolutePath() + RUN_BAT,
                "\n");
            FileUtilities.appendStringToFile(targDir.getAbsolutePath() + RUN_BAT,
                "java -jar " + targDir.getName() + ".jar\n");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void removeGitDependency(FreeLabelTextFieldPair comp0) {
        File srcDir;
        final String NEW_TARGET = PROJECT_PATH + "/" + comp0.getText();
        srcDir = new File(NEW_TARGET);
        File[] files = srcDir.listFiles();

        for (File targfile : files) {
            if (targfile.getName().equals(".git") && targfile.isDirectory()) {
                try {
                    FileUtilities.deleteDirectory(targfile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if (targfile.getAbsolutePath().contains(".iml")) {
                FileUtilities.deleteFile(targfile.getAbsolutePath());
            }
        }
    }

    private void updateAuthPropsFile(FreeLabelTextFieldPair comp0) {
        final String AUTH_FILE = PROJECT_PATH + "/" + comp0.getText() + "/src/main/resources/auth.properties";
        File authFile = new File(AUTH_FILE);
        if (authFile.exists()) {
            authFile.delete();
        }
        try {
            FileUtilities.writeStringToFile(
                AUTH_FILE, "auth.admin.id=" + comp0.getText() + "\n");
            FileUtilities.appendStringToFile(
                AUTH_FILE, "auth.admin.password=" + GenerateRandomKeys.generateRandomKey(20, 1) + "\n");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void updateBuildGradleFile(FreeLabelTextFieldPair comp0) {
        final String BUILD_GRADLE_FILE = PROJECT_PATH + "/" + comp0.getText() + "/build.gradle";
        File buildFile = new File(BUILD_GRADLE_FILE);

        try {
            String allGradleContents = FileUtilities.writeFileToString(BUILD_GRADLE_FILE);
            if (buildFile.exists()) {
                buildFile.delete();
            }
            allGradleContents = allGradleContents.replace("projectName = \"SkeletonSpringBootProject\"",
                "projectName = \"" + comp0.getText() + "\"");
            allGradleContents = allGradleContents.replace("projectTitle = \"Skeleton Spring Boot Project\"",
                "projectTitle = \"" + comp0.getText() + "\"");
            FileUtilities.writeStringToFile(BUILD_GRADLE_FILE, allGradleContents);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void updateApplicationPropsFile(FreeLabelTextFieldPair comp0, FreeLabelTextFieldPair comp1) {
        final String APP_PROPS_FILE = PROJECT_PATH + "/" + comp0.getText() + "/src/main/resources/application.properties";
        File appPropFile = new File(APP_PROPS_FILE);

        try {
            String allAppPropContents = FileUtilities.writeFileToString(APP_PROPS_FILE);
            if (appPropFile.exists()) {
                appPropFile.delete();
            }
            allAppPropContents = allAppPropContents.replace("server.port=8200",
                "server.port=" + comp1.getText());
            FileUtilities.writeStringToFile(APP_PROPS_FILE, allAppPropContents);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
