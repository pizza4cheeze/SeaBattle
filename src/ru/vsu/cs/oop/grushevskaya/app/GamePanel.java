package ru.vsu.cs.oop.grushevskaya.app;

import ru.vsu.cs.oop.grushevskaya.Coordinate;
import ru.vsu.cs.oop.grushevskaya.GameStates;
import ru.vsu.cs.oop.grushevskaya.Player;
import ru.vsu.cs.oop.grushevskaya.battleField.BattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.HitStates;
import ru.vsu.cs.oop.grushevskaya.battleField.cell.CellStates;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.EnemyDeckStates;
import ru.vsu.cs.oop.grushevskaya.bot.BotGenius;
import ru.vsu.cs.oop.grushevskaya.bot.Strategy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements MouseListener {
    private final Dimension indentFirstBattleField = new Dimension(50, 50);
    private final Dimension indentSecondBattleField = new Dimension(500, 50);

    private Dimension currBattleFieldIndent = new Dimension(500, 50); // отступы для отрисовки полей

    private final int battleFieldSize = 400;
    private final int size = battleFieldSize / 10; // размеры поля

    Image emptyImage = null;
    Image checkedImage = null;
    Image hurtImage = null; // картинки для отрисовки поля

    Player firstPlayer;
    Player secondPlayer;
    GameStates state; // игрово-логиковые штуки

    JLabel newsAboutMove; // подсказки для игроков о ходе партии

    public GamePanel(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        state = GameStates.FIRST_PLAYER_MOTION;

        setLayout(null);

        // получаем картинки
        try {
            emptyImage = ImageIO.read(new File("empty.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            checkedImage = ImageIO.read(new File("checked.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            hurtImage = ImageIO.read(new File("x.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(1300, 550));

        //добавляем подсказ очки
        newsAboutMove = new JLabel(String.format("%s, сделайте первый ход!", firstPlayer.getName()));
        newsAboutMove.setBounds(48, 480, 800, 40);
        add(newsAboutMove);

        repaint();

//        JButton buttonFirstPlayer = new JButton("Своя расстановка 1");
//        buttonFirstPlayer.addActionListener(e ->
//        {
//            buttonFirstPlayer.setText("Вражеская расстановка"); // todo: сделать для каждого игрока кнопку, при наведении на которую он будет видеть свою расстановку
//        });
//        add(buttonFirstPlayer);

//        buttonFirstPlayer.setLocation(getWidth() - 50, getHeight() - 25);

        this.addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics2D g2d = bi.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(4));

        g2d.drawRect((int) indentFirstBattleField.getWidth(), (int) indentFirstBattleField.getHeight(), battleFieldSize, battleFieldSize);
        BattleField m1 = firstPlayer.getBattleField();
        drawBattleField(g2d, m1, indentFirstBattleField);

        g2d.drawRect((int) indentSecondBattleField.getWidth(), (int) indentSecondBattleField.getHeight(), battleFieldSize, battleFieldSize);
        BattleField m2 = secondPlayer.getBattleField(); // todo: не нашла команды, позволяющей выделить код и вынести его в метод
        drawBattleField(g2d, m2, indentSecondBattleField);

        g2d.setColor(Color.CYAN);
        g2d.drawRect((int) currBattleFieldIndent.getWidth(), (int) currBattleFieldIndent.getHeight(), battleFieldSize, battleFieldSize);
        System.out.println(currBattleFieldIndent.getWidth() + " " + currBattleFieldIndent.getHeight());

        g.drawImage(bi, 0, 0, null);
        g2d.dispose();
    }

    private void drawBattleField(Graphics2D g2d, BattleField battleField, Dimension indent) {
        int indexOnImage = 0;
        for (int width = (int) indent.getWidth() + 15; width < indent.getWidth() + battleFieldSize; width+=size) {
            String s = Integer.toString(indexOnImage);
            g2d.drawString(s, width, (float) (indent.getHeight() - 10));
            indexOnImage++;
        }

        indexOnImage = 0;
        for (int height = (int) indent.getHeight() + 20; height < indent.getHeight() + battleFieldSize; height+=size) {
            String s = Integer.toString(indexOnImage);
            g2d.drawString(s, (float) (indent.getWidth() - 15), height);
            indexOnImage++;
        }

        for (int width = (int) indent.getWidth(); width < indent.getWidth() + battleFieldSize; width += size) {
            for (int height = (int) indent.getHeight(); height < indent.getHeight() + battleFieldSize; height += size) {
                Coordinate tempCoord = pixelToCoord(height, width, indent);
                int row = tempCoord.getRow();
                int col = tempCoord.getColumn();
                if (battleField.getCells()[row][col].isThereAShip()) {
                    if (battleField.getShip(row, col).getEnemyState() == EnemyDeckStates.VISIBLE) {
                        g2d.drawImage(hurtImage, width, height, size, size, this);
                    } else g2d.drawImage(emptyImage, width, height, size, size, this);
                } else if (battleField.getCells()[row][col].getState() == CellStates.CHECKED) {
                    g2d.drawImage(checkedImage, width, height, size, size, this);
                } else g2d.drawImage(emptyImage, width, height,  size, size, this);
            }
        }
    }

    private Coordinate pixelToCoord(int height, int width, Dimension indent) {
        return new Coordinate((int) ((height - indent.getHeight()) / size), (int) ((width - indent.getWidth()) / size));
    }

    private boolean clickInBattleField(MouseEvent e) {
        return e.getX() >= currBattleFieldIndent.getWidth() && e.getX() <= currBattleFieldIndent.getWidth() + battleFieldSize
                && e.getY() >= currBattleFieldIndent.getHeight() && e.getY() <= currBattleFieldIndent.getHeight() + battleFieldSize;
    }

    private boolean playerMove(Player player, Player enemy, Coordinate coordinate) {
        HitStates resultOfMove = enemy.move(coordinate);
        if (resultOfMove == HitStates.MISS) {
            newsAboutMove.setText(String.format("%s, Вы промахнулись. Сейчас ходит %s", player.getName(), enemy.getName()));
            if (state == GameStates.SECOND_PLAYER_MOTION) {
                System.out.println(2);
                state = GameStates.FIRST_PLAYER_MOTION;
                currBattleFieldIndent = indentSecondBattleField;
            } else if (state == GameStates.FIRST_PLAYER_MOTION) {
                System.out.println(1);
                state = GameStates.SECOND_PLAYER_MOTION;
                currBattleFieldIndent = indentFirstBattleField;
            }
        } else {
            newsAboutMove.setText(String.format("%s, Вы попали. Следующий ход за вами", player.getName()));
        }

        if (enemy.getBattleField().isEnemyLose()) {
            state = GameStates.END;
            newsAboutMove.setText(String.format("Игра окончена! Победитель - %s", player.getName()));
        }
        if (resultOfMove != HitStates.MISS) {
            return true;
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (clickInBattleField(e)) {
            if (state == GameStates.FIRST_PLAYER_MOTION) {
                playerMove(firstPlayer, secondPlayer, pixelToCoord(e.getY(), e.getX(), indentSecondBattleField));
                if (state == GameStates.SECOND_PLAYER_MOTION && secondPlayer.getStrategy() == Strategy.BOT) {
                    while (true) {
                        if (!botMove(secondPlayer, firstPlayer)) {
                            break;
                        }
                    }
                    state = GameStates.FIRST_PLAYER_MOTION;
                }
            } else if (state == GameStates.SECOND_PLAYER_MOTION) {
                playerMove(secondPlayer, firstPlayer, pixelToCoord(e.getY(), e.getX(), indentFirstBattleField));
            }
        }
    }

    private boolean botMove(Player player, Player enemy) {
        Coordinate coordinate = BotGenius.botMove(enemy);
        try {
            System.out.println("bot start");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean moved = playerMove(player, enemy, coordinate);
        repaint();
        return moved;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
