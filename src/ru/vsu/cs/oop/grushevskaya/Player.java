package ru.vsu.cs.oop.grushevskaya;

import ru.vsu.cs.oop.grushevskaya.app.introduction.PlayerInfo;
import ru.vsu.cs.oop.grushevskaya.battleField.BattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.HitStates;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;
import ru.vsu.cs.oop.grushevskaya.bot.Strategy;

import java.util.ArrayList;

public class Player {
    private String name;
    private BattleField battleField;

    private Strategy strategy;

    public Player(String name, Strategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    public Player(PlayerInfo info) {
        this(info.getName(), info.isBot() ? Strategy.BOT : Strategy.PERSON);
    }

    public void setBattleField(BattleField battleField) {
        this.battleField = battleField;
    }

    public BattleField getBattleField() {
        return battleField;
    }

    public String getName() {
        return name;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void createBattleField (ArrayList<Ship> ships) {
        battleField.arrangeTheShips(ships);
    }

    public HitStates move(Coordinate coordinate) {
        return battleField.hitBattleField(coordinate);
    }
}