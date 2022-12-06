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
    public ArrangePanelDemo(GayFrame gayFrame) {

    }
    //    private ArrayList<Ship> ships = new ArrayList<>();
//
//    private final int battleFieldSize = 400;
//    int size = battleFieldSize / 10;
//    private Dimension indent = new Dimension(50, 50);
//
//    final GraphicsCell[] graphicsCells = new GraphicsCell[2];
//
//    public ArrangePanelDemo(GayFrame frame) {
//        setPreferredSize(new Dimension(1300, 550));
//
//        addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                graphicsCells[0] = new GraphicsCell(size, e.getY(), e.getX(), indent);
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                graphicsCells[1] = new GraphicsCell(size, e.getY(), e.getX(), indent);
//                createShip(graphicsCells);
//                repaint();
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//
//            }
//        });
//
//        addMouseMotionListener(new MouseMotionListener() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                graphicsCells[1] = new GraphicsCell(size, e.getY(), e.getX(), indent);
//                repaint();
//            }
//
//            @Override
//            public void mouseMoved(MouseEvent e) {
//
//            }
//        });
//
//        JButton button = new JButton("Готово");
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                frame.getFirstBattleField(new BattleField(ships));
//            }
//        });
//        add(button);
//        button.setLocation(getWidth() - 50, getHeight() - 25);
//    }
//
//
//    private void createShip(Coordinate[] graphicsCells) {
//        if (ArrangePanelDemo.this.graphicsCells[0].getColumn() == ArrangePanelDemo.this.graphicsCells[1].getColumn() || ArrangePanelDemo.this.graphicsCells[0].getRow() == ArrangePanelDemo.this.graphicsCells[1].getRow()) {
//            int widthOfTheShip = Math.abs(ArrangePanelDemo.this.graphicsCells[0].getColumn() - ArrangePanelDemo.this.graphicsCells[1].getColumn());
//            int heightOfTheShip = Math.abs(ArrangePanelDemo.this.graphicsCells[0].getRow() - ArrangePanelDemo.this.graphicsCells[1].getRow());
//
//            List<Deck> decks = new ArrayList<>();
//
//            if (widthOfTheShip > 0) {
//                if (ArrangePanelDemo.this.graphicsCells[0].getColumn() > ArrangePanelDemo.this.graphicsCells[1].getColumn()) {
//                    Coordinate temp = ArrangePanelDemo.this.graphicsCells[0];
//                    ArrangePanelDemo.this.graphicsCells[0] = ArrangePanelDemo.this.graphicsCells[1];
//                    ArrangePanelDemo.this.graphicsCells[1] = temp;
//                }
//                for (int j = ArrangePanelDemo.this.graphicsCells[0].getColumn(); j <= ArrangePanelDemo.this.graphicsCells[1].getColumn(); j++) {
//                    decks.add(new Deck(ArrangePanelDemo.this.graphicsCells[0].getRow(), j));
//                }
//            } else if (heightOfTheShip > 0) {
//                if (ArrangePanelDemo.this.graphicsCells[0].getRow() > ArrangePanelDemo.this.graphicsCells[1].getRow()) {
//                    Coordinate temp = ArrangePanelDemo.this.graphicsCells[0];
//                    ArrangePanelDemo.this.graphicsCells[0] = ArrangePanelDemo.this.graphicsCells[1];
//                    ArrangePanelDemo.this.graphicsCells[1] = temp;
//                }
//                for (int i = ArrangePanelDemo.this.graphicsCells[0].getRow(); i <= ArrangePanelDemo.this.graphicsCells[1].getRow(); i++) {
//                    decks.add(new Deck(i, ArrangePanelDemo.this.graphicsCells[0].getColumn()));
//                }
//            } else {
//                decks.add(new Deck(ArrangePanelDemo.this.graphicsCells[0].getRow(), ArrangePanelDemo.this.graphicsCells[0].getColumn()));
//            }
//
//            ships.add(new Ship(decks));
//        }
//    }
//
//        @Override
//    protected void paintComponent(Graphics g) {
//        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
//        Graphics2D g2d = bi.createGraphics();
//
//        Image deckImage = null;
//        try {
//                deckImage = ImageIO.read(new File("ship.png"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        Image emptyImage = null;
//        try {
//                emptyImage = ImageIO.read(new File("empty.png"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        g2d.setColor(Color.WHITE);
//        g2d.fillRect(0, 0, getWidth(), getHeight());
//
//        g2d.drawImage(deckImage, 0, 0, 40, 40, this);
//        g2d.drawImage(emptyImage, 0, 50, 40, 40, this);
//
//        g2d.setColor(Color.BLACK);
//        g2d.setStroke(new BasicStroke(4));
//        g2d.drawRect(50, 50, 450, 450);
//
//        for (int row = (int) indent.getHeight(); row < battleFieldSize.getHeight() + indent.getHeight(); row += (battleFieldSize.getHeight() / 10)) {
//            for (int col = (int) indent.getWidth(); col < battleFieldSize.getWidth() + indent.getWidth(); row += (battleFieldSize.getWidth() / 10)) {
//                g2d.drawImage(emptyImage, row, col, 40, 40, this);
//                for (Ship ship : ships) {
//                    for (Deck deck : ship.getDecks()) {
//                        if (deck.getRow() == (row - 50) / 10 && deck.getColumn() == (col - 50) / 10) {
//                            g2d.drawImage(deckImage, row, col, 40, 40, this);
//                        }
//                    }
//                }
//            }
//        }
//
//        g2d.setColor(Color.RED);
//        int xStart = graphicsCells[0].getColumn() * 40 + 50;
//        int yStart = graphicsCells[0].getRow() * 40 + 50;
//
//        g2d.drawRect(yStart, xStart, graphicsCells[1].getRow() * 40 + 50 - yStart, graphicsCells[1].getColumn() * 40 + 50 - xStart);
//
//        g.drawImage(bi, 0, 0, null);
//        g2d.dispose();
//
//    }
}
