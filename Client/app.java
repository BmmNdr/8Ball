
// app.java
import java.io.IOException;

/**
 * The main class that represents the application.
 */
public class app {
    /**
     * The main method is the entry point of the application.
     * It initializes the server connection window, waits for it to close,
     * and then creates the GUI and GameClient objects.
     * It continuously receives messages from the server and handles them accordingly.
     *
     * @param args the command line arguments
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {

        ServerConnectionWindow scw = new ServerConnectionWindow();

        while (scw.frame.isVisible()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ;

        GUI gui = new GUI();
        GameClient gameClient = new GameClient(gui, scw.ipAddress, scw.port);

        try {
            while (true) {
            String[] tmp = gameClient.in.readLine().split(";", 2);

            if (tmp[0].equals("paint")) {
                gui.hideTurnLabel();
                gui.hideWaitLabel();
                gameClient.receiveGameState(tmp[1]);
            } else if (tmp[0].equals("turn")) {
                // Handle turn
                gameClient.receiveTurn(tmp[1]);
                gameClient.sendNotification();
            } else if (tmp[0].equals("wait")) {
                // Handle wait
                gui.showWaitLabel();
            } else if (tmp[0].equals("end")) {
                // Handle end
                boolean hasWon = tmp[1].equals("1");
                gui.showEndGameLabel(hasWon);
            }
        }
        } catch (Exception e) {
            System.out.println("Server Chiuso");
        }
    }
}
