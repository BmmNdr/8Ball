import java.io.IOException;
import java.net.ServerSocket;

public class server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket;
        int port = 666;
        boolean startGame = false;

        serverSocket = new ServerSocket(port);

        GameManager game = new GameManager();
        while (!startGame) {
            startGame = game.setPlayer(new Player(serverSocket.accept()));
        }

        try {
            // START GAME!!
            game.StartGame(); // If GameManager is a Thread, you can have multiple games at the same time
        } catch (Exception e) {
            System.out.println("Giocatori disconnessi");
        }

        serverSocket.close();
    }
}
