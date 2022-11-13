package ru.vsu.cs.oop.grushevskaya.battleField;

import ru.vsu.cs.oop.grushevskaya.Coordinate;
import ru.vsu.cs.oop.grushevskaya.battleField.cell.Cell;
import ru.vsu.cs.oop.grushevskaya.battleField.cell.CellStates;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Deck;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.DeckStates;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;

import java.util.List;

public class EnemyBattleField extends AbstractBattleField {
    public boolean isEnemyLose() {
        int hurtDecksCounter = 0;
        for (Ship ship : ships) {
            hurtDecksCounter += ship.getHurtCounter();
        }
        return hurtDecksCounter == 20;
    }

    public EnemyBattleField(List<Ship> ships) {
        this.ships = ships;
    }

    public boolean hitBattleField(Coordinate coordinate) {
        cells[coordinate.getRow()][coordinate.getColumn()].setState(CellStates.CHECKED);
        for (Ship ship : ships) {
            for (Deck deck : ship.getDecks()) {
                if (deck.getRow() == coordinate.getRow() && deck.getColumn() == coordinate.getColumn()) {
                    ship.hitTheShip(coordinate);
                    this.getCells()[coordinate.getRow()][coordinate.getColumn()].setShipHere(true);
                    return true;
                }
            }
        }
        return false;
    }
}
