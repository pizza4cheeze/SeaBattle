package ru.vsu.cs.oop.grushevskaya.app.server;

import ru.vsu.cs.oop.grushevskaya.Coordinate;
import ru.vsu.cs.oop.grushevskaya.GameStates;
import ru.vsu.cs.oop.grushevskaya.Player;
import ru.vsu.cs.oop.grushevskaya.battleField.BattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.HitStates;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameServer {
    List<PlayerController> players = new ArrayList<>();
    GameStates state = GameStates.FIRST_PLAYER_MOTION;

    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer(9999);
        server.start();
    }

    private final int port;

    public GameServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        System.out.printf("Server started on: %d%n", port);
        ServerSocket serverSocket = new ServerSocket(port);
        for (int i = 0; i < 2; i++) {
            Socket socket = serverSocket.accept();
            PlayerController playerController = new PlayerController(socket);
            players.add(playerController);
        }

//        for (BattleField battleField : players.values()) {
//            System.out.println(battleField.toShipsString());
//            System.out.println(battleField.toString());
//        }

        while (state != GameStates.END) {
            boolean f = true;
            PlayerController p1, p2;
            if (f) {
                p1 = players.get(0);
                p2 = players.get(1);
            } else {
                p1 = players.get(1);
                p2 = players.get(0);
            }
            Coordinate coordinate = p1.requestMove();
            HitStates state = p1.makeMove(p2.getPlayer(), coordinate);

            p1.sendMessage(state.name());
            p2.sendMessage("y turn " + state.name());
            f = !f;
        }

//        while (true) {
//            Socket socket = serverSocket.accept();
//            //TODO create client session and start it
//            System.out.printf("Client connected from: %s%n", socket.getInetAddress());
//            ClientSession session = new ClientSession(socket);
//            new Thread(session).start();
//        }
    }

}
