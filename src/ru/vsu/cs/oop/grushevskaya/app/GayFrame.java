package ru.vsu.cs.oop.grushevskaya.app;

import ru.vsu.cs.oop.grushevskaya.Player;

import javax.swing.*;
import java.awt.*;

public class GayFrame extends JFrame {
    private final Introduction introduction = new Introduction(this);
    private final ArrangePanel arrangePanel = new ArrangePanel();
    private Player firstPlayer;
    private Player secondPlayer;

    public GayFrame() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Морской бой");

        add(introduction);
        pack();

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void melonPult(PlayersInfo playersInfo) {
        firstPlayer = new Player(playersInfo.getFirst());
        secondPlayer = new Player(playersInfo.getSecond());
        System.out.println(firstPlayer.getName() + " " + firstPlayer.getStrategy());
        System.out.println(secondPlayer.getName() + " " + secondPlayer.getStrategy());
        remove(introduction);
        add(arrangePanel);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new GayFrame();
    }
}
