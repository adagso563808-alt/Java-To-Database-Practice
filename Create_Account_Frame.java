package com.mycompany.practice;

import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.mindrot.jbcrypt.BCrypt;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Create_Account_Frame extends JFrame {

    private JTextField usernameField, emailAddressField, contactNumberField;
    private JPasswordField passwordField;

    public Create_Account_Frame() {
        setSize(280, 300);
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        usernameField = new JTextField(20);
        emailAddressField = new JTextField(20);
        contactNumberField = new JTextField(20);
        passwordField = new JPasswordField(20);

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Email Address:"));
        add(emailAddressField);
        add(new JLabel("Contact Number:"));
        add(contactNumberField);

        JButton register = new JButton("Register");
        register.setFocusPainted(false);
        register.setBackground(Color.green);
        register.setForeground(Color.black);

        add(register);

        register.addActionListener(e -> {
            register();
            new Login_Frame().setVisible(true);
            dispose();
        });

    }

    private void register() {

        String username = usernameField.getText();
        String emailAddress = emailAddressField.getText();
        String contactNumber = contactNumberField.getText();
        char[] passwordChar = passwordField.getPassword();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username is empty!");
            return;
        } else if (passwordChar.length == 0) {
            JOptionPane.showMessageDialog(null, "Password is empty!");
            return;
        } else if (emailAddress.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email address is empty!");
            return;
        } else if (contactNumber.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Contact number is empty!");
            return;
        }

        Connection connection = XamppConnection.connection();
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Failed to connect database.");
            return;
        }

        String sql = "INSERT INTO Users(Username, Password, Email_Address, Contact_Number) VALUES(?,?,?,?);";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String password = new String(passwordChar);
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.setString(3, emailAddress);
            ps.setString(4, contactNumber);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Account created successfully!");
            ps.close();
            usernameField.setText("");
            passwordField.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
