package ru.vsu.cs.oop.grushevskaya.app;

import ru.vsu.cs.oop.grushevskaya.Coordinate;
import ru.vsu.cs.oop.grushevskaya.battleField.BattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Deck;
import ru.vsu.cs.oop.grushevskaya.battleField.ship.Ship;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArrangePanelDemo extends JPanel {
    private ArrayList<Ship> ships = new ArrayList<>();

    private final int battleFieldSize = 400;
    int size = battleFieldSize / 10;
    private Dimension indent = new Dimension(50, 50);

    final GraphicsCell[] graphicsCells = new GraphicsCell[2];

    public ArrangePanelDemo(GayFrame frame) {
        setPreferredSize(new Dimension(1300, 550));

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

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
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                graphicsCells[1] = new GraphicsCell(size, e.getY(), e.getX(), indent);
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        JButton button = new JButton("Готово");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getFirstBattleField(new BattleField(ships));
            }
        });
        add(button);
        button.setLocation(getWidth() - 50, getHeight() - 25);
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
                    GraphicsCell temp = graphicsCells[0];
                    graphicsCells[0] = graphicsCells[1];
                    graphicsCells[1] = temp;
                }
                for (int j = col1; j <= col2; j++) {
                    decks.add(new Deck(row1, j));
                }
            } else if (heightOfTheShip > 0) {
                if (row1 > row2) {
                    GraphicsCell temp = graphicsCells[0];
                    graphicsCells[0] = graphicsCells[1];
                    graphicsCells[1] = temp;
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
    protected void paintComponent(Graphics g) {
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

        g2d.drawImage(deckImage, 0, 0, 40, 40, this);
        g2d.drawImage(emptyImage, 0, 50, 40, 40, this);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect((int) indent.getWidth(), (int) indent.getHeight(), battleFieldSize, battleFieldSize);

        for (int row = (int) indent.getHeight(); row <= battleFieldSize + indent.getHeight(); row += size) {
            for (int col = (int) indent.getWidth(); col <= battleFieldSize + indent.getWidth(); col += size) {
                g2d.drawImage(emptyImage, row, col, size, size, this);
                for (Ship ship : ships) {
                    for (Deck deck : ship.getDecks()) {
                        if (deck.getRow() == (row - 50) / 10 && deck.getColumn() == (col - 50) / 10) {
                            g2d.drawImage(deckImage, row, col, size, size, this);
                        }
                    }
                }
            }
        }


        g2d.setColor(Color.RED);
        int xStart = graphicsCells[0].getScreenColumn();
        int yStart = graphicsCells[0].getScreenRow();

        g2d.drawRect(yStart, xStart, graphicsCells[1].getRow() * 40 + 50 - yStart, graphicsCells[1].getColumn() * 40 + 50 - xStart);

        g.drawImage(bi, 0, 0, null);
        g2d.dispose();
    }
}
