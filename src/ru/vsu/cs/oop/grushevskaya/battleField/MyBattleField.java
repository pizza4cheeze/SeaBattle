package ru.vsu.cs.oop.grushevskaya.battleField;

import ru.vsu.cs.oop.grushevskaya.battleField.cell.Cell;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Deck;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public class MyBattleField extends AbstractBattleField {
    public MyBattleField arrangeTheShips(List<Ship> ships) {
        this.ships = ships;

        for (Ship s : ships) {
            for (int i = 0; i < s.getDecks().size(); i++) {
                cells[s.getDecks().get(i).getRow()][s.getDecks().get(i).getColumn()] = new Cell((char) s.getDecks().get(i).getRow(), s.getDecks().get(i).getColumn(), true);
            }
        }
        return this;
    }

    public MyBattleField(List<Ship> ships) {
        arrangeTheShips(ships);
    }

    public boolean isThereAShip(int row, int column) {
        return cells[row][column].isThereAShip();
    }
}
