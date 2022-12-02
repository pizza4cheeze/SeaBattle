package ru.vsu.cs.oop.grushevskaya.battleField.ship;

public class Deck {
    private int row;
    private int column;

    private DeckStates state;
    private EnemyDeckStates enemyState;

    public Deck(int row, int column) {
        this.row = row;
        this.column = column;
        this.state = DeckStates.UNHURT;
        this.enemyState = EnemyDeckStates.INVISIBLE;
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

    public DeckStates getState() {
        return state;
    }

    public void setEnemyState(EnemyDeckStates enemyState) {
        this.enemyState = enemyState;
    }

    public EnemyDeckStates getEnemyState() {
        return enemyState;
    }

    public void hitTheDeck() {
        this.state = DeckStates.HURT;
        this.enemyState = EnemyDeckStates.VISIBLE;
    }
}
