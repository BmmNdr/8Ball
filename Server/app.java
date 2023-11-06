import java.io.IOException;
import java.net.ServerSocket;

public class app {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket;
        int port = 666;
        boolean closeServer = false;

        serverSocket = new ServerSocket(port);

        while(!closeServer){
            new Player(serverSocket.accept()).start();
        }

        serverSocket.close();
    }
}
