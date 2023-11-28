
// GameClient.java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameClient class represents a client for the 8 Ball game.
 * It handles communication with the server and updates the GUI accordingly.
 */
public class GameClient { 
    private PrintWriter out;
    public BufferedReader in;
    private GUI GUI;

    /**
     * Constructs a GameClient object with the specified GUI, IP address, and port number.
     * @param GUI the GUI object to update with game state and ball movements
     * @param ip the IP address of the server
     * @param port the port number of the server
     * @throws IOException if an I/O error occurs when creating the socket or streams
     */
    public GameClient(GUI GUI, String ip, int port) throws IOException {
        this.GUI = GUI;
        Socket clientSocket = new Socket(ip, port);
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**
     * Receives the game state from the server and updates the GUI with the new ball positions.
     * @param gameState the string representation of the game state
     * @throws IOException if an I/O error occurs when reading the game state
     */
    public void receiveGameState(String gameState) throws IOException {
        String[] ballsData = gameState.split(";");
        List<CBall> balls = new ArrayList<>();

        for (String ballData : ballsData) {
            String[] parts = ballData.split("_");
            int number = Integer.parseInt(parts[0]);
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            balls.add(new CBall(number, x, y));
        }

        GUI.updateBalls(balls);
    }

    /**
     * Sends the player's move to the server.
     * @param move the player's move
     */
    public void sendPlayerMove(String move) {
        out.println(move);
    }

    /**
     * Sends a notification to the server indicating that the player is ready to make a move.
     * Waits until it's the player's turn before sending the notification.
     */
    public void sendNotification() {

        while (GUI.isturn) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Move sent to server: " + GUI.message);

        out.println(GUI.message);
    }

    /**
     * Receives the turn information from the server and updates the GUI with the ball type the player can hit.
     * @param turn the string representation of the turn information
     */
    public void receiveTurn(String turn) {
        String[] parts = turn.split(";");
        String ballType = parts[0]; // get the type of ball the player can hit
        GUI.isturn = true;
        GUI.updateBallType(ballType);
    }
}