/**
 * The ThreadSend class represents a thread responsible for sending the positions of the balls to the players.
 * It extends the Thread class and runs in the background while the balls are moving.
 */
public class ThreadSend extends Thread {
    GameManager game;

    /**
     * Constructs a ThreadSend object with the specified GameManager.
     * 
     * @param game the GameManager object
     */
    public ThreadSend(GameManager game) {
        this.game = game;
    }

    /**
     * Runs the thread and continuously sends the positions of the balls to the players until the balls stop moving.
     */
    @Override
    public void run() {
        while (ballsMoving()) {
            sendBallsPosition();

            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * Sends the positions of the balls to the players.
     */
    public void sendBallsPosition() {
        // create string with balls position
        String toSend = "paint;";

        for (Ball b : game.balls)
            toSend += b.toString() + ";";

        // pass string to player (they will send it to the client)
        game.player1.sendBallsPositions(toSend);

        if (!game.debugMode)
            game.player2.sendBallsPositions(toSend);
    }

    /**
     * Checks if any of the balls are still moving.
     * 
     * @return true if any of the balls are moving, false otherwise
     */
    private boolean ballsMoving() {
        for (Ball ball : game.balls) {
            if (ball.isMoving)
                return true;
        }

        return false;
    }
}