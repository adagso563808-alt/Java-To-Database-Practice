package com.mycompany.practice;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import org.mindrot.jbcrypt.BCrypt;

public class Login_Frame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPassword;

    public Login_Frame() {

        setTitle("Login");
        setSize(280, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        showPassword = new JCheckBox("Show Password");

        add(new JLabel("Username"));
        add(usernameField);
        add(new JLabel("Password"));
        add(passwordField);
        add(showPassword);

        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });

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
            char[] passwordChar = passwordField.getPassword();

            if (login(username, passwordChar)) {
                JOptionPane.showMessageDialog(null, "Login successful!");
                new Dashboard_Frame().setVisible(true); 
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Wrong credentials.");
            }
        });

        btnCreateAccount.addActionListener(e -> {

            new Create_Account_Frame().setVisible(true);
        });
    }

    private boolean login(String username, char[] passwordChar) {

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username field is empty!");
            return false;
        }

        if (passwordChar.length == 0) {
            JOptionPane.showMessageDialog(this, "Password field is empty!");
            return false;
        }

        Connection connection = XamppConnection.connection();
        if (connection != null) {
            System.out.println("Connected!");
        } else {
            System.out.println("Failed to connect.");
        }

        String sql = "SELECT * FROM Users WHERE Username = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("Password");
                String inputPassword = new String(passwordChar);
                return BCrypt.checkpw(inputPassword, storedHash);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
