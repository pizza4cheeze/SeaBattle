package ru.vsu.cs.oop.grushevskaya;

import ru.vsu.cs.oop.grushevskaya.battleField.EnemyBattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.MyBattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Deck;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.DeckStates;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public class GameUtils {
    public static List<Ship> convertStringToShips(String data) throws Exception {
        List<Ship> ships = new ArrayList<>();

        String[] splitShips = data.split(", | |,");

        if (splitShips.length != 10) {
            throw new IllegalStateException("Кораблей должно быть 10. Попробуйте расставить корабли снова");
        }

        for (String splitShip : splitShips) {
            String[] oneSplitShip = splitShip.split("-|- | -");

            Coordinate p1 = getCoordinate(oneSplitShip[0]);
            Coordinate p2 = getCoordinate(oneSplitShip[1]);

            int widthOfTheShip = Math.abs(p1.getColumn() - p2.getColumn());
            int heightOfTheShip = Math.abs(p1.getRow() - p2.getRow());

            if (widthOfTheShip != 0 && heightOfTheShip != 0) {
                throw new IllegalStateException("Корабль " + oneSplitShip[0] + "-" + oneSplitShip[1] + " введен некорректно. " +
                        "Корабли не должны занимать несколько строк и столбцов одновременно. " +
                        "Попробуйте расставить корабли еще раз");
            }

            List<Deck> decks = new ArrayList<>();

            if (widthOfTheShip > 0) {
                if (p1.getColumn() > p2.getColumn()) {
                    Coordinate temp = p1;
                    p1 = p2;
                    p2 = temp;
                }
                for (int j = p1.getColumn(); j <= p2.getColumn(); j++) {
                    decks.add(new Deck(p1.getRow(), j));
                }
            } else if (heightOfTheShip > 0) {
                if (p1.getRow() > p2.getRow()) {
                    Coordinate temp = p1;
                    p1 = p2;
                    p2 = temp;
                }
                for (int i = p1.getRow(); i <= p2.getRow(); i++) {
                    decks.add(new Deck(i, p1.getColumn()));
                }
            } else {
                decks.add(new Deck(p1.getRow(), p1.getColumn()));
            }

            ships.add(new Ship(decks));
        }

        checkShips(ships);
        return ships;
    }

    private static void checkShips(List<Ship> ships) {
        int[] decksCheck = {0, 0, 0, 0};
        for (Ship ship : ships) {
            decksCheck[ship.getDecks().size() - 1]++;
        }
        if (decksCheck[0] != 4 || decksCheck[1] != 3 || decksCheck[2] != 2 || decksCheck[3] != 1) {
            throw new IllegalStateException("Вы добавили неверное количество кораблей. Расставьте на поле " +
                    "1 - 4хпалубный, 2 - 3хпалубных, 3 - 2хпалубных и 4 - 1палубных кораблей.");
        }
        for (int mainShipIdx = 0; mainShipIdx < ships.size(); mainShipIdx++) {
            for (int sideShipIdx = mainShipIdx + 1; sideShipIdx < ships.size(); sideShipIdx++) {
                for (Deck decksMainShip : ships.get(mainShipIdx).getDecks()) {
                    for (Deck decksSideShip : ships.get(sideShipIdx).getDecks()) {
                        if (Math.abs(decksMainShip.getRow() - decksSideShip.getRow()) <= 1 &&
                                Math.abs(decksMainShip.getColumn() - decksSideShip.getColumn()) <= 1) {
                            throw new IllegalStateException("Ваши корабли находят друг на друга. " +
                                    "Вокруг корабля в радиусе 1 клетки в любом направлении, в том числе и по диагонали, не должно стоять других кораблей. " +
                                    "Попробуйте расставить корабли снова");
                        }
                    }
                }
            }
        }
    }

    public static Coordinate getCoordinate(String oneSplitShip) {
        int column;
        int row;

        switch (oneSplitShip.charAt(0)) {
            case 'A', 'a', 'А', 'а' -> column = 0;
            case 'Б', 'б' -> column = 1;
            case 'В', 'в', 'B', 'b' -> column = 2;
            case 'Г', 'г' -> column = 3;
            case 'Д', 'д' -> column = 4;
            case 'Е', 'е', 'E', 'e' -> column = 5;
            case 'Ж', 'ж' -> column = 6;
            case 'З', 'з' -> column = 7;
            case 'И', 'и' -> column = 8;
            case 'К', 'к', 'K', 'k' -> column = 9;
            default -> throw new IllegalStateException("Unexpected value: " + oneSplitShip.charAt(0));
        }
        row = Character.getNumericValue(oneSplitShip.charAt(1));
        
        return new Coordinate(row, column);
    }

    public static boolean isGameEnded(EnemyBattleField battleField) {
        int decksCounter = 0;
        for (Ship ship : battleField.getShips()) {
            for (Deck deck : ship.getDecks()) {
                if (deck.getState() == DeckStates.HURT) {
                    decksCounter++;
                }
            }
        }
        return decksCounter == 20; // загадочная константа
    }
}
