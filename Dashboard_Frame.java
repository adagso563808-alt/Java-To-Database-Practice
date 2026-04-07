package com.mycompany.practice;

import java.awt.*;
import javax.swing.*;

public class Dashboard_Frame extends JFrame {

    public Dashboard_Frame(String username) {

        setSize(600, 400);
        setTitle("Dashboard");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setBackground(Color.DARK_GRAY);
        JLabel topLabel = new JLabel("Welcome, " + username);
        topLabel.setFont(new Font("Digital", Font.BOLD, 18));
        topLabel.setForeground(Color.WHITE);
        northPanel.add(topLabel);

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new GridLayout(5, 1, 5, 5));

        JButton btnAdd = new JButton("Add");
        btnAdd.setFocusPainted(false);
        btnAdd.setBackground(Color.blue);
        btnAdd.setForeground(Color.white);
        JButton btnView = new JButton("View");
        btnView.setFocusPainted(false);
        btnView.setBackground(Color.blue);
        btnView.setForeground(Color.white);
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFocusPainted(false);
        btnUpdate.setBackground(Color.blue);
        btnUpdate.setForeground(Color.white);
        JButton btnDelete = new JButton("Delete");
        btnDelete.setFocusPainted(false);
        btnDelete.setBackground(Color.blue);
        btnDelete.setForeground(Color.white);
        JButton btnExit = new JButton("Exit");
        btnExit.setFocusPainted(false);
        btnExit.setBackground(Color.blue);
        btnExit.setForeground(Color.white);

        westPanel.add(btnAdd);
        westPanel.add(btnView);
        westPanel.add(btnUpdate);
        westPanel.add(btnDelete);
        westPanel.add(btnExit);

        JPanel contentPanel = new JPanel(new BorderLayout());

        add(northPanel, BorderLayout.NORTH);
        add(westPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            contentPanel.removeAll();
            
        });

    }
}
