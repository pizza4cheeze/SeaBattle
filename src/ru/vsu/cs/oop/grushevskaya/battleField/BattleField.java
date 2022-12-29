package ru.vsu.cs.oop.grushevskaya.battleField;

import com.lezko.simplejson.ArrObj;
import com.lezko.simplejson.Obj;
import ru.vsu.cs.oop.grushevskaya.Coordinate;
import ru.vsu.cs.oop.grushevskaya.battleField.cell.Cell;
import ru.vsu.cs.oop.grushevskaya.battleField.cell.CellStates;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Deck;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.EnemyDeckStates;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.ShipStates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BattleField {
    protected int size = 10;
    protected Cell[][] cells = new Cell[size][size];
    protected List<Ship> ships;

    public BattleField() {
        createEmptyBattleField();
    }

    public BattleField(List<Ship> ships) {
        createEmptyBattleField();
        arrangeTheShips(ships);
    }

    public BattleField arrangeTheShips(List<Ship> ships) {
        this.ships = ships;

        for (Ship s : ships) {
            for (int i = 0; i < s.getDecks().size(); i++) {
                int row = s.getDecks().get(i).getRow();
                int column = s.getDecks().get(i).getColumn();
                cells[row][column] = new Cell(row, column, true);
                System.out.println(1);
            }
        }
        return this;
    }

    public void createEmptyBattleField() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    @Override
    public String toString() {
        Obj arr = new ArrObj();
        for (int row = 0; row < size; row++) {
            Obj rowObj = new ArrObj();
            for (int col = 0; col < size; col++) {
                if (isThereAShip(row, col)) {
                    rowObj.append("ship");
                } else rowObj.append("null");
            }
            arr.append(rowObj);
        }
        return arr.toString();
    } //todo: ass ass ass ass ass
    // [[а0, б0]]
    // [[б1, б2]]

    public String toShipsString() {
        Obj arrObj = new ArrObj();
        for (Ship ship : ships) {
            arrObj.append(ship.toString());
        }
        return arrObj.toString();
    }

    public static BattleField fromShipString(String shipsString) {
        Obj obj = Obj.fromString(shipsString);
        List<Ship> ships = new ArrayList<>();
        for (Obj shipObj : obj.toList()) {
            ships.add(Ship.fromString(shipObj.toString()));
        }
        return new BattleField(ships);
    }

    public boolean isThereAShip(int row, int column) {
        return cells[row][column].isThereAShip();
    }

    public HitStates hitBattleField(Coordinate coordinate) {
        cells[coordinate.getRow()][coordinate.getColumn()].setState(CellStates.CHECKED);
        for (Ship ship : ships) {
            for (Deck deck : ship.getDecks()) {
                if (deck.getRow() == coordinate.getRow() && deck.getColumn() == coordinate.getColumn()) {
                    ShipStates shipState = ship.hitTheShip(coordinate);
                    this.getCells()[coordinate.getRow()][coordinate.getColumn()].setShipHere();
                    if (shipState == ShipStates.KILLED) {
                        killTheShip(ship);
                        return HitStates.KILLED;
                    }
                    return HitStates.HURT;
                }
            }
        }
        return HitStates.MISS;
    }

    private void killTheShip(Ship ship) {
        for (Deck deck : ship.getDecks()) {
            int row = deck.getRow();
            int column = deck.getColumn();
            makeChecked(row, column);
        }
    }

    private void makeChecked(int row, int column) {
        if (row + 1 < size && column + 1 < size) {
            cells[row + 1][column + 1].setState(CellStates.CHECKED);
        }
        if (row + 1 < size) {
            cells[row + 1][column].setState(CellStates.CHECKED);
        }
        if (row + 1 < size && column - 1 >= 0) {
            cells[row + 1][column - 1].setState(CellStates.CHECKED);
        }
        if (column - 1 >= 0) {
            cells[row][column - 1].setState(CellStates.CHECKED);
        }
        if (row - 1 >= 0 && column - 1 >= 0) {
            cells[row - 1][column - 1].setState(CellStates.CHECKED);
        }
        if (row - 1 >= 0) {
            cells[row - 1][column].setState(CellStates.CHECKED);
        }
        if (row - 1 >= 0 && column + 1 < size) {
            cells[row - 1][column + 1].setState(CellStates.CHECKED);
        }
        if (column + 1 < size) {
            cells[row][column + 1].setState(CellStates.CHECKED);
        }
    }


    public Cell[][] getCells() {
        return cells;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public boolean isEnemyLose() {
        for (Ship s : ships) {
            if (s.getState() != ShipStates.KILLED) {
                return false;
            }
        }
        return true;
    }

    public Deck getShip(int row, int column) {
        for (Ship ship : ships) {
            for (Deck deck : ship.getDecks()) {
                if (deck.getRow() == row && deck.getColumn() == column) {
                    return deck;
                }
            }
        }
        return null;
    }
}
