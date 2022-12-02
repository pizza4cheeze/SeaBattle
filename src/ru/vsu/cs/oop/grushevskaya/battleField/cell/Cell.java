package ru.vsu.cs.oop.grushevskaya.battleField.cell;

public class Cell {
    private int column;
    private int row;
    private boolean shipHere;

    private CellStates state;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.shipHere = false;
        this.state = CellStates.NULL;
    }

    public Cell(int column, int row, boolean shipHere) {
        this.column = column;
        this.row = row;
        this.shipHere = shipHere;
        this.state = CellStates.NULL;
    }

    public void setShipHere() {
        this.shipHere = true;
    }

    public void setState(CellStates state) {
        this.state = state;
    }

    public CellStates getState() {
        return state;
    }

    public boolean isThereAShip() {
        return shipHere;
    }
}
