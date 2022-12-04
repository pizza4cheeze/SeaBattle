package ru.vsu.cs.oop.grushevskaya.app;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ArrangePanel extends JPanel {

    public ArrangePanel() {
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics2D g2d = bi.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.BLACK);
        g2d.drawRect(2, 2, getWidth()-5, getHeight()-5);

        g.drawImage(bi, 0, 0, null);
        g2d.dispose();

    }
}
