package ru.vsu.cs.oop.grushevskaya.app;

public class PlayerInfo {
    private final String name;
    private final boolean bot;

    public PlayerInfo(String name, boolean bot) {
        this.name = name;
        this.bot = bot;
    }

    public String getName() {
        return name;
    }

    public boolean isBot() {
        return bot;
    }
}
