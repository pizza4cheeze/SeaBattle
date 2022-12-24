package ru.vsu.cs.oop.grushevskaya.app.introduction;

import javax.swing.*;
import java.awt.*;

public class PlayerInfoForm extends JPanel {
    private final JLabel label;
    private final JTextField nameField = new JTextField();
    private final JCheckBox bot = new JCheckBox("Бот");

    public PlayerInfoForm(String title) {
        this.label = new JLabel("Имя игрока " + title);
        setLayout(new GridLayout(3, 1, 0, 8));

        add(label);
        add(nameField);
        add(bot);
    }

    public PlayerInfo getInfo() {
        return new PlayerInfo(nameField.getText(), bot.isSelected());
    }

}
