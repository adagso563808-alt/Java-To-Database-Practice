package com.mycompany.practice;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class Login_Frame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private final String USERNAME = "admin";
    private final String PASSWORD = "1234";

    public Login_Frame() {

        setSize(280, 300);
        setTitle("Login Frame");
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);

        JButton btnLogin = new JButton("Login");
        btnLogin.setFocusPainted(false);
        btnLogin.setBackground(Color.blue);
        btnLogin.setForeground(Color.white);
        JButton btnCreateAccount = new JButton("Create Account");
        btnCreateAccount.setFocusPainted(false);
        btnCreateAccount.setBackground(Color.red);
        btnCreateAccount.setForeground(Color.white);

        add(btnLogin);
        add(btnCreateAccount);

        btnLogin.addActionListener(e -> {

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (usernameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill up username field.");
            }
            if (passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this, "Please fill up password field.");
            }

            if (login(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        });

        btnCreateAccount.addActionListener(e -> {
            new Create_Account_Frame().setVisible(true);
        });
    }

    private boolean login(String username, String password) {
        Connection connection = XamppConnection.connection();
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database connection failed.");
            return false;
        }

        String sqlQuery = "SELECT * FROM Users WHERE Username = ? AND Password = ?;";

        try (PreparedStatement pStatement = connection.prepareStatement(sqlQuery)) {
            pStatement.setString(1, username);
            pStatement.setString(2, password);
            ResultSet rs = pStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return false;
    }
}
