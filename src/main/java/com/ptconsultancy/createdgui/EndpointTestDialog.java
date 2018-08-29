/* This file is auto-generated by the ScriptDirectedGui program.                  */
/* Please do not modify directly as the code cannot then be guaranteed to operate */
/* correctly. However, please feel free to copy the source into a project.        */

package com.ptconsultancy.createdgui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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

        FreeRadioButton rb4 = new FreeRadioButton(col, "Text", 330, 230, 50, 20);
        rb4.setSelected();
        FreeRadioButton rb5 = new FreeRadioButton(col, "JSON", 380, 230, 60, 20);
        FreeRadioButton rb6 = new FreeRadioButton(col, "XML", 440, 230, 50, 20);

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
        items1.add("--Select");
        for (Service service : admin.getAllServicesByName()) {
            if (service.isRunning()) {
                items1.add(service.getName());
            }
        }
        FreeLabelComboBoxPair comp1 = new FreeLabelComboBoxPair(col, "Please select the service name:", 30, 140, 240, items1);

        FreeLabelTextFieldPair comp2 = new FreeLabelTextFieldPair(col, "Please enter endpoint:", 30, 190, 240);

        comp2.getTextField().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               b0.doClick();
            }
        });

        // This is the Body text area and is only used when doing an active pasing of data (such as POST or PUT)
        FreeTextArea body = new FreeTextArea(col, "Body:", 30, 240, 200, 635, 220, false);
        body.getPanel().setVisible(false);

        // This is the control for the Test button
        b0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (comp1.getComboBox().getSelectedIndex() > 0 && !StringUtils.isEmpty(comp2.getText())) {
                    RestTemplate restTemplate = new RestTemplate();
                    Service service = admin.getServiceByName(comp1.getSelectedItem());
                    String url = service.getUrl() + "/" + comp2.getText();
                    if (rb0.isSelected()) {
                        String token;
                        if (!comp2.getText().equals("healthcheck")) {
                            token = getSecurityToken(restTemplate, service);
                            url = url + "/" + token;
                        }
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
                        String token = getSecurityToken(restTemplate, service);
                        url = url + "/" + service.getCredentials().getUserId() + "/" + service.getCredentials().getPassword() + "/" + token;
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
                        String token = getSecurityToken(restTemplate, service);
                        url = url + "/" + service.getCredentials().getUserId() + "/" + service.getCredentials().getPassword() + "/" + token;
                        try {
                            URI uri = new URI(url);
                            restTemplate.delete(uri);
                        } catch (URISyntaxException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    if (comp1.getComboBox().getSelectedIndex() == 0) {
                        JOptionPane.showMessageDialog(tg, "No service selected - please select a service before continuing",
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(tg, "No endpoint selected - please select an endpoint before continuing",
                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        // This is the control for the Clear button
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rb0.doClick();
                output.clearTextArea();
                body.clearTextArea();
                comp1.getComboBox().setSelectedIndex(0);
                comp2.setText("");
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
        p1.add(rb4);
        p1.add(rb5);
        p1.add(rb6);
        p1.add(body.getPanel());
        p1.add(output.getPanel());
        p1.add(l0);
        this.add(p1);
    }

    private String getSecurityToken(RestTemplate restTemplate, Service service) {
        String secureUrl = service.getUrl() + "/securitytoken";
        return restTemplate.getForObject(secureUrl, String.class);
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
