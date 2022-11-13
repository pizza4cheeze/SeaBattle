package ru.vsu.cs.oop.grushevskaya.battleField;

import ru.vsu.cs.oop.grushevskaya.battleField.cell.Cell;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;

import java.util.List;

public class AbstractBattleField {
    protected int size = 10;
    protected Cell[][] cells = new Cell[size][size];
    protected List<Ship> ships;

    public AbstractBattleField() {
        createEmptyBattleField();
    }

    public void createEmptyBattleField() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }
}
