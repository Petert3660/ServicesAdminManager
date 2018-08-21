/* This file is auto-generated by the ScriptDirectedGui program.                  */
/* Please do not modify directly as the code cannot then be guaranteed to operate */
/* correctly. However, please feel free to copy the source into a project.        */

package com.ptconsultancy.createdgui;

import com.ptconsultancy.admin.Admin;
import com.ptconsultancy.guicomponents.FreeButton;
import com.ptconsultancy.guicomponents.FreeLabel;
import com.ptconsultancy.guicomponents.FreeLabelTextButtonTriple;
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

public class AddServicesDialog extends JFrame {

    private static final String MAIN_HEADING = "Services Admin Manager";
    private static final String SUB_HEADING = "Add Service";
    private static final String TITLE = MAIN_HEADING + " - " + SUB_HEADING;
    private static final int FRAME_X_SIZE = 700;
    private static final int FRAME_Y_SIZE = 250;
    private Color col = new Color(230, 255, 255);

    private AddServicesDialog tg = this;

    private MainDialog mainDialog;

    private Admin admin;

    @Autowired
    public AddServicesDialog(MainDialog mainDialog, Admin admin) {
        this.admin = admin;
        this.mainDialog = mainDialog;
        mainDialog.setEnabled(false);

        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(col);

        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 30, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton("OK", 255, 150, 80);

        FreeButton b1 = new FreeButton("Cancel", 365, 150, 80);

        FreeLabelTextButtonTriple comp0 = new FreeLabelTextButtonTriple(col, "Please enter location of service:", 30, 90, 10, "Browse");

        // This is the control for the OK button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!admin.addService(comp0.getText())) {
                    JOptionPane.showMessageDialog(tg, "Service " + comp0.getText() + " has already been added",
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(tg, "Service " + comp0.getText() + " has been successfully added",
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                }
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

        // This is the control for the Browse button on the triple Label, Text, Button
        comp0.getButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setCurrentDirectory(new File("C:/PTConsultancy/LocalTestEnvironment"));
                int returnVal = fc.showDialog(tg, "Select");
                if (returnVal == 0) {
                    comp0.setText(fc.getSelectedFile().getAbsolutePath());
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
