/* This file is auto-generated by the ScriptDirectedGui program.                  */
/* Please do not modify directly as the code cannot then be guaranteed to operate */
/* correctly. However, please feel free to copy the source into a project.        */

package com.ptconsultancy.createdgui;

import static com.ptconsultancy.constants.ServiceAdminConstants.MAIN_HEADING;
import static com.ptconsultancy.constants.ServiceAdminConstants.TRUE;

import com.ptconsultancy.admin.Admin;
import com.ptconsultancy.domain.guicomponents.FreeButton;
import com.ptconsultancy.domain.guicomponents.FreeLabel;
import com.ptconsultancy.domain.guicomponents.FreeLabelTextButtonTriple;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.util.StringUtils;

public class ExportToJenkinsDialog extends JFrame {

    private static final String SUB_HEADING = "Export Service to Jenkins";
    private static final String TITLE = MAIN_HEADING + " - " + SUB_HEADING;
    private static final int FRAME_X_SIZE = 700;
    private static final int FRAME_Y_SIZE = 270;
    private Color col = new Color(230, 255, 255);

    private ExportToJenkinsDialog tg = this;

    private MainDialog mainDialog;

    private Admin admin;

    private String servicePath;

    @Autowired
    public ExportToJenkinsDialog(MainDialog mainDialog, Admin admin) {
        this.admin = admin;
        this.mainDialog = mainDialog;
        mainDialog.setEnabled(false);

        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(col);

        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 30, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton(FreeButton.OK, 255, 160, 80);

        FreeButton b1 = new FreeButton(FreeButton.CANCEL, 365, 160, 80);

        FreeLabelTextButtonTriple comp0 = new FreeLabelTextButtonTriple(col, "Please enter location of service source:", 30, 90, 10, FreeButton.BROWSE);

        // This is the control for the OK button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!StringUtils.isEmpty(comp0.getText())) {
                    String serviceName = comp0.getText();
                    if (!admin.addService(servicePath)) {
                        JOptionPane.showMessageDialog(tg, "Service " + serviceName + " has already been added",
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                    }
                    b1.doClick();
                } else {
                    JOptionPane.showMessageDialog(tg, "No service selected - please enter/select a service before continuing",
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
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

        // This is the control for the Browse button on the triple Label, Text, Button
        comp0.getButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setCurrentDirectory(new File("C:/GradleTutorials"));
                int returnVal = fc.showDialog(tg, "Select");
                if (returnVal == 0) {
                    comp0.setText(fc.getSelectedFile().getName());
                    servicePath = fc.getSelectedFile().getAbsolutePath();
                }
            }
        });

        p1.add(b0);
        p1.add(b1);
        p1.add(comp0.getPanel());
        p1.add(l0);
        this.add(p1);
    }
}
