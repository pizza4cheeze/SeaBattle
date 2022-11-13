package ru.vsu.cs.oop.grushevskaya;

import ru.vsu.cs.oop.grushevskaya.battleField.EnemyBattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.MyBattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private MyBattleField myBattleField;
    private EnemyBattleField enemyBattleField;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMyBattleField(MyBattleField myBattleField) {
        this.myBattleField = myBattleField;
    }

    public MyBattleField getMyBattleField() {
        return myBattleField;
    }

    public void setEnemyBattleField(List<Ship> ships) {
        this.enemyBattleField = new EnemyBattleField(ships);
    }

    public EnemyBattleField getEnemyBattleField() {
        return enemyBattleField;
    }

    public void createBattleField (ArrayList<Ship> ships) {
        myBattleField.arrangeTheShips(ships);
    }

    public boolean move(Coordinate coordinate) {
        return enemyBattleField.hitBattleField(coordinate);
    }
}
