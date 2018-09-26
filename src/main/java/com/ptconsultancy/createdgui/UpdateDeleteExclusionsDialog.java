/* This file is auto-generated by the ScriptDirectedGui program.                  */
/* Please do not modify directly as the code cannot then be guaranteed to operate */
/* correctly. However, please feel free to copy the source into a project.        */

package com.ptconsultancy.createdgui;

import static com.ptconsultancy.constants.FileSystemConstants.EXCLUSION_FILE;
import static com.ptconsultancy.constants.FileSystemConstants.LOCAL_TEST_ENV;
import static com.ptconsultancy.constants.ServiceAdminConstants.MAIN_HEADING;
import static com.ptconsultancy.constants.ServiceAdminConstants.STANDARD_DROPDOWN_SELECT;
import static com.ptconsultancy.constants.ServiceAdminConstants.TRUE;

import com.ptconsultancy.domain.guicomponents.FreeButton;
import com.ptconsultancy.domain.guicomponents.FreeLabel;
import com.ptconsultancy.domain.guicomponents.FreeLabelComboBoxPair;
import com.ptconsultancy.domain.utilities.FileUtilities;
import com.ptconsultancy.helpers.NewServiceHelper;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.thymeleaf.util.StringUtils;

public class UpdateDeleteExclusionsDialog extends NewServiceHelper {

    private static final String TITLE = "Update Delete Exclusions";
    private static final int FRAME_X_SIZE = 550;
    private static final int FRAME_Y_SIZE = 250;
    private Color col = new Color(230, 255, 255);

    private UpdateDeleteExclusionsDialog tg = this;

    private MainDialog mainDialog;

    private String exclusions = "";

    FreeLabelComboBoxPair comp0;
    JPanel p1;

    public UpdateDeleteExclusionsDialog(MainDialog mainDialog) {
        this.mainDialog = mainDialog;
        mainDialog.setEnabled(false);

        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(col);

        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 30, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton("Add", 125, 150, 80);

        FreeButton b1 = new FreeButton(FreeButton.CANCEL, 345, 150, 80);

        FreeButton b2 = new FreeButton("Remove", 235, 150, 80);

        comp0 = new FreeLabelComboBoxPair(col, "Select project to remove from the list:", 30, 90, 260, readFileAndPopulateList());

        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setCurrentDirectory(new File(LOCAL_TEST_ENV));
                int returnVal = fc.showDialog(tg, "Select");
                if (returnVal == 0) {
                    String serviceName = fc.getSelectedFile().getName();
                    String exclusions = "";
                    try {
                        exclusions = FileUtilities.writeFileToString(EXCLUSION_FILE);
                        if (!exclusions.contains(serviceName)) {
                            FileUtilities.deleteFile(EXCLUSION_FILE);
                            exclusions = exclusions.substring(0, exclusions.lastIndexOf("\r\n"));
                            exclusions = exclusions + "\r\n" + serviceName + "\r\n";
                            FileUtilities.writeStringToFile(EXCLUSION_FILE, exclusions);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                    comp0.repopulateComboBox(readFileAndPopulateList());
                    comp0.getPanel().repaint();
                }
            }
        });

        // This is the control for the OK button
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!comp0.isFirstItemSelected()) {
                    int res = JOptionPane.showConfirmDialog(tg, "Are you sure you wish to remove the project " + comp0.getSelectedItem() + " from delete exclusions?",
                        TITLE, JOptionPane.YES_NO_CANCEL_OPTION);
                    if (res == 0) {
                        exclusions = exclusions.replace(comp0.getSelectedItem() + "\r\n", "");
                        try {
                            FileUtilities.writeStringToFile(EXCLUSION_FILE, exclusions);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        comp0.repopulateComboBox(readFileAndPopulateList());
                        comp0.getPanel().repaint();
                    }
                } else {
                    JOptionPane.showMessageDialog(tg, "No project selected - please select a project before continuing",
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

        p1.add(b0);
        p1.add(b1);
        p1.add(b2);
        p1.add(comp0.getPanel());
        p1.add(l0);
        this.add(p1);
    }

    private ArrayList<String> readFileAndPopulateList() {
        try {
            exclusions = FileUtilities.writeFileToString(EXCLUSION_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] prepareItems = exclusions.split("\r\n");
        ArrayList<String> items0 = new ArrayList<String>();
        items0.add(STANDARD_DROPDOWN_SELECT);
        for (String item: prepareItems) {
            if (!StringUtils.isEmpty(item) && !item.startsWith("#")) {
                items0.add(item);
            }
        }

        return items0;
    }
}
