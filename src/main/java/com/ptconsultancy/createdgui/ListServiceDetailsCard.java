/* This file is auto-generated by the ScriptDirectedGui program.                  */
/* Please do not modify directly as the code cannot then be guaranteed to operate */
/* correctly. However, please feel free to copy the source into a project.        */

package com.ptconsultancy.createdgui;

import com.ptconsultancy.admin.Service;
import com.ptconsultancy.guicomponents.FreeButton;
import com.ptconsultancy.guicomponents.FreeLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ListServiceDetailsCard extends JFrame {

    private static final String MAIN_HEADING = "Services Admin Manager";
    private static final String SUB_HEADING = "List Service Details";
    private static final String TITLE = MAIN_HEADING + " - " + SUB_HEADING;
    private static final int FRAME_X_SIZE = 550;
    private static final int FRAME_Y_SIZE = 350;
    private Color col = new Color(235, 255, 255);

    private ListServiceDetailsCard tg = this;

    private Service service;

    public ListServiceDetailsCard(Service service) {
        this.service = service;
        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(col);

        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 30, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton("Close", 235, 260, 80);

        FreeLabel comp0 = new FreeLabel("Service name:", 30, 90, 150, 20);
        FreeLabel detail0 = new FreeLabel(service.getName(), 160, 90, 200, 20) ;

        FreeLabel comp1 = new FreeLabel("Service URL:", 30, 120, 150, 20);
        FreeLabel detail1 = new FreeLabel(service.getUrl(), 160, 120, 200, 20) ;

        FreeLabel comp2 = new FreeLabel("Service location:", 30, 150, 150, 20);
        FreeLabel detail2 = new FreeLabel(service.getAbsolutePath(), 160, 150, 400, 20) ;

        FreeLabel comp3 = new FreeLabel("Service userId:", 30, 180, 150, 20);
        FreeLabel detail3 = new FreeLabel(service.getCredentials().getUserId(), 160, 180, 200, 20) ;

        FreeLabel comp4 = new FreeLabel("Service password:", 30, 210, 150, 20);
        FreeLabel detail4 = new FreeLabel(service.getCredentials().getPassword(), 160, 210, 250, 20) ;

        // This is the control for the Close-implement button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tg.dispose();
            }
        });

        p1.add(b0);
        p1.add(comp0);
        p1.add(comp1);
        p1.add(comp2);
        p1.add(comp3);
        p1.add(comp4);
        p1.add(detail0);
        p1.add(detail1);
        p1.add(detail2);
        p1.add(detail3);
        p1.add(detail4);
        p1.add(l0);
        this.add(p1);
    }
}
