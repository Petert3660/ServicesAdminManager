/* This file is auto-generated by the ScriptDirectedGui program.                  */
/* Please do not modify directly as the code cannot then be guaranteed to operate */
/* correctly. However, please feel free to copy the source into a project.        */

package com.ptconsultancy.createdgui;

import static com.ptconsultancy.constants.ServiceAdminConstants.MAIN_HEADING;
import static com.ptconsultancy.constants.ServiceAdminConstants.TRUE;

import com.ptconsultancy.domain.guicomponents.FreeButton;
import com.ptconsultancy.domain.guicomponents.FreeLabel;
import com.ptconsultancy.domain.guicomponents.FreeLabelTextFieldPair;
import com.ptconsultancy.domain.utilities.GenerateRandomKeys;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.springframework.beans.factory.annotation.Autowired;

public class GeneratePasswordDialog extends JFrame {

    private static final String TITLE = "Generate Password";
    private static final int FRAME_X_SIZE = 700;
    private static final int FRAME_Y_SIZE = 250;
    private Color col = new Color(230, 255, 255);

    private GeneratePasswordDialog tg = this;

    private MainDialog mainDialog;

    @Autowired
    public GeneratePasswordDialog(MainDialog mainDialog) {
        this.mainDialog = mainDialog;
        mainDialog.setEnabled(false);

        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(col);

        int xpos = FRAME_X_SIZE / 2 - 95;
        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 30, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton(FreeButton.OK, xpos, 150, 80);

        FreeButton b1 = new FreeButton(FreeButton.CANCEL, xpos + 110, 150, 80);

        FreeLabelTextFieldPair comp0 = new FreeLabelTextFieldPair(col, "Generated password:", 30, 90, 200);

        // This is the control for the OK button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comp0.setText(GenerateRandomKeys.generateRandomKey(20, 1));
                b1.setText(FreeButton.CLOSE);
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
        this.add(p1);
    }
}
