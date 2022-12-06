package ru.vsu.cs.oop.grushevskaya.app;

import ru.vsu.cs.oop.grushevskaya.Player;
import ru.vsu.cs.oop.grushevskaya.app.introduction.Introduction;
import ru.vsu.cs.oop.grushevskaya.app.introduction.PlayersInfo;
import ru.vsu.cs.oop.grushevskaya.battleField.BattleField;

import javax.swing.*;
import java.awt.*;

public class GayFrame extends JFrame {
    private final Introduction introduction = new Introduction(this);
    private ArrangePanelDemo arrangePanelDemo = new ArrangePanelDemo(this);
    private Player firstPlayer;
    private Player secondPlayer;

    public GayFrame() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setTitle("Морской бой");

        add(introduction);
        pack();

        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

    public void getPlayersIntroduction(PlayersInfo playersInfo) {
        firstPlayer = new Player(playersInfo.getFirst());
        secondPlayer = new Player(playersInfo.getSecond()); // получаем информацию об игроках и помещаем ее в поля игроков

        //System.out.println(firstPlayer.getName() + " " + firstPlayer.getStrategy());
        //System.out.println(secondPlayer.getName() + " " + secondPlayer.getStrategy());

        remove(introduction);
        add(arrangePanelDemo);

        pack();
        setLocationRelativeTo(null);
    }

    public void getFirstBattleField(BattleField battleField) {
        firstPlayer.setBattleField(battleField);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new GayFrame();
    }
}
