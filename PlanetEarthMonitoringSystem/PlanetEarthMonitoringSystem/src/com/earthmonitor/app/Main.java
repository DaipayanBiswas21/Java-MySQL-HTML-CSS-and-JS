package com.earthmonitor.app;

import java.sql.SQLException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.earthmonitor.app.ui.LoginFrame;
import com.earthmonitor.util.DB;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        DB.init();

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
