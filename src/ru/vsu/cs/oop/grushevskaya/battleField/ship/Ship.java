package ru.vsu.cs.oop.grushevskaya.battleField.ship;

import java.util.List;

public class Ship {
    private List<Deck> decks;

    public List<Deck> getDecks() {
        return decks;
    }

    public boolean isShipDead() {
        return false;
        // реализация
    }

    public void hitTheShip(Deck deck) {
        // реализация
    }

    public Ship(List<Deck> decks) {
        this.decks = decks;
    }
}
