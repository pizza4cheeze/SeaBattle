package ru.vsu.cs.oop.grushevskaya.app.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9999);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()) {
                out.println(sc.nextLine());
                System.out.println(in.readLine());
            }
            out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
