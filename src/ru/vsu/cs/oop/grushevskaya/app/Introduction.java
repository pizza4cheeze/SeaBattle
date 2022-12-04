package ru.vsu.cs.oop.grushevskaya.app;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Introduction extends JPanel {
    private PlayersInfo playersInfo;
    private final GayFrame frame;

    private PlayerInfoForm form1 = new PlayerInfoForm("1");
    private PlayerInfoForm form2 = new PlayerInfoForm("2");

    public Introduction(GayFrame frame) {
        this.frame = frame;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel formsPanel = new JPanel();
        JLabel label = new JLabel("Добро пожаловать в морской бой! Пожалуйста, представьтесь");
        label.setAlignmentX(CENTER_ALIGNMENT);
        add(label);
        formsPanel.add(form1);
        formsPanel.add(form2);
        formsPanel.setLayout(new FlowLayout());
        add(formsPanel);

        JButton button = new JButton();
        button.setText("Далее");

        button.setAlignmentX(CENTER_ALIGNMENT);

        button.addActionListener(e -> {
            frame.melonPult(new PlayersInfo(form1.getInfo(), form2.getInfo()));
        });

        add(button);
    }

    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics2D g2d = bi.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.BLACK);

        g.drawImage(bi, 0, 0, null);
        g2d.dispose();
    }
}
