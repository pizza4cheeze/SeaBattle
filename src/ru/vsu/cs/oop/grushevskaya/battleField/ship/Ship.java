package ru.vsu.cs.oop.grushevskaya.battleField.ship;

import ru.vsu.cs.oop.grushevskaya.Coordinate;

import java.util.List;

public class Ship {
    private List<Deck> decks;
    private ShipStates state;
    private int hurtCounter;

    public List<Deck> getDecks() {
        return decks;
    }

    public void hitTheShip(Coordinate coordinate) {
        for (Deck deck : decks) {
            if (deck.getRow() == coordinate.getRow() && deck.getColumn() == coordinate.getColumn()) {
                deck.setState(DeckStates.HURT);
                this.state = ShipStates.HURT;
                hurtCounter++;
            }
        }
        if (hurtCounter == decks.size()) {
            this.state = ShipStates.KILLED;
        }
    }

    public Ship(List<Deck> decks) {
        this.decks = decks;
        this.hurtCounter = 0;
    }
}
