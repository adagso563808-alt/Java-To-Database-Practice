package com.mycompany.practice;

import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class Create_Account_Frame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public Create_Account_Frame() {

        setSize(280, 300);
        setTitle("Register");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        add(new JLabel("Username"));
        add(usernameField);
        add(new JLabel("Password"));
        add(passwordField);

        JButton register = new JButton("Register");
        register.setFocusPainted(false);
        register.setBackground(Color.green);
        register.setForeground(Color.white);

        add(register);

        register.addActionListener(e -> {
            registerUser();
        });
    }

    private void registerUser() {
        String username = usernameField.getText();
        char[] passwordChar = passwordField.getPassword();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username is empty!");
            return;
        }

        if (passwordChar.length() == 0) {
            JOptionPane.showMessageDialog(this, "Password is empty!");
            return;
        }

        Connection connection = XamppConnection.connection();
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database connection failed.");
            return;
        }

        String sqlQuery = "INSERT INTO Users (Username, Password) VALUES(?,?);";

        try (PreparedStatement pStatement = connection.prepareStatement(sqlQuery)) {
            String password = new String(passwordChar);
            pStatement.setString(1, username);
            pStatement.setString(2, password);
            pStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Account created successfully!");
            this.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
