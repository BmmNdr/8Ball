// app.java
import java.io.IOException;

public class app {
    public static void main(String[] args) throws IOException {
        GUI gui = new GUI();
        GameClient gameClient = new GameClient(gui);

        // Add an ActionListener to the GUI's button that sends the user's move to the server
        gui.getButton().addActionListener(e -> {
            String playerMove = gui.getPlayerMove();
            gameClient.sendPlayerMove(playerMove);
        });


        //TODO riceve un messaggio dal server, poi capisce cosa fare (se muovere le palline, attendere o inviare una mossa)...

        while (true) {
            gameClient.receiveGameState(tmp); //TODO ...quindi al receiveGameState invio una stringa con le palline in CSV
            //TODO evitare flickering
        }
    }
}