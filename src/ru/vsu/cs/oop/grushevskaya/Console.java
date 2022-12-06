package ru.vsu.cs.oop.grushevskaya;

import ru.vsu.cs.oop.grushevskaya.battleField.BattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.HitStates;
import ru.vsu.cs.oop.grushevskaya.battleField.cell.CellStates;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.EnemyDeckStates;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;
import ru.vsu.cs.oop.grushevskaya.bot.BotGenius;
import ru.vsu.cs.oop.grushevskaya.bot.Strategy;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Console {
    public static void main(String[] args) throws Exception {
        System.out.println("Добро пожаловать в морской бой");
        Scanner sc = new Scanner(System.in);

        System.out.println("Введите 0, если передаете свою игру боту");

        Player firstPlayer; // инициализация игрока 1
        if (Objects.equals(sc.nextLine(), "0")) {
            firstPlayer = new Player("Bot1", Strategy.BOT);
        } else {
            System.out.println("Игрок 1, введите свое имя");
            String player1Name = sc.nextLine();
            firstPlayer = new Player(player1Name, Strategy.PERSON);
        }

        System.out.printf("%s, ведите 0, если хотите сыграть с ботом\n", firstPlayer.getName());

        Player secondPlayer; // инициализация игрока 2
        if (Objects.equals(sc.nextLine(), "0")) {
            secondPlayer = new Player("Bot2", Strategy.BOT);
        } else {
            System.out.println("Игрок 2, введите свое имя");
            String player2Name = sc.nextLine();
            secondPlayer = new Player(player2Name, Strategy.PERSON);
        }

        arrangeHint(firstPlayer.getName()); // корабли игрока 1

        List<Ship> firstPlayerShips = GameUtils.convertStringToShips(sc.nextLine());
        firstPlayer.setBattleField(new BattleField(firstPlayerShips)); // TODO: сделать чтобы программа не падала из за неверно введенной строки кораблей

        printMyBattleField(firstPlayer);

        if (secondPlayer.getStrategy() == Strategy.PERSON) { // корабли игрока 2
            arrangeHint(secondPlayer.getName());

            List<Ship> secondPlayerShips = GameUtils.convertStringToShips(sc.nextLine());
            secondPlayer.setBattleField(new BattleField(secondPlayerShips));

            printMyBattleField(secondPlayer);
        } else {
            List<Ship> secondPlayerShips = GameUtils.convertStringToShips("а1-а1, а3-а5, г1-г1, ж1-ж1, к1-к1, в3-г3, з3-з4, б7-в7, б9-д9, и7-и9");
            secondPlayer.setBattleField(new BattleField(secondPlayerShips));
        }

        GameStates state = GameStates.FIRST_PLAYER_MOTION;

        while (state != GameStates.END) {
            if (state == GameStates.FIRST_PLAYER_MOTION) {
                state = playerMove(firstPlayer, secondPlayer, state);
            } else {
                state = playerMove(secondPlayer, firstPlayer, state);
            }
        }
    }

    public static void arrangeHint(String name) {
        System.out.printf("""
                %s, расставьте корабли. Для этого напишите координаты всех кораблей в формате ->\s
                Б1-Г1, К0-К0, Б3-Б4, Г3-Г4, К4-К7, К9-К9, А9-Б9, Г9-Г9, Ж1-Ж1, Ж3-Ж5\s
                или\s
                а1-а1, а3-а5, г1-г1, ж1-ж1, к1-к1, в3-г3, з3-з4, б7-в7, б9-д9, и7-и9\s
                (по количеству палуб и расположению на поле корабли могут идти в любом порядке,\s
                у каждого корабля есть координаты X (буквы А, Б, В, Г, Д, Е, Ж, З, И, К) и Y (0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
                """, name);
        //а1-а1, а3-а5, г1-г1, ж1-ж1, к1-к1, в3-г3, з3-з4, б7-в7, б9-д9, и7-и9
    }

    public static void printMyBattleField(Player player) {
        System.out.printf("%s, ваша расстановка: \n", player.getName());
        BattleField m1 = player.getBattleField();
        for (int i = 0; i < m1.getCells().length; i++) {
            for (int j = 0; j < m1.getCells()[0].length; j++) {
                System.out.print("|");
                if (m1.getCells()[i][j].isThereAShip()) {
                    System.out.print("*");
                } else if (m1.getCells()[i][j].getState() == CellStates.CHECKED) {
                    System.out.print("-");
                } else System.out.print(" ");
            }
            System.out.println("|");
        }
    }

    public static GameStates playerMove(Player player, Player enemy, GameStates state) {
        Coordinate coordinate;
        printEnemyBattleField(enemy, player.getName());
        if (player.getStrategy() == Strategy.BOT) {
            coordinate = BotGenius.botMove(enemy); //
        } else {
            Scanner sc = new Scanner(System.in);
            moveHint(player.getName());
            coordinate = GameUtils.getCoordinate(sc.nextLine());
        }

        HitStates resultOfMove = enemy.move(coordinate);
        // TODO: убрать возможность ударять в одну координату дважды и ударять в несуществующие точки чтобы прога не падала
        //  + сделать так чтобы она просила ввести другую координату снова
        if (resultOfMove != HitStates.MISS) {
            System.out.printf("%s, вы попали!\n", player.getName());
        } else {
            System.out.printf("%s, вы не попали!\n", player.getName());
            if (state == GameStates.SECOND_PLAYER_MOTION) {
                state = GameStates.FIRST_PLAYER_MOTION;
            } else state = GameStates.SECOND_PLAYER_MOTION; // TODO: добавить возможность сдаться
        }

        if (enemy.getBattleField().isEnemyLose()) {
            printWinner(player.getName());
            state = GameStates.END;
        } // проверка на конец игры
        return state;
    }

    public static void printEnemyBattleField(Player enemy, String playerName) {
        System.out.printf("%s, вражеская известная расстановка: \n", playerName);
        BattleField m1 = enemy.getBattleField();
        for (int i = 0; i < m1.getCells().length; i++) {
            for (int j = 0; j < m1.getCells()[0].length; j++) {
                System.out.print("|");
                if (m1.getCells()[i][j].isThereAShip()) {
                    if (m1.getShip(i, j).getEnemyState() == EnemyDeckStates.VISIBLE) {
                        System.out.print("*");
                    } else System.out.print(" ");
                } else if (m1.getCells()[i][j].getState() == CellStates.CHECKED) {
                    System.out.print("-");
                } else System.out.print(" ");
            }
            System.out.println("|");
        }
    }

    public static void moveHint(String name) {
        System.out.printf("%s, передайте координаты точки, куда хотите нанести удар (например, 'A5')\n", name);
    }

    public static void printWinner (String name) {
        System.out.printf("Поздравляю с победой, %s. Игра окончена!\n", name);
    }
}