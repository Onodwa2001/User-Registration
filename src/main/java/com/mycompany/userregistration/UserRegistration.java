/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.userregistration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Numata
 */
public class UserRegistration {
    
    private final String DATABASE_URI = "jdbc:derby://localhost:1527/Hackathon2022";
    String title, fname, lname, gender, email, password;
    
    public UserRegistration(String title, String fname, String lname, 
            String gender, String email, String password) {
        this.title = title;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }
    
    public void save() {
        Connection conn = null;
        Statement statement = null;
        int ok;
        
        try {
            conn = DriverManager.getConnection(DATABASE_URI, "Administrator", "password");
            statement = conn.createStatement();
            ok = statement.executeUpdate(String.format("INSERT INTO USERREGISTRATION VALUES('%s', '%s', '%s', '%s', '%s', '%s')", 
                    title, fname, lname, gender, email, password));
            
            if (ok > 0) {
                JOptionPane.showMessageDialog(null, "User successfully created");
            } else {
                JOptionPane.showMessageDialog(null, "An error occured");
            }
        } catch(SQLException sql) {
            JOptionPane.showMessageDialog(null, "Error: " + sql.getMessage());
        } finally {
            try{
                conn.close();
                statement.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An error occurred: " + ex);
            }
        }
    }
    
}
