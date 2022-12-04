package ru.vsu.cs.oop.grushevskaya.bot;

import ru.vsu.cs.oop.grushevskaya.Coordinate;
import ru.vsu.cs.oop.grushevskaya.Player;
import ru.vsu.cs.oop.grushevskaya.battleField.cell.CellStates;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Deck;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.DeckStates;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.ShipStates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotGenius { // вообще я так и играю
    public static Coordinate botMove(Player enemy) {
        for (Ship ship : enemy.getBattleField().getShips()) { // TODO: хочется убрать громоздкое нахождение cells, ships и decks, но я забыла хоткей....
            if (ship.getState() == ShipStates.HURT) {
                if (ship.getDecks().size() - ship.getAliveDecksCounter() == 1) {
                    for (Deck deck : ship.getDecks()) {
                        if (deck.getState() == DeckStates.HURT) {
                            return generateDiapason(new Coordinate(deck.getRow(), deck.getColumn()));
                            // если ранена одна клетка и направление неизвестно, пробуем угадать направление и по x, и по y
                        }
                    }
                }
                else if (ship.getDecks().size() - ship.getAliveDecksCounter() > 1) {
                    List<Deck> listDecks = new ArrayList<>();
                    for (Deck deck : ship.getDecks()) {
                        if (deck.getState() == DeckStates.HURT) {
                            listDecks.add(deck);
                        }
                    }
                    Coordinate first = new Coordinate(listDecks.get(0).getRow(), listDecks.get(0).getColumn());
                    Coordinate second = new Coordinate(listDecks.get(listDecks.size()-1).getRow(), listDecks.get(listDecks.size()-1).getColumn());
                    return generateDiapason(first, second);
                }
            }
        }
        Random rnd = new Random();
        int countUnchecked = 0;
        int countRandom = 0;
        for (int i = 0; i < enemy.getBattleField().getCells().length; i++) {
            for (int j = 0; j < enemy.getBattleField().getCells()[0].length; j++) {
                if (enemy.getBattleField().getCells()[i][j].getState() == CellStates.NULL) {
                    countUnchecked++;
                }
            }
        }
        int random = rnd.nextInt(countUnchecked);
        for (int i = 0; i < enemy.getBattleField().getCells().length; i++) {
            for (int j = 0; j < enemy.getBattleField().getCells()[0].length; j++) {
                if (enemy.getBattleField().getCells()[i][j].getState() == CellStates.NULL) {
                    countRandom++;
                    if (countRandom == random) {
                        return new Coordinate(i, j);
                    }
                }
            }
        }
        return new Coordinate(0, 0);
    }

    private static Coordinate generateDiapason(Coordinate coordinate) { // угадываем направление из 4х
        int row = coordinate.getRow();
        int column = coordinate.getColumn();

        int size = 10; // TODO: костыль хихиххихих

        List<Coordinate> coordinates = new ArrayList<>();

        if (row + 1 < size) {
            coordinates.add(new Coordinate(row + 1, column));
        }
        if (column - 1 >= 0) {
            coordinates.add(new Coordinate(row, column - 1));
        }
        if (row - 1 >= 0) {
            coordinates.add(new Coordinate(row - 1, column));
        }
        if (column + 1 < size) {
            coordinates.add(new Coordinate(row, column + 1));
        }
        Random rnd = new Random();
        int random = rnd.nextInt(coordinates.size());
        return coordinates.get(random);
    }

    private  static Coordinate generateDiapason(Coordinate firstCoordinate, Coordinate secondCoordinate) { // угадываем направление из 2х
        int row1 = firstCoordinate.getRow();
        int col1 = firstCoordinate.getColumn();

        int row2 = secondCoordinate.getRow();
        int col2 = secondCoordinate.getColumn();

        int size = 10;

        Random rnd = new Random();

        List<Coordinate> coordinates = new ArrayList<>();

        if (firstCoordinate.getColumn() == secondCoordinate.getColumn()) {
            if (row1 > row2) {
                int temp = row1;
                row1 = row2;
                row2 = temp;
            }
            if (row1 - 1 >= 0) {
                coordinates.add(new Coordinate(row1 - 1, col1));
            }
            if (row2 + 1 < size) {
                coordinates.add(new Coordinate(row2 + 1, col1));
            }
            int random = rnd.nextInt(coordinates.size());
            return coordinates.get(random);
        }
        else {
            if (col1 > col2) {
                int temp = col1;
                col1 = col2;
                col2 = temp;
            }
            if (col1 - 1 >= 0) {
                coordinates.add(new Coordinate(row1, col1 - 1));
            }
            if (col2 + 1 < size) {
                coordinates.add(new Coordinate(row1, col2 + 1));
            }
            int random = rnd.nextInt(coordinates.size());
            return coordinates.get(random);
        }
    }
}
