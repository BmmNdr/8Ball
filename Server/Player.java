import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player{
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Player(Socket socket) throws IOException {
        clientSocket = socket;

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void test() {
        String str;
        try {

            str = in.readLine();

            System.out.println(str);

            out.println("Hello Client");

            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {}
    }

    //Your turn method, returs cue direction and power (Vector??)
}
