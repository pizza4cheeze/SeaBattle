package ru.vsu.cs.oop.grushevskaya.app;

public class PlayersInfo {
    private PlayerInfo first;
    private PlayerInfo second;

    public PlayersInfo(PlayerInfo first, PlayerInfo second) {
        this.first = first;
        this.second = second;
    }

    public PlayerInfo getFirst() {
        return first;
    }

    public PlayerInfo getSecond() {
        return second;
    }
}
