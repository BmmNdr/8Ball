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
    public Vector yourTurn() {

        String ballType;

        if (hasHalf == null)
            ballType = null;
        else if (hasHalf)
            ballType = "half";
        else
            ballType = "full";

        out.println("turn;" + ballType);

        String str = "";
        try {
            str = in.readLine();
        } catch (IOException e) {
            str = "0;0";
        }

        String[] splitStr = str.split(";");

        return new Vector(Double.parseDouble(splitStr[0]), Double.parseDouble(splitStr[1]));
    }

    public void pWait() {
        out.println("wait;");
    }

    public void win() {
        out.println("1;");
    }

    public void lose() {
        out.println("0;");
    }

    public void sendBallsPositions(String position) {
        out.println(position);
    }
}
