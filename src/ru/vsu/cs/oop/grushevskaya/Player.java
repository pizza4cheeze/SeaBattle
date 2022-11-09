package ru.vsu.cs.oop.grushevskaya;

import ru.vsu.cs.oop.grushevskaya.battleField.EnemyBattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.MyBattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;

import java.util.ArrayList;

public class Player {
    private String name;
    private MyBattleField myBattleField;
    private EnemyBattleField enemyBattleField;

    public void setMyBattleField(MyBattleField myBattleField) {
        this.myBattleField = myBattleField;
    }

    public String getName() {
        return name;
    }

    public Player(String name) {
        this.name = name;
    }

    public void createBattleField (ArrayList<Ship> ships) {
        myBattleField.arrangeTheShips(ships);
    }

    public boolean move(char column, int row) {
        return true; // тут должно быть другое
    }

    public MyBattleField getMyBattleField() {
        return myBattleField;
    }

    public EnemyBattleField getEnemyBattleField() {
        return enemyBattleField;
    }
}
