/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.userregistration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Numata
 */
public class UserRegistrationGUI extends JFrame implements ActionListener {

    JPanel panelCenter, panelEast, panelWest, panelSouth, wrapperPanel, genderPanel;
    JLabel title, firstName, lastName, gender, email, password, confirm, terms;
    JLabel space, firstNameError, lastNameError, genderError, emailError, passwordError, confirmError;
    JComboBox titleList;
    JTextField firstNameTxt, lastNameTxt, emailTxt;
    JPasswordField passwordInput, confirmInput;
    JRadioButton femaleRadio, maleRadio;
    JCheckBox checkBoxTerms;
    JButton saveBtn, clearBtn, exitBtn;
    ButtonGroup bg = new ButtonGroup();
    DefaultComboBoxModel model = new DefaultComboBoxModel();

    public UserRegistrationGUI() {
        super("User Registration");

        // panel instantiations
        wrapperPanel = new JPanel();
        panelCenter = new JPanel();
        panelEast = new JPanel();
        panelWest = new JPanel();
        panelSouth = new JPanel();
        genderPanel = new JPanel();

        // label instantiation
        title = new JLabel("Title:");
        firstName = new JLabel("First Name:");
        lastName = new JLabel("Last Name:");
        gender = new JLabel("Gender");
        email = new JLabel("Email:");
        password = new JLabel("Password:");
        confirm = new JLabel("Confirm Password:");
        terms = new JLabel("Terms and Conditions:");

        // list, textfield, radio and check buttons
        titleList = new JComboBox(new String[]{"Dr", "Miss", "Mr", "Mrs", "Prof"});
        firstNameTxt = new JTextField();
        lastNameTxt = new JTextField();
        femaleRadio = new JRadioButton("Female");
        maleRadio = new JRadioButton("Male");
        emailTxt = new JTextField();
        passwordInput = new JPasswordField();
        confirmInput = new JPasswordField();
        checkBoxTerms = new JCheckBox("I agree to the terms and conditions");

        // error labels instantiations 
        space = new JLabel();
        firstNameError = new JLabel();
        lastNameError = new JLabel();
        genderError = new JLabel();
        emailError = new JLabel();
        passwordError = new JLabel();
        confirmError = new JLabel();

        // button instations
        saveBtn = new JButton("Save");
        clearBtn = new JButton("Clear");
        exitBtn = new JButton("Exit");
    }

    public void setGUI() {
        // panels
        add(wrapperPanel, BorderLayout.CENTER);
        add(panelSouth, BorderLayout.SOUTH);

        // add panels inside wrapper panel
        wrapperPanel.add(panelWest, BorderLayout.WEST);
        wrapperPanel.add(panelCenter, BorderLayout.CENTER);
        wrapperPanel.add(panelEast, BorderLayout.EAST);

        // setting layout of panels
        wrapperPanel.setLayout(new GridLayout(1, 3));
        panelWest.setLayout(new GridLayout(8, 1));
        panelCenter.setLayout(new GridLayout(8, 1));
        panelEast.setLayout(new GridLayout(8, 1));
        panelSouth.setLayout(new GridLayout(1, 3));
        genderPanel.setLayout(new GridLayout(1, 2));

        // adding content to panel left
        panelWest.add(title);
        panelWest.add(firstName);
        panelWest.add(lastName);
        panelWest.add(gender);
        panelWest.add(email);
        panelWest.add(password);
        panelWest.add(confirm);
        panelWest.add(terms);

        // adding content to panel center
        panelCenter.add(titleList);
        panelCenter.add(firstNameTxt);
        panelCenter.add(lastNameTxt);
        panelCenter.add(genderPanel);
        panelCenter.add(emailTxt);
        panelCenter.add(passwordInput);
        panelCenter.add(confirmInput);
        panelCenter.add(checkBoxTerms);

        // add radio buttons
        genderPanel.add(femaleRadio);
        genderPanel.add(maleRadio);

        // add error messages
        panelEast.add(space);
        panelEast.add(firstNameError);
        panelEast.add(lastNameError);
        panelEast.add(genderError);
        panelEast.add(emailError);
        panelEast.add(passwordError);
        panelEast.add(confirmError);

        // add radioButtons to ButtonGroup
        bg.add(femaleRadio);
        bg.add(maleRadio);

        // add buttons to south panel
        panelSouth.add(saveBtn);
        panelSouth.add(exitBtn);
        panelSouth.add(clearBtn);

        // add action listeners to buttons
        saveBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        clearBtn.addActionListener(this);

        // add listener to checkbox
        checkBoxTerms.addActionListener(this);

        // disable save button
        saveBtn.setEnabled(false);

        // make gui visible
        setVisible(true);
        setSize(800, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == exitBtn) {
            System.exit(0);
        } else if (ae.getSource() == clearBtn) {
            model.setSelectedItem("Dr");
            firstNameTxt.setText("");
            lastNameTxt.setText("");
            bg.clearSelection();
            emailTxt.setText("");
            passwordInput.setText("");
            confirmInput.setText("");
            checkBoxTerms.setSelected(false);
            
            firstNameError.setText("");
            lastNameError.setText("");
            genderError.setText("");
            emailError.setText("");
            passwordError.setText("");
            confirmError.setText("");
        } else if (ae.getSource() == checkBoxTerms) {
            // disable and enable button based on whether checkbox is checked or not
            if (checkBoxTerms.isSelected()) {
                saveBtn.setEnabled(true);
            } else {
                saveBtn.setEnabled(false);
            }
        } else {
            // save users to database
            String titleStr = String.valueOf(titleList.getSelectedItem());
            String firstNameStr = firstNameTxt.getText();
            String lastNameStr = lastNameTxt.getText();
            String result = handleSelection();
            String emailStr = emailTxt.getText();
            String passwordStr = String.valueOf(passwordInput.getPassword());
            String confirmStr = String.valueOf(confirmInput.getPassword());

            ArrayList<JLabel> errorJLabels = new ArrayList<>();
            errorJLabels.add(firstNameError);
            errorJLabels.add(lastNameError);
            errorJLabels.add(emailError);
            errorJLabels.add(passwordError);
            errorJLabels.add(confirmError);

            ArrayList<JTextField> jtxtList = new ArrayList<>();
            jtxtList.add(firstNameTxt);
            jtxtList.add(lastNameTxt);
            jtxtList.add(emailTxt);
            jtxtList.add(passwordInput);
            jtxtList.add(confirmInput);

            String[] errors = {"First name is required", "Last name is required",
                "Email is required", "Password is required", "Please confirm password"};

            if (!passwordStr.equals(confirmStr)) {
                confirmError.setForeground(Color.red);
                confirmError.setText("Passwords don't match");
            } else if (!(firstNameStr.equals("") || lastNameStr.equals("") || emailStr.equals("")
                    || passwordStr.equals("") || confirmStr.equals(""))) {
                UserRegistration user = new UserRegistration(titleStr, firstNameStr, lastNameStr,
                        result, emailStr, passwordStr);
                if (!user.isUnique()) {
                    JOptionPane.showMessageDialog(null, "This user already exists");
                    return;
                } else if (!isEmail(emailTxt.getText())) {
                    emailError.setForeground(Color.red);
                    emailError.setText("Not a valid email");
                    return;
                }

                user.save();
            } else {
                for (int i = 0; i < jtxtList.size(); i++) {
                    if (jtxtList.get(i).getText().equals("")) { // if empty
                        errorJLabels.get(i).setForeground(Color.red);
                        errorJLabels.get(i).setText(errors[i]);
                    } else if (!jtxtList.get(i).getText().equals("")) {
                        errorJLabels.get(i).setText("");
                    }
                }
            }

        }
    }

    public boolean isEmail(String value) {
        Pattern pattern = Pattern.compile("^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n"
                + ")*@[A-Z0-9-]+(?:\\.[A-Z0-9-]+)*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    public boolean isEmpty(String titleStr, String firstNameStr,
            String lastNameStr, String emailStr, String passwordStr, String confirmStr) {
        boolean valid = !(titleStr.equals("") && firstNameStr.equals("") && emailStr.equals("")
                && passwordStr.equals("") && confirmStr.equals(""));
        return valid;
    }

    /**
     *
     * @return Male or female based on value selected
     */
    public String handleSelection() {
        String result = "";
        try {
            if (bg.getSelection().isSelected()) {
                result = femaleRadio.isSelected() ? "Female" : "Male";
            }
        } catch (NullPointerException nul) {
            genderError.setForeground(Color.red);
            genderError.setText("Please select gender");

        }
        return result;
    }

}

class ComboItem {

    private String key;
    private String value;

    public ComboItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
