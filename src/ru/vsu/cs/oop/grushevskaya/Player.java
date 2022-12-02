package ru.vsu.cs.oop.grushevskaya;

import ru.vsu.cs.oop.grushevskaya.battleField.BattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.HitStates;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;

import java.util.ArrayList;

public class Player {
    private String name;
    private BattleField battleField;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBattleField(BattleField battleField) {
        this.battleField = battleField;
    }

    public BattleField getBattleField() {
        return battleField;
    }


    public void createBattleField (ArrayList<Ship> ships) {
        battleField.arrangeTheShips(ships);
    }

    public HitStates move(Coordinate coordinate) {
        return battleField.hitBattleField(coordinate);
    }
}
