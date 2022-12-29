package ru.vsu.cs.oop.grushevskaya.battleField.ship;

import com.lezko.simplejson.ArrObj;
import com.lezko.simplejson.Obj;
import ru.vsu.cs.oop.grushevskaya.Coordinate;
import ru.vsu.cs.oop.grushevskaya.GameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Ship {
    private List<Deck> decks;
    int aliveDecksCounter;

    private ShipStates state;

    public Ship(List<Deck> decks) {
        this.decks = decks;
        this.aliveDecksCounter = decks.size();
        this.state = ShipStates.UNTAPPED;
    }

    @Override
    public String toString() {
        Obj arrObj = new ArrObj();
        for (Deck deck : decks) {
            arrObj.append(deck.getRow() + "" + deck.getColumn());
        }
        return arrObj.toString();
    }

    public static Ship fromString(String ass) {
        Obj arr = Obj.fromString(ass);
        List<Deck> decks = new ArrayList<>();
        for (Obj coordObj : arr.toList()) {
            Coordinate coord = GameUtils.getCoordinate(coordObj.toString());
            decks.add(new Deck(coord.getRow(), coord.getColumn()));
        }
        return new Ship(decks);
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

    public int getAliveDecksCounter() {
        return aliveDecksCounter;
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
            this.state = ShipStates.HURT;
        }
        return this.state;
    }
}
