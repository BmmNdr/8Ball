// GameClient.java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private GUI GUI;

    public GameClient(GUI GUI) throws IOException {
        this.GUI = GUI;
        this.clientSocket = new Socket("127.0.0.1", 666);
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void receiveGameState(String gameState) throws IOException {
        String[] ballsData = gameState.split(";");
        List<Ball> balls = new ArrayList<>();

        for (String ballData : ballsData) {
            String[] parts = ballData.split("-");
            int number = Integer.parseInt(parts[0]);
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            balls.add(new Ball(number, x, y));
        }

        GUI.updateBalls(balls);
    }

    public void sendPlayerMove(String move) {
        out.println(move);
    }

    public void close() throws IOException {
        clientSocket.close();
    }
}