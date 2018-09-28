/* This file is auto-generated by the ScriptDirectedGui program.                  */
/* Please do not modify directly as the code cannot then be guaranteed to operate */
/* correctly. However, please feel free to copy the source into a project.        */

package com.ptconsultancy.createdgui;

import static com.ptconsultancy.constants.FileSystemConstants.ADMIN_SAVE;
import static com.ptconsultancy.constants.FileSystemConstants.LOCAL_TEST_ENV;
import static com.ptconsultancy.constants.FileSystemConstants.PROJECT_PATH;
import static com.ptconsultancy.constants.InformationMessages.ALL_SERVICES_RUNNING;
import static com.ptconsultancy.constants.InformationMessages.ENDPOINT_CONFLICT;
import static com.ptconsultancy.constants.InformationMessages.NOT_ALL_SERVICES_RUNNING;
import static com.ptconsultancy.constants.InformationMessages.NO_PROJ_CONFIG;
import static com.ptconsultancy.constants.InformationMessages.NO_PROJ_SEL;
import static com.ptconsultancy.constants.InformationMessages.NO_PROJ_SELECTED;
import static com.ptconsultancy.constants.InformationMessages.NO_SERVICES_ADDED_ENDPOINT;
import static com.ptconsultancy.constants.InformationMessages.NO_SERVICES_RUNNING;
import static com.ptconsultancy.constants.InformationMessages.NO_SERVICES_RUNNING_ENDPOINT;
import static com.ptconsultancy.constants.InformationMessages.PROJ_SAVED;
import static com.ptconsultancy.constants.InformationMessages.STOP_BEFORE_SAVE;
import static com.ptconsultancy.constants.ServiceAdminConstants.MAIN_HEADING;

import com.ptconsultancy.admin.Admin;
import com.ptconsultancy.admin.Service;
import com.ptconsultancy.constants.InformationMessages;
import com.ptconsultancy.constants.MenuConstants;
import com.ptconsultancy.domain.guicomponents.FreeButton;
import com.ptconsultancy.domain.guicomponents.FreeLabel;
import com.ptconsultancy.domain.guicomponents.FreeTextArea;
import com.ptconsultancy.domain.guis.GuiHelper;
import com.ptconsultancy.runners.ScriptRunner;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

public class MainDialog extends JFrame {

    private static final String TITLE = MAIN_HEADING;
    private static final int FRAME_X_SIZE = 1000;
    private static final int FRAME_Y_SIZE = 900;
    private Color col = new Color(235, 255, 255);
    private static final int EXIT_STATUS = 0;

    private String projectName = "";

    private JPanel p1 = new JPanel();

    private FreeLabel comp1;

    JMenuItem menuItem01;

    private MainDialog tg = this;

    private JMenuBar menuBar = new JMenuBar();

    private Admin admin;
    private Environment env;

    FreeButton b0 = new FreeButton(FreeButton.EXIT, 460, 800, 80);
    FreeTextArea comp0 = new FreeTextArea(col, "Output:", 30, 90, 200, 935, 620, false);

    @Autowired
    public MainDialog(Admin admin, Environment env) {

        this.admin = admin;
        this.env = env;
        this.admin.setFreeTextArea(comp0);

        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        comp1 = new FreeLabel("The currently selected project is:- " + projectName,30, 750, 400, 20);

        p1.setLayout(null);
        p1.setBackground(col);

        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 30, new Font("", Font.BOLD + Font.ITALIC, 20));

        // This is the control for the Exit-implement button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeRunServiceFiles();
                stopAllServices();
                System.exit(EXIT_STATUS);
            }
        });

        p1.add(b0);
        p1.add(comp0.getPanel());
        p1.add(l0);
        p1.add(comp1);

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
        JMenu menu0 = new JMenu(MenuConstants.PROJECT_MENU);
        JMenuItem menuItem00 = new JMenuItem(MenuConstants.PROJECT_MENU_NEW);
        menu0.add(menuItem00);
        menuItem01 = new JMenuItem(MenuConstants.PROJECT_MENU_OPEN);
        menu0.add(menuItem01);
        JMenuItem menuItem02 = new JMenuItem(MenuConstants.PROJECT_MENU_SAVE);
        menu0.add(menuItem02);
        menu0.addSeparator();
        JMenuItem menuItem04 = new JMenuItem(FreeButton.EXIT);
        menu0.add(menuItem04);

        // This is the control for the Project/New Project menu item
        menuItem00.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NewProjectDialog newProjectDialog = new NewProjectDialog(tg);
                GuiHelper.showFrame(newProjectDialog);
            }
        });

        // This is the control for the Project/Open Project  menu item
        menuItem01.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openProjectChoice();
            }
        });

        // This is the control for the Project/Save Project menu item
        menuItem02.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveProjectObject();
            }
        });

        // This is the control for the Project/Exit-implement menu item
        menuItem04.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                b0.doClick();
            }
        });

        menuBar.add(menu0);

        JMenu menu1 = new JMenu(MenuConstants.SERVICES_MENU);
        JMenuItem menuItem10 = new JMenuItem(MenuConstants.SERVICES_MENU_NEW_API);
        menu1.add(menuItem10);
        JMenuItem menuItem11 = new JMenuItem(MenuConstants.SERVICES_MENU_NEW_FRONTEND);
        menu1.add(menuItem11);
        menu1.addSeparator();
        JMenuItem menuItem17 = new JMenuItem(MenuConstants.SERVICES_MENU_DELETE);
        menu1.add(menuItem17);
        JMenuItem menuItem18 = new JMenuItem(MenuConstants.SERVICES_REVIEW_DELETE_EXCLUSION);
        menu1.add(menuItem18);
        menu1.addSeparator();
        JMenuItem menuItem12 = new JMenuItem(MenuConstants.SERVICES_MENU_ADD);
        menu1.add(menuItem12);
        JMenuItem menuItem13 = new JMenuItem(MenuConstants.SERVICES_MENU_REMOVE);
        menu1.add(menuItem13);
        menu1.addSeparator();
        JMenuItem menuItem14 = new JMenuItem(MenuConstants.SERVICES_MENU_MONITOR);
        menu1.add(menuItem14);
        menu1.addSeparator();
        JMenuItem menuItem15 = new JMenuItem(MenuConstants.SERVICES_MENU_PASSWORD);
        menu1.add(menuItem15);
        menu1.addSeparator();
        JMenuItem menuItem16 = new JMenuItem(MenuConstants.SERVICES_MENU_LIST);
        menu1.add(menuItem16);
        menu1.addSeparator();
        JMenuItem menuItem19 = new JMenuItem(MenuConstants.SERVICES_CHANGE_PORT);
        menu1.add(menuItem19);

        // This is the control for the Services/New REST Service menu item
        menuItem10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NewServiceDialog newServiceDialog = new NewServiceDialog(tg, env);
                GuiHelper.showFrame(newServiceDialog);
            }
        });

        // This is the control for the Services/New Web Service menu item
        menuItem11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NewWebServiceDialog newWebServiceDialog = new NewWebServiceDialog(tg, env);
                GuiHelper.showFrame(newWebServiceDialog);
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
                if (admin.noServices()) {
                    JOptionPane.showMessageDialog(tg, InformationMessages.updateNoService("removed"),
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    RemoveServicesDialog removeServicesDialog = new RemoveServicesDialog(tg, admin);
                    GuiHelper.showFrame(removeServicesDialog);
                }
            }
        });

        // This is the control for the Services/Monitor Service menu item
        menuItem14.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (admin.noServices()) {
                    JOptionPane.showMessageDialog(tg, InformationMessages.updateNoService("monitored"),
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else if (admin.noServiceRunning()) {
                    JOptionPane.showMessageDialog(tg, InformationMessages.updateNoServicesRun("monitored"),
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    MonitorServicesDialog monitorServicesDialog = new MonitorServicesDialog(tg, admin);
                    GuiHelper.showFrame(monitorServicesDialog);
                }
            }
        });

        // This is the control for the Services/Generate Password menu item
        menuItem15.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GeneratePasswordDialog generatePasswordDialog = new GeneratePasswordDialog(tg);
                GuiHelper.showFrame(generatePasswordDialog);
            }
        });

        menuItem16.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (admin.noServices()) {
                    JOptionPane.showMessageDialog(tg, InformationMessages.updateNoServicesAdded("viewed"),
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    ListServiceDetailsDialog listServiceDetailsDialog = new ListServiceDetailsDialog(tg, admin);
                    GuiHelper.showFrame(listServiceDetailsDialog);
                }
            }
        });

        menuItem17.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeleteServicesDialog deleteServicesDialog = new DeleteServicesDialog(tg);
                GuiHelper.showFrame(deleteServicesDialog);
            }
        });

        menuItem18.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdateDeleteExclusionsDialog updateDeleteExclusionsDialog = new UpdateDeleteExclusionsDialog(tg);
                GuiHelper.showFrame(updateDeleteExclusionsDialog);
            }
        });

        menuItem19.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (admin.noServices()) {
                    JOptionPane.showMessageDialog(tg, InformationMessages.updateNoServicesAdded("updated"),
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    UpdateServicesPortDialog updateServicesPortDialog = new UpdateServicesPortDialog(tg, admin);
                    GuiHelper.showFrame(updateServicesPortDialog);
                }
            }
        });

        menuBar.add(menu1);

        JMenu menu2 = new JMenu(MenuConstants.START_SERVICES_MENU);
        JMenuItem menuItem20 = new JMenuItem(MenuConstants.START_SERVICES_MENU_ALL);
        menu2.add(menuItem20);
        JMenuItem menuItem21 = new JMenuItem(MenuConstants.START_SERVICES_MENU);
        menu2.add(menuItem21);
        menu2.addSeparator();
        JMenuItem menuItem23 = new JMenuItem(MenuConstants.RESTART_SERVICES_MENU_ALL);
        menu2.add(menuItem23);
        JMenuItem menuItem24 = new JMenuItem(MenuConstants.RESTART_SERVICES_MENU);
        menu2.add(menuItem24);

        // This is the control for the Start Services/Start All Services menu item
        menuItem20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (admin.noServices()) {
                    JOptionPane.showMessageDialog(tg, InformationMessages.updateNoService("started"),
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else if (admin.allServicesRunning()) {
                    JOptionPane.showMessageDialog(tg, ALL_SERVICES_RUNNING,
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    updateServiceInfo();
                    startAllServices();
                }
            }
        });

        // This is the control for the Start Services/Start Services menu item
        menuItem21.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (admin.noServices()) {
                    JOptionPane.showMessageDialog(tg, InformationMessages.updateNoService("started"),
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else if (admin.allServicesRunning()) {
                    JOptionPane.showMessageDialog(tg, ALL_SERVICES_RUNNING,
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    StartServiceDialog startServiceDialog = new StartServiceDialog(tg, admin);
                    GuiHelper.showFrame(startServiceDialog);
                }
            }
        });

        // This is the control for the Start Services/Restart All Services menu item
        menuItem23.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!admin.noServices() && admin.allServicesRunning()) {
                    stopAllServices();
                    startAllServices();
                } else {
                    if (admin.noServices()) {
                        JOptionPane.showMessageDialog(tg, InformationMessages.updateNoService("restarted"),
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                    } else if (!admin.allServicesRunning()) {
                        JOptionPane.showMessageDialog(tg, NOT_ALL_SERVICES_RUNNING,
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        // This is the control for the Start Services/Restart Services menu item
        menuItem24.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (admin.noServices()) {
                    JOptionPane.showMessageDialog(tg, InformationMessages.updateNoService("restarted"),
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    RestartServiceDialog restartServiceDialog = new RestartServiceDialog(tg, admin);
                    GuiHelper.showFrame(restartServiceDialog);
                }
            }
        });

        menuBar.add(menu2);

        JMenu menu3 = new JMenu(MenuConstants.STOP_SERVICES_MENU);
        JMenuItem menuItem30 = new JMenuItem(MenuConstants.STOP_SERVICES_MENU_ALL);
        menu3.add(menuItem30);
        JMenuItem menuItem31 = new JMenuItem(MenuConstants.STOP_SERVICES_MENU);
        menu3.add(menuItem31);

        // This is the control for the Stop Services/Stop All Services menu item
        menuItem30.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (admin.noServices()) {
                    JOptionPane.showMessageDialog(tg, InformationMessages.updateNoService("stopped"),
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else if (admin.noServiceRunning()) {
                    JOptionPane.showMessageDialog(tg, NO_SERVICES_RUNNING,
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    stopAllServices();
                }
            }
        });

        // This is the control for the Stop Services/Stop Services menu item
        menuItem31.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (admin.noServices()) {
                    JOptionPane.showMessageDialog(tg, InformationMessages.updateNoService("stopped"),
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else if (admin.noServiceRunning()) {
                    JOptionPane.showMessageDialog(tg, NO_SERVICES_RUNNING,
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    StopServiceDialog stopServiceDialog = new StopServiceDialog(tg, admin);
                    GuiHelper.showFrame(stopServiceDialog);
                }
            }
        });

        menuBar.add(menu3);

        JMenu menu5 = new JMenu(MenuConstants.ENDPOINT_TEST_MENU);
        JMenuItem menuItem50 = new JMenuItem(MenuConstants.ENDPOINT_TEST_MENU);
        menu5.add(menuItem50);

        // This is the control for the Endpoint Test/Endpoint Test menu item
        menuItem50.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (admin.noServices()) {
                    JOptionPane.showMessageDialog(tg, NO_SERVICES_ADDED_ENDPOINT,
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else if (admin.noServiceRunning()) {
                    JOptionPane.showMessageDialog(tg, NO_SERVICES_RUNNING_ENDPOINT,
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    EndpointTestDialog endpointTestDialog = new EndpointTestDialog(tg, admin);
                    GuiHelper.showFrame(endpointTestDialog);
                }
            }
        });

        menuBar.add(menu5);

        JMenu menu4 = new JMenu(MenuConstants.HELP_MENU);
        JMenuItem menuItem40 = new JMenuItem(MenuConstants.HELP_MENU);
        menu4.add(menuItem40);
        menu4.addSeparator();
        JMenuItem menuItem42 = new JMenuItem(MenuConstants.HELP_MENU_ABOUT);
        menu4.add(menuItem42);

        // This is the control for the Help/Help menu item
        menuItem40.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HelpDialog helpDialog = new HelpDialog(tg);
                GuiHelper.showFrame(helpDialog);
            }
        });

        // This is the control for the Help/About menu item
        menuItem42.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AboutDialog aboutDialog = new AboutDialog(tg);
                GuiHelper.showFrame(aboutDialog);
            }
        });

        menuBar.add(menu4);
    }

    public void updateServiceInfo() {
        ArrayList<String> tempServices = new ArrayList<>();
        for (Service service : admin.getAllServicesByName()) {
            tempServices.add(service.getName());
        }
        for (String serviceName : tempServices) {
            updateServiceInfo(serviceName);
        }
    }

    public void updateServiceInfo(String serviceName) {
        admin.removeService(serviceName);
        admin.addService(LOCAL_TEST_ENV + "\\" + serviceName);
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
        ScriptRunner sr = new ScriptRunner(filename, service, admin);
        sr.start();
    }

    private void stopAllServices() {
        for (Service service : admin.getAllServicesByName()) {
            if (service.isRunning()) {
                RestTemplate restTemplate = new RestTemplate();
                String endpoint = service.getUrl() + "/shutdown/" + service.getCredentials().getUserId() + "/" +
                    service.getCredentials().getPassword();
                try {
                    restTemplate.postForObject(endpoint, null, String.class);
                } catch (Exception e1) {

                }
                admin.stopServiceRunningByName(service.getName());
            }
        }
        admin.outputServiceStatus();
    }

    private void startAllServices() {
        if (admin.reportHostPortConflict() == 0) {
            int count = 0;
            for (Service service : admin.getAllServicesByName()) {
                if (!service.isRunning()) {
                    prepareAndExecuteOutputFile(service, count++);
                }
            }
        } else {
            JOptionPane.showMessageDialog(tg, ENDPOINT_CONFLICT,
                TITLE, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void updateProjectSelection(String text) {
        projectName = text;
        comp1.setLabelText("The currently selected project is:- " + projectName);
        File file = new File(PROJECT_PATH + "\\" + projectName + ADMIN_SAVE);
        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                admin = (Admin) objectIn.readObject();
                objectIn.close();
                admin.setFreeTextArea(comp0);
                admin.outputServiceStatus();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(tg, NO_PROJ_CONFIG,
                TITLE, JOptionPane.INFORMATION_MESSAGE);
        }
        p1.repaint();
    }

    private void openProjectChoice() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setCurrentDirectory(new File(PROJECT_PATH));
        int returnVal = fc.showDialog(tg, "Select");

        if (returnVal == 0) {
            File file = fc.getSelectedFile();
            if (file.getAbsolutePath().equals(PROJECT_PATH)) {
                JOptionPane.showMessageDialog(tg, NO_PROJ_SELECTED,
                    TITLE, JOptionPane.INFORMATION_MESSAGE);
                menuItem01.doClick();
            } else {
                updateProjectSelection(file.getName());
            }
        }
    }

    private void saveProjectObject() {
        if (admin.noServiceRunning()) {
            if (!StringUtils.isEmpty(projectName)) {
                try {
                    FileOutputStream fileOut = new FileOutputStream(PROJECT_PATH + "\\" + projectName + ADMIN_SAVE);
                    ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                    objectOut.writeObject(admin);
                    objectOut.close();
                    JOptionPane.showMessageDialog(tg, PROJ_SAVED,
                        TITLE, JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(tg, NO_PROJ_SEL,
                    TITLE, JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            int res = JOptionPane.showConfirmDialog(tg, STOP_BEFORE_SAVE,
                TITLE, JOptionPane.YES_NO_OPTION);
            if (res == 0) {
                stopAllServices();
            }
        }
    }
}
