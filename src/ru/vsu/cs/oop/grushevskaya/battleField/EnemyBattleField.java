package ru.vsu.cs.oop.grushevskaya.battleField;

import ru.vsu.cs.oop.grushevskaya.battleField.cell.Cell;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;

import java.util.List;

public class EnemyBattleField {
    private int size = 10;
    private Cell[][] cells = new Cell[size][size];

    private List<Ship> ships;
}
