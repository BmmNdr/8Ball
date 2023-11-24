
// app.java
import java.io.IOException;

public class app {
    public static void main(String[] args) throws IOException {
        GUI gui = new GUI();
        GameClient gameClient = new GameClient(gui);

        while (true) {
            String[] tmp = gameClient.in.readLine().split(";", 2);
            System.out.println(tmp[1]);

            if (tmp[0].equals("paint")) {
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
    }
}
