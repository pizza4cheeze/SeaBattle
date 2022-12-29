package ru.vsu.cs.oop.grushevskaya.app.server;

import com.lezko.simplejson.MapObj;
import com.lezko.simplejson.Obj;
import ru.vsu.cs.oop.grushevskaya.Coordinate;
import ru.vsu.cs.oop.grushevskaya.GameStates;
import ru.vsu.cs.oop.grushevskaya.GameUtils;
import ru.vsu.cs.oop.grushevskaya.Player;
import ru.vsu.cs.oop.grushevskaya.battleField.BattleField;
import ru.vsu.cs.oop.grushevskaya.battleField.HitStates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerController {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    private Player player;

    public Player getPlayer() {
        return player;
    }

    public PlayerController(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        this.player = new Player("игрок", requestBattleField());
    }

    public void sendMessage(String s) {
        out.println(s);
    }

    public BattleField requestBattleField() {
        return BattleField.fromShipString(receiveLine());
    }

    public void sendState(BattleField battleField, boolean move) {
        Obj obj = new MapObj();
        obj.put("move", String.valueOf(move));
        obj.put("field", battleField.toString());
        sendMessage(obj.toString());
    }

    public HitStates makeMove(Player enemy, Coordinate coordinate) {
        return enemy.move(coordinate);
    }


    public Coordinate requestMove() {
        out.println("move");
        String response = receiveLine();
        return GameUtils.getCoordinate(response);
    }

    public String receiveLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
