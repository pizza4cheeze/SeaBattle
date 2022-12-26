package ru.vsu.cs.oop.grushevskaya.app;

import ru.vsu.cs.oop.grushevskaya.GameUtils;
import ru.vsu.cs.oop.grushevskaya.Player;
import ru.vsu.cs.oop.grushevskaya.app.arrange.ArrangePanelDemo;
import ru.vsu.cs.oop.grushevskaya.app.introduction.Introduction;
import ru.vsu.cs.oop.grushevskaya.app.introduction.PlayersInfo;
import ru.vsu.cs.oop.grushevskaya.battleField.BattleField;
import ru.vsu.cs.oop.grushevskaya.bot.Strategy;

import javax.swing.*;
import java.awt.*;

public class GayFrame extends JFrame {
    private final Introduction introduction = new Introduction(this);
    private ArrangePanelDemo arrangePanelDemo;

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

        arrangePanelDemo = new ArrangePanelDemo(this, firstPlayer.getName());

        remove(introduction);
        add(arrangePanelDemo);

        pack();
        setLocationRelativeTo(null);
    }

    public void getBattleField(BattleField battleField) throws Exception {
        if (firstPlayer.getBattleField() == null) {
            firstPlayer.setBattleField(battleField);
            remove(arrangePanelDemo);
            if (secondPlayer.getStrategy() == Strategy.BOT) {
                secondPlayer.setBattleField(new BattleField(GameUtils.convertStringToShips("Б1-Г1, К0-К0, Б3-Б4, Г3-Г4, К4-К7, К9-К9, А9-Б9, Г9-Г9, Ж1-Ж1, Ж3-Ж5")));
                remove(arrangePanelDemo);
                add(new GamePanel(firstPlayer, secondPlayer));
            } else {
                arrangePanelDemo = new ArrangePanelDemo(this, secondPlayer.getName());
                add(arrangePanelDemo);
            }
        } else if (secondPlayer.getBattleField() == null) {
            secondPlayer.setBattleField(battleField);
            remove(arrangePanelDemo);
            add(new GamePanel(firstPlayer, secondPlayer));
        }
//
//        secondPlayer.setBattleField(new BattleField(GameUtils.convertStringToShips("Б1-Г1, К0-К0, Б3-Б4, Г3-Г4, К4-К7, К9-К9, А9-Б9, Г9-Г9, Ж1-Ж1, Ж3-Ж5")));
//
//
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new GayFrame();
    }
}
