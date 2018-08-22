/* This file is auto-generated by the ScriptDirectedGui program.                  */
/* Please do not modify directly as the code cannot then be guaranteed to operate */
/* correctly. However, please feel free to copy the source into a project.        */

package com.ptconsultancy.createdgui;

import com.ptconsultancy.admin.Admin;
import com.ptconsultancy.admin.Service;
import com.ptconsultancy.guicomponents.FreeButton;
import com.ptconsultancy.guicomponents.FreeLabel;
import com.ptconsultancy.guicomponents.FreeLabelComboBoxPair;
import com.ptconsultancy.guicomponents.FreeLabelTextFieldPair;
import com.ptconsultancy.guicomponents.FreeRadioButton;
import com.ptconsultancy.guicomponents.FreeRadioButtonGroup;
import com.ptconsultancy.guicomponents.FreeTextArea;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.springframework.beans.factory.annotation.Autowired;

public class EndpointTestDialog extends JFrame {

    private static final String MAIN_HEADING = "Services Admin Manager";
    private static final String SUB_HEADING = "Endpoint Test";
    private static final String TITLE = MAIN_HEADING + " - " + SUB_HEADING;
    private static final int FRAME_X_SIZE = 700;
    private static final int FRAME_Y_SIZE = 900;
    private Color col = new Color(230, 255, 255);

    JPanel p1 = new JPanel();
    // This is the output text area and is on screen at all times
    FreeTextArea output = new FreeTextArea(col, "The output will be shown below:", 30, 240, 200, 635, 480, false);

    private EndpointTestDialog tg = this;

    private MainDialog mainDialog;

    private Admin admin;

    private boolean getLastSelected = true;

    @Autowired
    public EndpointTestDialog(MainDialog mainDialog, Admin admin) {
        this.mainDialog = mainDialog;
        this.admin = admin;

        mainDialog.setEnabled(false);

        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        p1.setLayout(null);
        p1.setBackground(col);

        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 30, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton("Test", 200, 800, 80);

        FreeButton b1 = new FreeButton("Clear", 310, 800, 80);

        FreeButton b2 = new FreeButton("Cancel", 420, 800, 80);

        FreeRadioButton rb0 = new FreeRadioButton(col, "Get", 30, 90, 50, 20);
        rb0.setSelected();
        FreeRadioButton rb1 = new FreeRadioButton(col, "Post", 80, 90, 60, 20);
        FreeRadioButton rb2 = new FreeRadioButton(col, "Put", 140, 90, 50, 20);
        FreeRadioButton rb3 = new FreeRadioButton(col, "Delete", 190, 90, 90, 20);

        FreeRadioButtonGroup comp0 = new FreeRadioButtonGroup();
        ArrayList<FreeRadioButton> items0 = new ArrayList<>();
        items0.add(rb0);
        items0.add(rb1);
        items0.add(rb2);
        items0.add(rb3);
        comp0.addButtons(items0);

        ArrayList<String> items1 = new ArrayList<String>();
        items1.add("--Select");
        for (Service service : admin.getAllServicesByName()) {
            if (service.isRunning()) {
                items1.add(service.getName());
            }
        }
        FreeLabelComboBoxPair comp1 = new FreeLabelComboBoxPair(col, "Please select the service name:", 30, 140, 240, items1);

        FreeLabelTextFieldPair comp2 = new FreeLabelTextFieldPair(col, "Please enter endpoint:", 30, 190, 240);

        // This is the Body text area and is only used when doing an active pasing of data (such as POST or PUT)
        FreeTextArea body = new FreeTextArea(col, "Body:", 30, 240, 200, 635, 220, false);
        body.getPanel().setVisible(false);

        // This is the control for the Test button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button output - " + b0.getButtonText());
            }
        });

        // This is the control for the Clear button
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button output - " + b1.getButtonText());
            }
        });

        // This is the control for the Cancel-implement button
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainDialog.setEnabled(true);
                tg.dispose();
            }
        });

        // This is the control for the Get radio button
        rb0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                body.getPanel().setVisible(false);
                resizeOutputForGet();
                getLastSelected = true;
            }
        });

        // This is the control for the Post radio button
        rb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                body.getPanel().setVisible(true);
                resizeOutputForPost();
            }
        });

        // This is the control for the Put radio button
        rb2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                body.getPanel().setVisible(true);
                resizeOutputForPost();
            }
        });

        // This is the control for the Delete radio button
        rb3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                body.getPanel().setVisible(true);
                resizeOutputForPost();
            }
        });

        p1.add(b0);
        p1.add(b1);
        p1.add(b2);
        p1.add(rb0);
        p1.add(rb1);
        p1.add(rb2);
        p1.add(rb3);
        p1.add(comp1.getPanel());
        p1.add(comp2.getPanel());
        p1.add(body.getPanel());
        p1.add(output.getPanel());
        p1.add(l0);
        this.add(p1);
    }

    private void resizeOutputForGet() {
        p1.remove(output.getPanel());
        output = new FreeTextArea(col, "The output will be shown below:", 30, 240, 200, 635, 480, false);
        p1.add(output.getPanel());
        this.repaint();
    }

    private void resizeOutputForPost() {
        if (getLastSelected) {
            p1.remove(output.getPanel());
            output = new FreeTextArea(col, "The output will be shown below:", 30, 490, 200, 635, 220, false);
            p1.add(output.getPanel());
            this.repaint();
            getLastSelected = false;
        }
    }
}
