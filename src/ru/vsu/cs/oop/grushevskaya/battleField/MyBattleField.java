package ru.vsu.cs.oop.grushevskaya.battleField;

import ru.vsu.cs.oop.grushevskaya.battleField.cell.Cell;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Deck;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public class MyBattleField {
    int size = 10;
    private Cell[][] cells = new Cell[size][size];

    private char[] columns = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З', 'И', 'К'};
    private int[] rows = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    List<Ship> ships;

    public MyBattleField arrangeTheShips(List<Ship> ships) {
        this.ships = ships;

        for (Ship s : ships) {
            for (int i = 0; i < s.getDecks().size(); i++) {
                cells[s.getDecks().get(i).getRow()][s.getDecks().get(i).getColumn()] = new Cell((char) s.getDecks().get(i).getRow(), s.getDecks().get(i).getColumn(), true);
            }
        }
        return this;
    }

    public void createEmptyBattleField() {
        for (int i = 0; i < size; i++) {
            for (int j =0; j < size; j++) {
                cells[i][j] = new Cell(rows[i], columns[j]);
            }
        }
    }

    public MyBattleField(List<Ship> ships) {
        createEmptyBattleField();
        arrangeTheShips(ships);
    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean isThereAShip(int row, int column) {
        return cells[row][column].isThereAShip();
    }
}
