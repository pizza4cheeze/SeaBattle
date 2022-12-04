package ru.vsu.cs.oop.grushevskaya.app;

import javax.swing.*;

public class PlayerInfoForm extends JPanel {
    private final JLabel label;
    private final JTextField nameField = new JTextField();
    private final JCheckBox bot = new JCheckBox("Бот");

    public PlayerInfoForm(String title) {
        this.label = new JLabel("Имя игрока" + title);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(label);
        add(nameField);
        add(bot);
    }

    public PlayerInfo getInfo() {
        return new PlayerInfo(nameField.getText(), bot.isSelected());
    }

}
