// app.java
import java.io.IOException;

public class app {
    public static void main(String[] args) throws IOException {
        GUI gui = new GUI();
        GameClient gameClient = new GameClient(gui);


        //TODO riceve un messaggio dal server, poi capisce cosa fare (se muovere le palline, attendere o inviare una mossa)...
        
        while (true) {
            String tmp = gameClient.in.readLine();
            System.out.println(tmp);
            gameClient.receiveGameState(tmp); //TODO ...quindi al receiveGameState invio una stringa con le palline in CSV
        }
    }
}