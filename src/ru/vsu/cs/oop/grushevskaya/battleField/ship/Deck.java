package ru.vsu.cs.oop.grushevskaya.battleField.ship;

public class Deck {
    private int row;
    private int column;

    private DeckStates state;

    public Deck(int row, int column) {
        this.row = row;
        this.column = column;
        this.state = DeckStates.UNHURT;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setState(DeckStates state) {
        this.state = state;
    }
}
