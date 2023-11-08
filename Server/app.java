import java.io.IOException;
import java.net.ServerSocket;

public class app {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket;
        int port = 666;
        boolean startGame = false;

        serverSocket = new ServerSocket(port);

        GameManager game = new GameManager();
        while(!startGame){
            startGame = game.setPlayer(new Player(serverSocket.accept()));
        }


        //STAR GAME!!
        game.StartGame(); //If GameManager is a Thread, you can have multiple games at the same time

        serverSocket.close();
    }
}
