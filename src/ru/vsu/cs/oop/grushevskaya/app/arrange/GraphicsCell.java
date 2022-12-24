package ru.vsu.cs.oop.grushevskaya.app.arrange;

import java.awt.*;

public class GraphicsCell {
    private final int row;
    private  final int column;
    private final int screenRow;
    private final int screenColumn;

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
        this.screenRow = screenRow;
        this.screenColumn = screenColumn;
        this.row = (int) ((this.screenRow - indent.getHeight()) / size);
        this.column = (int) ((this.screenColumn - indent.getWidth()) / size);
    }
}
