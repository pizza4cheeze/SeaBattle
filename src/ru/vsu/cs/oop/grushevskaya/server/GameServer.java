package ru.vsu.cs.oop.grushevskaya.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
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
        while (true) {
            Socket socket = serverSocket.accept();
            //TODO create client session and start it
            System.out.printf("Client connected from: %s%n", socket.getInetAddress());
//            ClientSession session = new ClientSession(socket);
//            new Thread(session).start();
        }
    }
}
