package ru.vsu.cs.oop.grushevskaya;

import ru.vsu.cs.oop.grushevskaya.battleField.BattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.HitStates;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;

import java.util.ArrayList;

public class Player {
    private String name;
    private BattleField battleField;


    private Strategy strategy;

    public Player(String name, Strategy strategy) {
        this.name = name;
        this.strategy = strategy;
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
