package com.earthmonitor.app.ui.components;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    private Color start = new Color(12, 97, 33);
    private Color end = new Color(38, 198, 93);

    public GradientPanel() { setOpaque(false); }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int w = getWidth(), h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, start, w, h, end);
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, w, h, 28, 28);
        g2.dispose();
        super.paintComponent(g);
    }
}
