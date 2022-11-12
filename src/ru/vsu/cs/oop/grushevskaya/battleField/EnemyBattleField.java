package ru.vsu.cs.oop.grushevskaya.battleField;

import ru.vsu.cs.oop.grushevskaya.Coordinate;
import ru.vsu.cs.oop.grushevskaya.battleField.cell.Cell;
import ru.vsu.cs.oop.grushevskaya.battleField.cell.CellStates;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Deck;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;

import java.util.List;

public class EnemyBattleField {
    private int size = 10;
    private Cell[][] cells = new Cell[size][size];

    private List<Ship> ships;

    public List<Ship> getShips() {
        return ships;
    }

    public EnemyBattleField(List<Ship> ships) {
        this.ships = ships;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean hitBattleField(Coordinate coordinate) {
        cells[coordinate.getRow()][coordinate.getColumn()].setState(CellStates.CHECKED);
        for (Ship ship : ships) {
            for (Deck deck : ship.getDecks()) {
                if (deck.getRow() == coordinate.getRow() && deck.getColumn() == coordinate.getColumn()) {
                    ship.hitTheShip(coordinate);
                    return true;
                }
            }
        }
        return false;
    }
}
