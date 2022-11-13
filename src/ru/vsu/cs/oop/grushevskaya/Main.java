package ru.vsu.cs.oop.grushevskaya;

import ru.vsu.cs.oop.grushevskaya.battleField.EnemyBattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.MyBattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        // представление
        System.out.print("""
                Добро пожаловать в морской бой
                Игрок 1, введите свое имя
                """);
        String player1Name = sc.nextLine();
        Player firstPlayer = new Player(player1Name);

        System.out.print("""
                Игрок 2, введите свое имя
                """);
        String player2Name = sc.nextLine();
        Player secondPlayer = new Player(player2Name);

        GameStates state = GameStates.COLLOCATION;

        arrangeHint(firstPlayer.getName()); // начало работы с игроком 1

        List<Ship> firstPlayerShips = GameUtils.convertStringToShips(sc.nextLine());
        firstPlayer.setMyBattleField(new MyBattleField(firstPlayerShips));

        printMyBattleField(firstPlayer); // конец работы с игроком 1

        arrangeHint(secondPlayer.getName()); // начало работы с игроком 2

        List<Ship> secondPlayerShips = GameUtils.convertStringToShips(sc.nextLine());
        secondPlayer.setMyBattleField(new MyBattleField(secondPlayerShips));

        printMyBattleField(secondPlayer); // конец работы с игроком 2

        firstPlayer.setEnemyBattleField(secondPlayerShips);
        secondPlayer.setEnemyBattleField(firstPlayerShips); // мега важные строчки!

        state = GameStates.FIRST_PLAYER_MOTION;

        while (state != GameStates.END) {
            if (state == GameStates.FIRST_PLAYER_MOTION) {
                state = playerMove(firstPlayer, state);
            } else {
                state = playerMove(secondPlayer, state);
            }
        }
    }

    public static void arrangeHint(String name) {
        System.out.printf("""
                %s, расставьте корабли. Для этого напишите координаты всех кораблей в формате ->\s
                Б1-Г1, К0-К0, Б3-Б4, Г3-Г4, К4-К7, К9-К9, А9-Б9, Г9-Г9, Ж1-Ж1, Ж3-Ж5\s
                (по количеству палуб и расположению на поле корабли могут идти в любом порядке,\s
                у каждого корабля есть координаты X (буквы А, Б, В, Г, Д, Е, Ж, З, И, К) и Y (0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
                """, name);
    }

    public static void printMyBattleField(Player player) {
        System.out.printf("%s, ваша расстановка: \n", player.getName());
        MyBattleField m1 = player.getMyBattleField();
        for (int i = 0; i < m1.getCells().length; i++) {
            for (int j = 0; j < m1.getCells()[0].length; j++) {
                System.out.print("|");
                if (m1.getCells()[i][j].isThereAShip()) {
                    System.out.print("*");
                } else System.out.print("-");
            }
            System.out.println("|");
        }
    }

    public static String showEnemyBattleField(String name) {
        Scanner sc = new Scanner(System.in);
        System.out.printf("""
                %s, введите '1' если хотите увидеть ситуацию на вражеском поле
                            '2' если хотите увидеть свою расстановку
                """, name);
        return switch (sc.nextLine()) {
            case "1" -> "enemy";
            case "2" -> "my";
            default -> sc.nextLine();
        };
    }

    public static void printEnemyBattleField(Player player) {
        System.out.printf("%s, вражеская известная расстановка: \n", player.getName());
        EnemyBattleField m1 = player.getEnemyBattleField();
        for (int i = 0; i < m1.getCells().length; i++) {
            for (int j = 0; j < m1.getCells()[0].length; j++) {
                System.out.print("|");
                if (m1.getCells()[i][j].isThereAShip()) {
                    System.out.print("*");
                } else System.out.print("-");
            }
            System.out.println("|");
        }
    }

    public static void moveHint(String name) {
        System.out.printf("%s, передайте координаты точки, куда хотите нанести удар (например, 'A5')\n", name);
    }

    public static GameStates playerMove(Player player, GameStates state) {
        Scanner sc = new Scanner(System.in);
        if (Objects.equals(showEnemyBattleField(player.getName()), "enemy")) {
            printEnemyBattleField(player);
        } else if (Objects.equals(showEnemyBattleField(player.getName()), "my")) {
            printMyBattleField(player);
        }

        moveHint(player.getName());
        Coordinate coordinate = GameUtils.getCoordinate(sc.nextLine()); // TODO: поведение

        boolean resultOfMove = player.move(coordinate); // проверка хода
        if (!resultOfMove) state = GameStates.SECOND_PLAYER_MOTION; // конец хода

        if (player.getEnemyBattleField().isEnemyLose()) {
            printWinner(player.getName());
            state = GameStates.END;
        } // проверка на конец игры
        return state;
    }

    public static void printWinner (String name) {
        System.out.printf("Поздравляю с победой, %s. Игра окончена!\n", name);
    }
}