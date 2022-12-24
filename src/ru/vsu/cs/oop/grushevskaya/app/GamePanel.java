package ru.vsu.cs.oop.grushevskaya.app;

import ru.vsu.cs.oop.grushevskaya.Coordinate;
import ru.vsu.cs.oop.grushevskaya.GameStates;
import ru.vsu.cs.oop.grushevskaya.Player;
import ru.vsu.cs.oop.grushevskaya.battleField.BattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.HitStates;
import ru.vsu.cs.oop.grushevskaya.battleField.cell.CellStates;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.EnemyDeckStates;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements MouseListener {
    private Dimension indentFirstBattleField = new Dimension(50, 50);
    private Dimension indentSecondBattleField = new Dimension(500, 50);

    private Dimension currBattleFieldIndent = new Dimension(500, 50);

    Player firstPlayer;
    Player secondPlayer;
    GameStates state;

    private final int battleFieldSize = 400;
    private final int size = battleFieldSize / 10;

    private Coordinate coordinate;

//    JLabel newsAboutMove;

    public GamePanel(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        state = GameStates.FIRST_PLAYER_MOTION;

        setPreferredSize(new Dimension(1300, 550));
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

        Image emptyImage = null;
        try {
            emptyImage = ImageIO.read(new File("empty.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image checkedImage = null;
        try {
            checkedImage = ImageIO.read(new File("checked.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image hurtImage = null;
        try {
            hurtImage = ImageIO.read(new File("x.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect((int) indentFirstBattleField.getWidth(), (int) indentFirstBattleField.getHeight(), battleFieldSize, battleFieldSize);
        g2d.drawRect((int) indentSecondBattleField.getWidth(), (int) indentSecondBattleField.getHeight(), battleFieldSize, battleFieldSize);

        BattleField m1 = firstPlayer.getBattleField();
        for (int width = (int) indentFirstBattleField.getWidth(); width < indentFirstBattleField.getWidth() + battleFieldSize; width += size) {
            for (int height = (int) indentFirstBattleField.getHeight(); height < indentFirstBattleField.getHeight() + battleFieldSize; height += size) {
                Coordinate tempCoord = pixelToCoord(height, width, indentFirstBattleField);
                int row = (int) tempCoord.getRow();
                int col = (int) tempCoord.getColumn();
                if (m1.getCells()[row][col].isThereAShip()) {
                    if (m1.getShip(row, col).getEnemyState() == EnemyDeckStates.VISIBLE) {
                        g2d.drawImage(hurtImage, width, height, size, size, this);
                    } else g2d.drawImage(emptyImage, width, height, size, size, this);
                } else if (m1.getCells()[row][col].getState() == CellStates.CHECKED) {
                    g2d.drawImage(checkedImage,width,  height, size, size, this);
                } else g2d.drawImage(emptyImage,width, height,  size, size, this);
            }
        }

        BattleField m2 = secondPlayer.getBattleField();
        for (int width = (int) indentSecondBattleField.getWidth(); width < indentSecondBattleField.getWidth() + battleFieldSize; width += size) {
            for (int height = (int) indentSecondBattleField.getHeight(); height < indentSecondBattleField.getHeight() + battleFieldSize; height += size) {
                Coordinate tempCoord = pixelToCoord(height, width, indentSecondBattleField);
                int row = (int) tempCoord.getRow();
                int col = (int) tempCoord.getColumn();
                if (m2.getCells()[row][col].isThereAShip()) {
                    if (m2.getShip(row, col).getEnemyState() == EnemyDeckStates.VISIBLE) {
                        g2d.drawImage(hurtImage, width, height, size, size, this);
                    } else g2d.drawImage(emptyImage, width, height, size, size, this);
                } else if (m2.getCells()[row][col].getState() == CellStates.CHECKED) {
                    g2d.drawImage(checkedImage,width,  height, size, size, this);
                } else g2d.drawImage(emptyImage,width, height,  size, size, this);
            }
        }

        g.drawImage(bi, 0, 0, null);
        g2d.dispose();
    }

    public Coordinate pixelToCoord(int height, int width, Dimension indent) {
        return new Coordinate((int) ((width - indent.getWidth()) / size), (int) ((height - indent.getHeight()) / size));
    }

    private boolean clickInBattleField(MouseEvent e) {
        return e.getX() >= currBattleFieldIndent.getWidth() && e.getX() <= currBattleFieldIndent.getWidth() + battleFieldSize
                && e.getY() >= currBattleFieldIndent.getHeight() && e.getY() <= currBattleFieldIndent.getHeight() + battleFieldSize;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (clickInBattleField(e)) {
            if (state == GameStates.FIRST_PLAYER_MOTION) {
                playerMove(secondPlayer, pixelToCoord(e.getY(), e.getX(), indentSecondBattleField));
            } else if (state == GameStates.SECOND_PLAYER_MOTION) {
                playerMove(firstPlayer, pixelToCoord(e.getY(), e.getX(), indentFirstBattleField));
            }
        }
        repaint();
    }

    private void playerMove(Player enemy, Coordinate coordinate) {
        HitStates resultOfMove = enemy.move(coordinate);
        if (resultOfMove == HitStates.MISS) {
            if (state == GameStates.SECOND_PLAYER_MOTION) {
                state = GameStates.FIRST_PLAYER_MOTION;
                currBattleFieldIndent = indentSecondBattleField;
                // todo: показывать игрокам на label'е результаты их ходов
            } else if (state == GameStates.FIRST_PLAYER_MOTION) {
                state = GameStates.SECOND_PLAYER_MOTION;
                currBattleFieldIndent = indentFirstBattleField;
            }
        }

        if (enemy.getBattleField().isEnemyLose()) {
            state = GameStates.END; // todo: печатать победителя
        }
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
