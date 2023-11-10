public class MovementManager extends Thread { // Sends ball position to all players every lapse of time
    private GameManager game;

    public MovementManager(GameManager game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (game.ballsMoving) {
            // create string with balls position
            String toSend = "paint;";

            for (Ball b : game.fulls)
                toSend += b.toString() + ";";

            for (Ball b : game.halfs)
                toSend += b.toString() + ";";

            toSend += game.cueBall.toString() + ";";

            toSend += game.eightBall.toString();

            // pass string to player (they will send it to the client)
            game.player1.sendBallsPositions(toSend);
            game.player2.sendBallsPositions(toSend);

            try {
                Thread.sleep(40); // 25 frames per second
            } catch (InterruptedException e) {
            }
        }
    }
}
