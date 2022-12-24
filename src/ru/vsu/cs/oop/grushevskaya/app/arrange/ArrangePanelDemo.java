package ru.vsu.cs.oop.grushevskaya.app.arrange;

import ru.vsu.cs.oop.grushevskaya.GameUtils;
import ru.vsu.cs.oop.grushevskaya.app.GayFrame;
import ru.vsu.cs.oop.grushevskaya.battleField.BattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Deck;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArrangePanelDemo extends JPanel implements MouseListener, MouseMotionListener {
    private final List<Ship> ships = new ArrayList<>();

    private final int battleFieldSize = 400;
    private final int size = battleFieldSize / 10;
    private final Dimension indent = new Dimension(50, 50);

    final GraphicsCell[] graphicsCells = new GraphicsCell[2];

    public ArrangePanelDemo(GayFrame frame) {
        setPreferredSize(new Dimension(1300, 550));
        graphicsCells[0] = new GraphicsCell(0, (int) indent.getWidth(), (int) indent.getHeight(), indent);
        graphicsCells[1] = new GraphicsCell(0, (int) indent.getWidth(), (int) indent.getHeight(), indent);

        setLayout(null);

        JButton button = new JButton("Готово");
        button.addActionListener(e ->
        {
            try {
                frame.getFirstBattleField(new BattleField(ships));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        button.setBounds(48, 480, 100, 30);
        add(button);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        graphicsCells[0] = new GraphicsCell(size, e.getY(), e.getX(), indent);
        graphicsCells[1] = new GraphicsCell(size, e.getY(), e.getX(), indent);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        graphicsCells[0] = new GraphicsCell(size, e.getY(), e.getX(), indent);
        graphicsCells[1] = new GraphicsCell(size, e.getY(), e.getX(), indent);
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        graphicsCells[1] = new GraphicsCell(size, e.getY(), e.getX(), indent);
        createShip(graphicsCells);
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        graphicsCells[1] = new GraphicsCell(size, e.getY(), e.getX(), indent);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e){
    }

    private void createShip(GraphicsCell[] graphicsCells) {
        int col1 = graphicsCells[0].getColumn();
        int row1 = graphicsCells[0].getRow();

        int col2 = graphicsCells[1].getColumn();
        int row2 = graphicsCells[1].getRow();

        if (col1 == col2 || row1 == row2) {
            int widthOfTheShip = Math.abs(col1 - col2);
            int heightOfTheShip = Math.abs(row1 - row2);

            List<Deck> decks = new ArrayList<>();

            if (widthOfTheShip > 0) {
                if (col1 > col2) {
                    int temp = col2;
                    col2 = col1;
                    col1 = temp;
                }
                for (int j = col1; j <= col2; j++) {
                    decks.add(new Deck(row1, j));
                }
            } else if (heightOfTheShip > 0) {
                if (row1 > row2) {
                    int temp = row1;
                    row1 = row2;
                    row2 = temp;
                }
                for (int i = row1; i <= row2; i++) {
                    decks.add(new Deck(i, col1));
                }
            } else {
                decks.add(new Deck(row1, col1));
            }

            ships.add(new Ship(decks));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics2D g2d = bi.createGraphics();

        Image deckImage = null;
        try {
            deckImage = ImageIO.read(new File("ship.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image emptyImage = null;
        try {
            emptyImage = ImageIO.read(new File("empty.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

//        g2d.drawImage(deckImage, 0, 0, 40, 40, this);
//        g2d.drawImage(emptyImage, 0, 50, 40, 40, this);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect((int) indent.getWidth(), (int) indent.getHeight(), battleFieldSize, battleFieldSize);

        for (int row = (int) indent.getHeight(); row < battleFieldSize + indent.getHeight(); row += size) {
            for (int col = (int) indent.getWidth(); col < battleFieldSize + indent.getWidth(); col += size) {
                g2d.drawImage(emptyImage, col, row, size, size, this);
                for (Ship ship : ships) {
                    for (Deck deck : ship.getDecks()) {
                        if (deck.getRow() == (row - indent.getHeight()) / size && deck.getColumn() == (col - indent.getWidth()) / size) {
                            g2d.drawImage(deckImage, col, row, size, size, this);
                        }
                    }
                }
            }
        }

        g2d.setColor(Color.RED);
        int xStart = graphicsCells[0].getScreenColumn();
        int yStart = graphicsCells[0].getScreenRow();

        g2d.drawRect(xStart, yStart, graphicsCells[1].getScreenColumn() - xStart, graphicsCells[1].getScreenRow() - yStart);

        g.drawImage(bi, 0, 0, null);
        g2d.dispose();
    }
}
