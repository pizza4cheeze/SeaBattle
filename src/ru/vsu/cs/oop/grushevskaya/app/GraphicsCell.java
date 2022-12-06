package ru.vsu.cs.oop.grushevskaya.app;

import javax.swing.*;
import java.awt.*;

public class GraphicsCell {
    int size;
    int row, column;
    int screenRow, screenColumn;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getScreenRow() {
        return screenRow;
    }

    public int getScreenColumn() {
        return screenColumn;
    }

    public GraphicsCell(int size, int screenRow, int screenColumn, Dimension indent) {
        this.size = size;
        this.screenRow = screenRow;
        this.screenColumn = screenColumn;
        pixelToCoord(indent);
    }

    public void coordToPixel(Dimension indent) {
        this.screenRow = (int) (row * size + indent.getHeight());
        this.screenColumn = (int) (column * size + indent.getWidth());
    }

    public void pixelToCoord(Dimension indent) {
        this.row = (int) ((screenRow - indent.getHeight()) / size);
        this.column = (int) ((screenColumn - indent.getWidth()) / size);
    }
}
