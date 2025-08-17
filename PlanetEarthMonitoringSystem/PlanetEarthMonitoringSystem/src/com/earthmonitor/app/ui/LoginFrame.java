package com.earthmonitor.app.ui;

import com.earthmonitor.app.ui.components.GradientPanel;
import com.earthmonitor.exception.MyException;
import com.earthmonitor.model.User;
import com.earthmonitor.service.AuthService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField usernameField = new JTextField(16);
    private final JPasswordField passwordField = new JPasswordField(16);
    private final JButton loginBtn = new JButton("Login");
    private final JButton registerBtn = new JButton("Register (Admin Only)");

    private final AuthService authService = new AuthService();

    public LoginFrame() {
        super("Planet Earth Monitoring — Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 380);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        GradientPanel card = new GradientPanel();
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;

        JLabel title = new JLabel("Planet Earth Monitoring:");
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(title, c);

        c.gridy++;
        JLabel subtitle = new JLabel("Manual environmental observations");
        subtitle.setForeground(Color.WHITE);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(subtitle, c);

        c.gridy++; c.gridwidth = 1;
        JLabel userLbl = new JLabel("Username"); userLbl.setForeground(Color.WHITE);
        card.add(userLbl, c);

        c.gridx = 1; card.add(usernameField, c);

        c.gridx = 0; c.gridy++;
        JLabel passLbl = new JLabel("Password"); passLbl.setForeground(Color.WHITE);
        card.add(passLbl, c);

        c.gridx = 1; card.add(passwordField, c);

        c.gridx = 0; c.gridy++; c.gridwidth = 2;
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttons.setOpaque(false);
        buttons.add(loginBtn);
        buttons.add(registerBtn);
        card.add(buttons, c);

        add(card, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> onLogin());
        registerBtn.addActionListener(e -> onRegister());
    }

    private void onLogin() {
        String u = usernameField.getText().trim();
        String p = new String(passwordField.getPassword());
        try {
            User user = authService.login(u, p);
            SwingUtilities.invokeLater(() -> {
                new DashboardFrame(user).setVisible(true);
                dispose();
            });
        } catch (MyException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onRegister() {
        String u = JOptionPane.showInputDialog(this, "New admin username:");
        if (u == null || u.isBlank()) return;
        JPasswordField pw1 = new JPasswordField();
        JPasswordField pw2 = new JPasswordField();
        int ok = JOptionPane.showConfirmDialog(this, new Object[]{"Password:", pw1, "Confirm:", pw2}, "Set Admin Password", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;
        String p1 = new String(pw1.getPassword());
        String p2 = new String(pw2.getPassword());
        if (!p1.equals(p2)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            authService.register(u, p1, User.Role.ADMIN);
            JOptionPane.showMessageDialog(this, "Admin created. You can log in now.");
        } catch (MyException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
