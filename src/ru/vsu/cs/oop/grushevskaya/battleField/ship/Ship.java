package ru.vsu.cs.oop.grushevskaya.battleField.ship;

import ru.vsu.cs.oop.grushevskaya.Coordinate;

import java.util.List;

public class Ship {
    private List<Deck> decks;
    int aliveDecksCounter;

    private ShipStates state;

    public Ship(List<Deck> decks) {
        this.decks = decks;
        this.aliveDecksCounter = decks.size();
        this.state = ShipStates.UNTAPPED;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setState(ShipStates state) {
        this.state = state;
    }

    public ShipStates getState() {
        return state;
    }

    public ShipStates hitTheShip(Coordinate coordinate) {
        for (Deck deck : decks) {
            if (deck.getRow() == coordinate.getRow() && deck.getColumn() == coordinate.getColumn()) {
                deck.hitTheDeck(); // ударяю палубу
                aliveDecksCounter--;
            }
        }
        if (aliveDecksCounter == 0) { // обновляю статус корабля
            this.state = ShipStates.KILLED;
        } else if (aliveDecksCounter != decks.size()) {
            this.state = ShipStates.HURT; // TODO: в чем разница между приписыванием this перед обращением к полю и не приписыванием?
        }
        return this.state;
    }
}
