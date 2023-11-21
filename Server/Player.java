import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Boolean hasHalf;
    public List<Ball> balls;

    public Player(Socket socket) throws IOException {
        clientSocket = socket;

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        balls = new ArrayList<Ball>();
    }

    // Your turn method, returs cue direction and power (cue ball speed and angle)
    public Vector yourTurn() { // TODO

        return new Vector(0, 5);
    }

    public void sendBallsPositions(String position) {
        out.println(position);
    }
}
