package com.earthmonitor.app.ui;

import com.earthmonitor.app.ui.components.GradientPanel;
import com.earthmonitor.model.User;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private final User currentUser;
    private final JButton viewBtn = new JButton("View Observations");
    private final JButton insertBtn = new JButton("Insert Observation");
    private final JButton updateBtn = new JButton("Update Observation (Admin)");
    private final JButton logoutBtn = new JButton("Logout");

    public enum Action { VIEW, INSERT, UPDATE }

    public DashboardFrame(User user) {
        super("Planet Earth Monitoring — Dashboard");
        this.currentUser = user;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        GradientPanel card = new GradientPanel();
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(card, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel welcome = new JLabel("Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
        welcome.setForeground(Color.WHITE);
        welcome.setFont(welcome.getFont().deriveFont(Font.BOLD, 20f));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        card.add(welcome, c);

        c.gridy++; c.gridwidth = 1;
        card.add(viewBtn, c);
        c.gridx = 1;
        if (user.getRole() == User.Role.ADMIN) card.add(insertBtn, c);

        c.gridx = 0; c.gridy++;
        if (user.getRole() == User.Role.ADMIN) {
            c.gridwidth = 2;
            card.add(updateBtn, c);
        }

        c.gridy++; c.gridwidth = 2;
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        bottom.add(logoutBtn);
        card.add(bottom, c);

        // Actions
        viewBtn.addActionListener(e -> openAction(Action.VIEW));
        insertBtn.addActionListener(e -> openAction(Action.INSERT));
        updateBtn.addActionListener(e -> openAction(Action.UPDATE));
        logoutBtn.addActionListener(e -> onLogout());
    }

    private void openAction(Action action) {
        SwingUtilities.invokeLater(() -> new ActionFrame(currentUser, action).setVisible(true));
    }

    private void onLogout() {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }
}
