/* This file is auto-generated by the ScriptDirectedGui program.                  */
/* Please do not modify directly as the code cannot then be guaranteed to operate */
/* correctly. However, please feel free to copy the source into a project.        */

package com.ptconsultancy.createdgui;

import static com.ptconsultancy.constants.InformationMessages.NO_SERVICE_SELECT;
import static com.ptconsultancy.constants.ServiceAdminConstants.DELETE_TYPE;
import static com.ptconsultancy.constants.ServiceAdminConstants.FALSE;
import static com.ptconsultancy.constants.ServiceAdminConstants.GET_TYPE;
import static com.ptconsultancy.constants.ServiceAdminConstants.HEALTHCHECK;
import static com.ptconsultancy.constants.ServiceAdminConstants.JSON;
import static com.ptconsultancy.constants.ServiceAdminConstants.MAIN_HEADING;
import static com.ptconsultancy.constants.ServiceAdminConstants.POST_TYPE;
import static com.ptconsultancy.constants.ServiceAdminConstants.PUT_TYPE;
import static com.ptconsultancy.constants.ServiceAdminConstants.SECURITY_TOKEN;
import static com.ptconsultancy.constants.ServiceAdminConstants.STANDARD_SEPARATOR;
import static com.ptconsultancy.constants.ServiceAdminConstants.STANDARD_TEXTAREA_LABEL;
import static com.ptconsultancy.constants.ServiceAdminConstants.TEXT;
import static com.ptconsultancy.constants.ServiceAdminConstants.TRUE;
import static com.ptconsultancy.constants.ServiceAdminConstants.XML;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ptconsultancy.admin.Admin;
import com.ptconsultancy.admin.Service;
import com.ptconsultancy.constants.ErrorMessages;
import com.ptconsultancy.domain.guicomponents.FreeButton;
import com.ptconsultancy.domain.guicomponents.FreeLabel;
import com.ptconsultancy.domain.guicomponents.FreeLabelComboBoxPair;
import com.ptconsultancy.domain.guicomponents.FreeLabelTextFieldPair;
import com.ptconsultancy.domain.guicomponents.FreeRadioButton;
import com.ptconsultancy.domain.guicomponents.FreeRadioButtonGroup;
import com.ptconsultancy.domain.guicomponents.FreeTextArea;
import com.ptconsultancy.domain.guis.GuiHelper;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

public class EndpointTestDialog extends JFrame {

    private static final String TITLE = "Endpoint Test";
    private static final int FRAME_X_SIZE = 700;
    private static final int FRAME_Y_SIZE = 900;
    private Color col = new Color(230, 255, 255);

    private static final String BODY_TEXTAREA_LABEL = "Body:";

    JPanel p1 = new JPanel();
    // This is the output text area and is on screen at all times
    FreeTextArea output = new FreeTextArea(col, STANDARD_TEXTAREA_LABEL, 30, 240, 200, 635, 480, FALSE);

    private EndpointTestDialog tg = this;

    private MainDialog mainDialog;

    private Admin admin;

    private boolean getLastSelected = true;

    private RestTemplate restTemplate;

    private FreeLabelTextFieldPair comp2 = new FreeLabelTextFieldPair(col, "Please enter endpoint:", 30, 190, 240);

    @Autowired
    public EndpointTestDialog(MainDialog mainDialog, Admin admin) {
        this.mainDialog = mainDialog;
        this.admin = admin;
        this.restTemplate = new RestTemplate();

        mainDialog.setEnabled(false);

        this.setTitle(TITLE);
        this.setSize(FRAME_X_SIZE, FRAME_Y_SIZE);

        p1.setLayout(null);
        p1.setBackground(col);

        int xpos = FRAME_X_SIZE / 2 - 150;
        FreeLabel l0 = new FreeLabel(MAIN_HEADING, 30, 30, 500, 30, new Font("", Font.BOLD + Font.ITALIC, 20));

        FreeButton b0 = new FreeButton("Test", xpos, 800, 80);

        FreeButton b1 = new FreeButton(FreeButton.CLEAR, xpos + 110, 800, 80);

        FreeButton b2 = new FreeButton(FreeButton.CANCEL, xpos + 220, 800, 80);

        FreeButton b3 = new FreeButton("Restart", 500, 140, 80);
        b3.setVisible(false);

        FreeButton b4 = new FreeButton("Healthcheck", 500, 190, 120);
        FreeButton b5 = new FreeButton("Build URL", 500, 140, 120);

        FreeRadioButton rb0 = new FreeRadioButton(col, GET_TYPE, 30, 90, 50, 20);
        rb0.setSelected();
        FreeRadioButton rb1 = new FreeRadioButton(col, POST_TYPE, 80, 90, 60, 20);
        FreeRadioButton rb2 = new FreeRadioButton(col, PUT_TYPE, 140, 90, 50, 20);
        FreeRadioButton rb3 = new FreeRadioButton(col, DELETE_TYPE, 190, 90, 90, 20);

        FreeRadioButton rb4 = new FreeRadioButton(col, TEXT, 330, 230, 50, 20);
        rb4.setSelected();
        FreeRadioButton rb5 = new FreeRadioButton(col, JSON, 380, 230, 60, 20);
        FreeRadioButton rb6 = new FreeRadioButton(col, XML, 440, 230, 50, 20);

        FreeRadioButtonGroup comp0 = new FreeRadioButtonGroup();
        ArrayList<FreeRadioButton> items0 = new ArrayList<>();
        items0.add(rb4);
        items0.add(rb5);
        items0.add(rb6);
        comp0.addButtons(items0);

        FreeRadioButtonGroup rbgType = new FreeRadioButtonGroup();
        ArrayList<FreeRadioButton> rbgTypeButtons = new ArrayList<>();
        rbgTypeButtons.add(rb0);
        rbgTypeButtons.add(rb1);
        rbgTypeButtons.add(rb2);
        rbgTypeButtons.add(rb3);
        rbgType.addButtons(rbgTypeButtons);

        ArrayList<String> items1 = new ArrayList<String>();
        for (Service service : admin.getAllServicesByName()) {
            if (service.isRunning()) {
                items1.add(service.getName());
            }
        }
        FreeLabelComboBoxPair comp1 = new FreeLabelComboBoxPair(col, "Please select the service name:", 30, 140, 240, items1);

        comp2.getTextField().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               b0.doClick();
            }
        });

        // This is the Body text area and is only used when doing an active pasing of data (such as POST or PUT)
        FreeTextArea body = new FreeTextArea(col, BODY_TEXTAREA_LABEL, 30, 240, 200, 635, 220, FALSE);
        body.getPanel().setVisible(false);

        // This is the control for the Test button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (comp1.getComboBox().getSelectedIndex() > 0 && !StringUtils.isEmpty(comp2.getText())) {
                    if (rb0.isSelected()) {
                        String url = getUrl(comp1.getSelectedItem(), comp2.getText());
                        output.clearTextArea();
                        try {
                            String response = restTemplate.getForObject(url, String.class);
                            if (rb5.isSelected()) {
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                JsonParser jp = new JsonParser();
                                JsonElement je = jp.parse(response);
                                String prettyJsonString = gson.toJson(je);
                                output.appendNewLine(prettyJsonString);
                            } else if (rb4.isSelected()) {
                                output.appendNewLine(response);
                            }
                        } catch (Exception e1) {
                            output.appendNewLine("Exception - " + e1.getMessage());
                        }
                    } else if (rb1.isSelected()) {
                        String url = getUrl(comp1.getSelectedItem(), comp2.getText());
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            JsonNode request = mapper.readTree(body.getText());
                            String response = restTemplate.postForObject(url, request, String.class);
                            output.clearTextArea();
                            output.appendNewLine(response);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else if (rb2.isSelected()) {

                    } else if (rb3.isSelected()) {
                        try {
                            URI uri = new URI(getUrl(comp1.getSelectedItem(), comp2.getText()));
                            restTemplate.delete(uri);
                        } catch (URISyntaxException e1) {
                            e1.printStackTrace();
                        }
                    }

                    if (output.getText().contains("Cannot issue a new token")) {
                        b3.setVisible(true);
                        b5.setVisible(false);
                        p1.repaint();
                    }
                } else {
                    if (comp1.getComboBox().getSelectedIndex() == 0) {
                        JOptionPane.showMessageDialog(tg, NO_SERVICE_SELECT, TITLE, JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(tg, ErrorMessages.ENDPOINT_ERROR, TITLE, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        // This is the control for the Clear button
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rb0.doClick();
                rb4.setSelected();
                output.clearTextArea();
                body.clearTextArea();
                comp1.getComboBox().setSelectedIndex(1);
                comp2.setText(HEALTHCHECK);
                b4.doClick();
            }
        });

        // This is the control for the Cancel-implement button
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainDialog.setEnabled(TRUE);
                tg.dispose();
            }
        });

        // This is the control for the Restart button
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (admin.getServiceByName(comp1.getSelectedItem()).isRunning()) {
                    RestTemplate restTemplate = new RestTemplate();
                    Service service = admin.getServiceByName(comp1.getSelectedItem());
                    String endpoint = service.getUrl() + "/shutdown/" + service.getCredentials().getUserId() + "/"+
                        service.getCredentials().getPassword();
                    try {
                        restTemplate.postForObject(endpoint, null, String.class);
                    } catch(Exception e1) {

                    }
                    admin.stopServiceRunningByName(service.getName());
                    admin.outputServiceStatus();
                }
                mainDialog.prepareAndExecuteOutputFile(admin.getServiceByName(comp1.getSelectedItem()), 0);
                b2.doClick();
            }
        });

        // This is the control for the Healthcheck button
        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (comp1.getComboBox().getSelectedIndex() > 0) {
                    comp2.setText(HEALTHCHECK);
                    b0.doClick();
                } else {
                    JOptionPane.showMessageDialog(tg, NO_SERVICE_SELECT, TITLE, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // This is the control for the Build URL button
        b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (comp1.getComboBox().getSelectedIndex() > 0) {
                    BuildEndpointURLDialog buildEndpointURLDialog = new BuildEndpointURLDialog(admin, tg, comp1.getSelectedItem());
                    GuiHelper.showFrame(buildEndpointURLDialog);
                } else {
                    JOptionPane.showMessageDialog(tg, NO_SERVICE_SELECT, TITLE, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // This is the control for the Get radio button
        rb0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                body.getPanel().setVisible(FALSE);
                resizeOutputForGet();
                getLastSelected = true;
            }
        });

        // This is the control for the Post radio button
        rb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                body.getPanel().setVisible(TRUE);
                resizeOutputForPost();
            }
        });

        // This is the control for the Put radio button
        rb2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                body.getPanel().setVisible(TRUE);
                resizeOutputForPost();
            }
        });

        // This is the control for the Delete radio button
        rb3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                body.getPanel().setVisible(TRUE);
                resizeOutputForPost();
            }
        });

        p1.add(b0);
        p1.add(b1);
        p1.add(b2);
        p1.add(b3);
        p1.add(b4);
        p1.add(b5);
        p1.add(rb0);
        p1.add(rb1);
        p1.add(rb2);
        p1.add(rb3);
        p1.add(comp1.getPanel());
        p1.add(comp2.getPanel());
        p1.add(rb4);
        p1.add(rb5);
        p1.add(rb6);
        p1.add(body.getPanel());
        p1.add(output.getPanel());
        p1.add(l0);
        this.add(p1);
    }

    private String getSecurityToken(Service service) {
        String secureUrl = service.getUrl() + STANDARD_SEPARATOR  + SECURITY_TOKEN;
        return restTemplate.getForObject(secureUrl, String.class);
    }

    private String getUrl(String serviceName, String endpoint) {

        Service service = admin.getServiceByName(serviceName);
        String url = service.getUrl() + STANDARD_SEPARATOR + endpoint;
        if (!endpoint.equals(HEALTHCHECK)) {
            url = url + STANDARD_SEPARATOR + getSecurityToken(service);
        }

        return url;
    }

    private void resizeOutputForGet() {
        p1.remove(output.getPanel());
        output = new FreeTextArea(col, STANDARD_TEXTAREA_LABEL, 30, 240, 200, 635, 480, FALSE);
        p1.add(output.getPanel());
        this.repaint();
    }

    private void resizeOutputForPost() {
        if (getLastSelected) {
            p1.remove(output.getPanel());
            output = new FreeTextArea(col, STANDARD_TEXTAREA_LABEL, 30, 490, 200, 635, 220, FALSE);
            p1.add(output.getPanel());
            this.repaint();
            getLastSelected = false;
        }
    }

    public void setEndpointURL(String url) {
        comp2.setText(url);
    }
}
