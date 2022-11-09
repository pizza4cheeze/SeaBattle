package ru.vsu.cs.oop.grushevskaya.battleField.cell;

public class Cell {
    private int column;
    private int row;
    private boolean shipHere;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.shipHere = false;
    }

    public boolean isThereAShip() {
        return shipHere;
    }

    public Cell(int column, int row, boolean shipHere) {
        this.column = column;
        this.row = row;
        this.shipHere = shipHere;
    }
}
