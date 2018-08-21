/* This file is auto-generated by the ScriptDirectedGui program.                  */
/* Please do not modify directly as the code cannot then be guaranteed to operate */
/* correctly. However, please feel free to copy the source into a project.        */

package com.ptconsultancy.createdgui;

import com.ptconsultancy.admin.Admin;
import com.ptconsultancy.admin.Service;
import com.ptconsultancy.guicomponents.FreeButton;
import com.ptconsultancy.guicomponents.FreeLabel;
import com.ptconsultancy.guicomponents.FreeTextArea;
import com.ptconsultancy.guis.GuiHelper;
import com.ptconsultancy.runners.ScriptRunner;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.springframework.beans.factory.annotation.Autowired;

public class MainDialog extends JFrame {

    private static final String MAIN_HEADING = "Services Admin Manager";
    private static final String TITLE = MAIN_HEADING;
    private static final int FRAME_X_SIZE = 1000;
    private static final int FRAME_Y_SIZE = 900;
    private Color col = new Color(235, 255, 255);
    private static final int EXIT_STATUS = 0;

    private MainDialog tg = this;

    private JMenuBar menuBar = new JMenuBar();

    private Admin admin;

    FreeButton b0 = new FreeButton("Exit", 460, 800, 80);
    FreeTextArea comp0 = new FreeTextArea(col, "Output:", 30, 90, 200, 935, 620, false);

    @Autowired
    public MainDialog(Admin admin) {

        this.admin = admin;
        this.admin.setFreeTextArea(comp0);

        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(col);

        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 30, new Font("", Font.BOLD + Font.ITALIC, 20));

        // This is the control for the Exit-implement button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeRunServiceFiles();
                System.exit(EXIT_STATUS);
            }
        });

        p1.add(b0);
        p1.add(comp0.getPanel());
        p1.add(l0);

        setUpMenuBar();
          this.setJMenuBar(menuBar);
        this.add(p1);
    }

    private void removeRunServiceFiles() {
        for (int i = 0; i < admin.getAllServicesByName().size(); i++) {
            File file = new File("run" + String.valueOf(i) + ".bat");
            if (file.exists()) {
                file.delete();
            }
        }
    }

    private void setUpMenuBar() {
        JMenu menu0 = new JMenu("Project");
        JMenuItem menuItem00 = new JMenuItem("New");
        menu0.add(menuItem00);
        JMenuItem menuItem01 = new JMenuItem("Open");
        menu0.add(menuItem01);
        JMenuItem menuItem02 = new JMenuItem("Save");
        menu0.add(menuItem02);
        menu0.addSeparator();
        JMenuItem menuItem04 = new JMenuItem("Exit");
        menu0.add(menuItem04);

        // This is the control for the Project/New menu item
        menuItem00.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item - New in the Project menu has been clicked");
            }
        });

        // This is the control for the Project/Open menu item
        menuItem01.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item - Open in the Project menu has been clicked");
            }
        });

        // This is the control for the Project/Save menu item
        menuItem02.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item - Save in the Project menu has been clicked");
            }
        });

        // This is the control for the Project/Exit-implement menu item
        menuItem04.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                b0.doClick();
            }
        });

        menuBar.add(menu0);

        JMenu menu1 = new JMenu("Services");
        JMenuItem menuItem10 = new JMenuItem("New REST Service");
        menu1.add(menuItem10);
        JMenuItem menuItem11 = new JMenuItem("New Web Service");
        menu1.add(menuItem11);
        menu1.addSeparator();
        JMenuItem menuItem12 = new JMenuItem("Add Existing Service");
        menu1.add(menuItem12);
        JMenuItem menuItem13 = new JMenuItem("Remove Service");
        menu1.add(menuItem13);
        menu1.addSeparator();
        JMenuItem menuItem14 = new JMenuItem("Monitor Service");
        menu1.add(menuItem14);

        // This is the control for the Services/New REST Service menu item
        menuItem10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item - New REST Service in the Services menu has been clicked");
            }
        });

        // This is the control for the Services/New Web Service menu item
        menuItem11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item - New Web Service in the Services menu has been clicked");
            }
        });

        // This is the control for the Add Existing Service menu item
        menuItem12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddServicesDialog addServicesDialog = new AddServicesDialog(tg, admin);
                GuiHelper.showFrame(addServicesDialog);
            }
        });

        // This is the control for the Remove Service menu item
        menuItem13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RemoveServicesDialog removeServicesDialog = new RemoveServicesDialog(tg, admin);
                GuiHelper.showFrame(removeServicesDialog);
            }
        });

        menuBar.add(menu1);

        JMenu menu2 = new JMenu("Start Services");
        JMenuItem menuItem20 = new JMenuItem("Start All Services");
        menu2.add(menuItem20);
        JMenuItem menuItem21 = new JMenuItem("Start Services");
        menu2.add(menuItem21);
        menu2.addSeparator();
        JMenuItem menuItem23 = new JMenuItem("Restart All Services");
        menu2.add(menuItem23);
        JMenuItem menuItem24 = new JMenuItem("Restart Services");
        menu2.add(menuItem24);

        // This is the control for the Start Services/Start All Services menu item
        menuItem20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (admin.noServices()) {
                    JOptionPane.showMessageDialog(tg, "There are no services added as yet - you must add services before they can be started",
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else if (admin.allServicesRunning()) {
                    JOptionPane.showMessageDialog(tg, "All available services are already running - you cannot run any further services",
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    startAllServices();
                }
            }
        });

        // This is the control for the Start Services/Start Services menu item
        menuItem21.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (admin.noServices()) {
                    JOptionPane.showMessageDialog(tg, "There are no services added as yet - you must add services before they can be started",
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else if (admin.allServicesRunning()) {
                    JOptionPane.showMessageDialog(tg, "All available services are already running - you cannot run any further services",
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    StartServiceDialog startServiceDialog = new StartServiceDialog(tg, admin);
                    GuiHelper.showFrame((startServiceDialog));
                }
            }
        });

        // This is the control for the Start Services/Restart All Services menu item
        menuItem23.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item - Restart All Services in the Start Services menu has been clicked");
            }
        });

        // This is the control for the Start Services/Restart Services menu item
        menuItem24.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item - Restart Services in the Start Services menu has been clicked");
            }
        });

        menuBar.add(menu2);

        JMenu menu3 = new JMenu("Stop Services");
        JMenuItem menuItem30 = new JMenuItem("Stop All Services");
        menu3.add(menuItem30);
        JMenuItem menuItem31 = new JMenuItem("Stop Services");
        menu3.add(menuItem31);

        // This is the control for the Stop Services/Stop All Services menu item
        menuItem30.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item - Stop All Services in the Stop Services menu has been clicked");
            }
        });

        // This is the control for the Stop Services/Stop Services menu item
        menuItem31.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item - Stop Services in the Stop Services menu has been clicked");
            }
        });

        menuBar.add(menu3);

        JMenu menu4 = new JMenu("Help");
        JMenuItem menuItem40 = new JMenuItem("Help");
        menu4.add(menuItem40);
        menu4.addSeparator();
        JMenuItem menuItem42 = new JMenuItem("About");
        menu4.add(menuItem42);

        // This is the control for the Help/<MenuItem51> menu item
        menuItem40.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item - Help in the Help menu has been clicked");
            }
        });

        // This is the control for the Help/About menu item
        menuItem42.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item - About in the Help menu has been clicked");
            }
        });

        menuBar.add(menu4);
    }

    public void prepareAndExecuteOutputFile(Service service, int count) {
        String filename = "run" + String.valueOf(count) + ".bat";
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
        try {
            RandomAccessFile fout = new RandomAccessFile(filename, "rw");
            fout.writeBytes("cd\\\n");
            fout.writeBytes("cd " + service.getAbsolutePath() + "\n\n");
            fout.writeBytes("start /min java -jar " + service.getName() + ".jar\n");
            fout.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ScriptRunner sr = new ScriptRunner(filename);
        sr.start();
    }

    private void startAllServices() {
        if (admin.reportHostPortConflict() == 0) {
            int count = 0;
            for (Service service : admin.getAllServicesByName()) {
                prepareAndExecuteOutputFile(service, count++);
            }

            for (int i = 0; i < count; i++) {
                Service service = admin.getAllServicesByName().get(i);
                admin.setServiceRunningByName(service.getName());
            }
            admin.outputServiceStatus();
        } else {
            JOptionPane.showMessageDialog(tg, "There are host/port conflicts between imported services - please resolve before starting all",
                TITLE, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
