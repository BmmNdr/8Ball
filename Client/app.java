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

        while (true) {
            gameClient.receiveGameState();

            // Sleep for a bit to simulate the time it takes for a player to make a move
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}