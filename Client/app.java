import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class app {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket clientSocket = new Socket("127.0.0.1", 666);

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    
        out.println("Hello Server");

        String str = in.readLine();

        System.out.println(str);

        clientSocket.close();
    }
}
