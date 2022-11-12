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

        boolean resultOfMove = true;
        Coordinate coordinate;

        while (state != GameStates.END) {
            if (state == GameStates.FIRST_PLAYER_MOTION) {
                if (Objects.equals(showEnemyBattleField(firstPlayer.getName(), sc), "enemy")) {
                    printEnemyBattleField(firstPlayer);
                } else if (Objects.equals(showEnemyBattleField(firstPlayer.getName(), sc), "my")) {
                    printMyBattleField(firstPlayer);
                }

                moveHint(firstPlayer.getName());
                coordinate = GameUtils.getCoordinate(sc.nextLine());

                resultOfMove = firstPlayer.move(coordinate); // проверка хода
                if (!resultOfMove) state = GameStates.SECOND_PLAYER_MOTION; // конец хода

                if (GameUtils.isGameEnded(firstPlayer.getEnemyBattleField())) {
                    printWinner(firstPlayer.getName());
                    state = GameStates.END;
                } // проверка на конец игры
            } else {
                if (Objects.equals(showEnemyBattleField(secondPlayer.getName(), sc), "enemy")) {
                    printEnemyBattleField(secondPlayer);
                } else if (Objects.equals(showEnemyBattleField(secondPlayer.getName(), sc), "my")) {
                    printMyBattleField(secondPlayer);
                }

                moveHint(secondPlayer.getName());
                coordinate = GameUtils.getCoordinate(sc.nextLine());

                resultOfMove = secondPlayer.move(coordinate); // проверка хода
                if (!resultOfMove) state = GameStates.FIRST_PLAYER_MOTION; // конец хода

                if (GameUtils.isGameEnded(secondPlayer.getEnemyBattleField())) {
                    printWinner(secondPlayer.getName());
                    state = GameStates.END;
                } // проверка на конец игры
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

    public static String showEnemyBattleField(String name, Scanner sc) {
        System.out.printf("%s, введите " +
                "'1' если хотите увидеть ситуацию на вражеском поле " +
                "'2' если хотите увидеть свою расстановку" +
                "'3' пропустить\n", name);
        return switch (sc.nextLine()) {
            case "1" -> "enemy";
            case "2" -> "my";
            default -> sc.nextLine();
        };
    }

    public static void printEnemyBattleField(Player player) {
        System.out.printf("%s, ваша расстановка: \n", player.getName());
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

    public static void printWinner (String name) {
        System.out.printf("Поздравляю с победой, %s. Игра окончена!\n", name);
    }
}
        /*System.out.println("Игроки, приветствую вас в игре Морской Бой");
        Scanner sc = new Scanner(System.in);

        System.out.println("Игрок 1, напишите свое имя");
        String firstPlayerName = sc.nextLine();
        Player firstPlayer = new Player(firstPlayerName);

        GameStates state = GameStates.COLLOCATION; // ???????

        int decision = 1;
        boolean checkTheShips = true;

        while (decision != 2 && !checkTheShips) {
            System.out.printf("%s, расставьте/переставьте корабли. Для этого напишите координаты всех кораблей в формате -> \n" +
                    "А2-А5, А7-А9, В1-В3, В5-В6, Д2-Д3, Д5-Д6, Д9-Д9, Ж3-Ж3, Ж10-Ж10, К5-К5 \n" +
                    "(по количеству палуб и расположению на поле корабли могут идти в любом порядке, \n " +
                    "у каждого корабля есть координаты X (буквы абвгдежзик) и Y (12345678910))\n", firstPlayer.name);

            String firstPlayerShips = sc.nextLine();
            firstPlayer.myBattleField = GameUtils.arrangeTheShips(firstPlayerShips); // метод пустой!!!

            checkTheShips = true;
            // добавить проверку на корректность расстановки

            //выветсти расстановку в консоль

            if (!checkTheShips) {
                System.out.print("Ваши корабли расставлены неверно.\n" +
                        "Расставьте корабли так, чтобы вокруг них в радиусе одной клетки не было других кораблей \n");
            } else {
                System.out.print("Показываю вам расстановку ваших кораблей: \n" +
                        "Чтобы отредактировать расстановку, введите 1, если готовы начать, нажмите 2 \n");
                decision = sc.nextInt();
            }
        }

        firstPlayer.enemyBattleField = new BattleField();

        for (int i = 0; i < 20; i++) {
            System.out.printf("%s, отвернитесь :)\n", firstPlayer.name);
        }

        System.out.println("Игрок 2, напишите свое имя");
        String secondPlayerName = sc.nextLine();
        Player secondPlayer = new Player(secondPlayerName);

        decision = 1;

        while (decision != 2 && !checkTheShips) {
            System.out.printf("%s, расставьте/переставьте корабли. Для этого напишите координаты всех кораблей в формате -> \n" +
                    "А2-А5, А7-А9, В1-В3, В5-В6, Д2-Д3, Д5-Д6, Д9-Д9, Ж3-Ж3, Ж10-Ж10, К5-К5 \n" +
                    "(по количеству палуб и расположению на поле корабли могут идти в любом порядке, \n " +
                    "у каждого корабля есть координаты X (буквы абвгдежзик) и Y (12345678910))\n", secondPlayer.name);

            String firstPlayerShips = sc.nextLine();
            firstPlayer.myBattleField = GameUtils.arrangeTheShips(firstPlayerShips); // метод пустой!!!

            checkTheShips = true;
            // добавить проверку на корректность расстановки

            // вывести расстановку в консоль

            if (!checkTheShips) {
                System.out.print("Ваши корабли расставлены неверно.\n" +
                        "Расставьте корабли так, чтобы вокруг них в радиусе одной клетки не было других кораблей \n");
            } else {
                System.out.print("Показываю вам расстановку ваших кораблей: \n" +
                        "Чтобы отредактировать расстановку, введите 1, если готовы начать, нажмите 2 \n");
                decision = sc.nextInt();
            }
        }

        secondPlayer.enemyBattleField = new BattleField(); // !!! переделать класс

        for (int i = 0; i < 20; i++) {
            System.out.println("Игра начинается");
        }

        System.out.printf("Ход игрока %s\n", firstPlayer.name);

        state = GameStates.FIRST_PLAYER_MOTION;

        while (state != GameStates.END) {
            if (state == GameStates.FIRST_PLAYER_MOTION) {
                System.out.printf("%s, укажите координаты точки, в которую хотите ударить: \n", firstPlayer.name);
                sc.nextLine(); // отформатировать скан так чтобы он передавал char и int из строки с консоли
                boolean move = firstPlayer.move('A', 5);
                if (!move) {
                    state = GameStates.SECOND_PLAYER_MOTION;
                    System.out.printf("%s, Вы попали! Следующий ход за вами! \n", firstPlayer.name);
                } else if (GameUtils.isGameEnded()) {
                    System.out.printf("%s, Вы победили! \n", firstPlayer.name);
                    state = GameStates.END;
                } else {
                    state = GameStates.FIRST_PLAYER_MOTION;
                    System.out.printf("Вы промахнулись. Сейчас ходит %s\n", secondPlayer.name);
                }
            }

            else {
                if (state == GameStates.SECOND_PLAYER_MOTION) {
                    System.out.printf("%s, укажите координаты точки, в которую хотите ударить: \n", secondPlayer.name);
                    sc.nextLine(); // отформатировать скан так чтобы он передавал char и int из строки с консоли
                    boolean move = secondPlayer.move('A', 5);
                    if (!move) {
                        state = GameStates.FIRST_PLAYER_MOTION;
                        System.out.printf("%s, Вы попали! Следующий ход за вами! \n", secondPlayer.name);
                    } else if (GameUtils.isGameEnded()) {
                        System.out.printf("%s, Вы победили! \n", secondPlayer.name);
                        state = GameStates.END;
                    } else {
                        state = GameStates.FIRST_PLAYER_MOTION;
                        System.out.printf("Вы промахнулись. Сейчас ходит %s\n", firstPlayer.name);
                    }
                }
            }
        }

        System.out.printf("Спасибо что выбрали нас! Оставьте нам отзыв на сайте https://пятыйсыр.окс");*/
