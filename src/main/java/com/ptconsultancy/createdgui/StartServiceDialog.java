/* This file is auto-generated by the ScriptDirectedGui program.                  */
/* Please do not modify directly as the code cannot then be guaranteed to operate */
/* correctly. However, please feel free to copy the source into a project.        */

package com.ptconsultancy.createdgui;

import static com.ptconsultancy.constants.InformationMessages.NO_SERVICE_SELECT;
import static com.ptconsultancy.constants.ServiceAdminConstants.MAIN_HEADING;
import static com.ptconsultancy.constants.ServiceAdminConstants.TRUE;

import com.ptconsultancy.admin.Admin;
import com.ptconsultancy.admin.Service;
import com.ptconsultancy.constants.InformationMessages;
import com.ptconsultancy.domain.guicomponents.FreeButton;
import com.ptconsultancy.domain.guicomponents.FreeLabel;
import com.ptconsultancy.domain.guicomponents.FreeLabelComboBoxPair;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.springframework.beans.factory.annotation.Autowired;

public class StartServiceDialog extends JFrame {

    private static final String TITLE = "Start Service";
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

        int xpos = FRAME_X_SIZE / 2 - 95;
        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 30, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton(FreeButton.OK, xpos, 150, 80);

        FreeButton b1 = new FreeButton(FreeButton.CANCEL, xpos + 110, 150, 80);

        ArrayList<String> items0 = new ArrayList<String>();
        for (Service service : admin.getAllServicesByName()) {
            if (!service.isRunning()) {
                items0.add(service.getName());
            }
        }
        FreeLabelComboBoxPair comp0 = new FreeLabelComboBoxPair(col, "Please select service to start:", 30, 90, 240, items0);

        // This is the control for the OK button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!comp0.isFirstItemSelected()) {
                    int portConflicts = admin.reportHostPortConflict(admin.getServiceByName(comp0.getSelectedItem()));
                    if (portConflicts == 0){
                        mainDialog.updateServiceInfo(comp0.getSelectedItem());
                        mainDialog.prepareAndExecuteOutputFile(admin.getServiceByName(comp0.getSelectedItem()), 0);
                        b1.doClick();
                    } else {
                        JOptionPane.showMessageDialog(tg, InformationMessages.reportPortConfilcts(comp0.getSelectedItem(), portConflicts), TITLE, JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(tg, NO_SERVICE_SELECT, TITLE, JOptionPane.INFORMATION_MESSAGE);
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
        this.add(p1);
    }
}
