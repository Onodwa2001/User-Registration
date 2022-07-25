/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.userregistration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Numata
 */
public class UserRegistration {
    
    private String DATABASE_URI = "jdbc:derby://localhost:1527/Hackathon2022";
    private String title;
    private String fname;
    private String lname;
    private String gender;
    private String email;
    private String password;
    
    public UserRegistration(String title, String fname, String lname, 
            String gender, String email, String password) {
        this.title = title;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    /**
     * @return the DATABASE_URI
     */
    public String getDATABASE_URI() {
        return DATABASE_URI;
    }

    /**
     * @param DATABASE_URI the DATABASE_URI to set
     */
    public void setDATABASE_URI(String DATABASE_URI) {
        this.DATABASE_URI = DATABASE_URI;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return the lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * @param lname the lname to set
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isUnique() {
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        boolean unique = false;
        
        try {
            conn = DriverManager.getConnection(getDATABASE_URI(), "Administrator", "password");
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM userregistration WHERE email = '" + email + "'");
            
            if (rs.next() == false) {
                unique = true;
            }
            
        } catch(SQLException sql) {
            System.out.println(sql);
            unique = false;
        }
        
        return unique;
    }
    
    public void save() {
        Connection conn = null;
        Statement statement = null;
        int ok;
        
        try {
            conn = DriverManager.getConnection(getDATABASE_URI(), "Administrator", "password");
            statement = conn.createStatement();
            ok = statement.executeUpdate(String.format("INSERT INTO USERREGISTRATION VALUES('%s', '%s', '%s', '%s', '%s', '%s')", getTitle(), getFname(), getLname(), getGender(), getEmail(), getPassword()));
            
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
