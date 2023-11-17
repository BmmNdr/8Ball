import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {
    Player player1 = null;
    Player player2 = null;
    boolean turn; // True --> Player 1 | False --> Player 2
    List<Ball> balls;
    CollisionCheck collisionCheck;
    ThreadSend threadSend;

    GameManager() {
        this.player1 = null;
        this.player2 = null;

        Coord[] ballsCoord = Constants.getBallsInitialPositions();

        balls = new ArrayList<Ball>(16);

        for (int i = 0; i < ballsCoord.length; i++) {
            if (i == 0) {
                balls.add(i, new Ball(ballsCoord[i], true, i));
            } else if (i == 8) {
                balls.add(i, new Ball(ballsCoord[i], false, i));
            } else if (i < 8) { // 7 1 6 3 2 4 5
                balls.add(i, new Ball(ballsCoord[i], false, i));
            } else { // 9 12 15 10 14 11 13
                balls.add(i, new Ball(ballsCoord[i], true, i));
            }
        }

        collisionCheck = new CollisionCheck(balls);
        threadSend = new ThreadSend(this);
    }

    public boolean setPlayer(Player p) {
        // TODO send player configs (table coordinates and ball radius)
        if (player1 == null) {
            player1 = p;
            // return false;
            return true; // TODO remove, only for debug
        }

        player2 = p;
        return true; // if both players are sets
    }

    public void StartGame() {

        sendBallsPosition();

        // Select random player to start
        this.turn = new Random().nextBoolean();

        turn = true; // TODO remove, only for debug

        // Set Balls position
        int winner = 0;
        while (winner == 0) {

            Turn();

            turn = !turn;

            winner = checkEndGame();
        }
    }

    public void Turn() {
        // At every turn, get cue direction and force
        Vector cue = turn ? player1.yourTurn() : player2.yourTurn();

        // move cue ball
        balls.get(0).velocity = new Vector(0, 5);

        try {
            moveBalls();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void moveBalls() throws InterruptedException {

        threadSend.start();

        collisionCheck.start();

        for (Ball ball : balls) {
            ball.start();
        }

        threadSend.join();

        collisionCheck.join();

        for (Ball ball : balls) {
            ball.join();
        }

    }

    public void sendBallsPosition() {
        // create string with balls position
        // String toSend = "paint;";
        String toSend = "";

        for (Ball b : balls)
            toSend += b.toString() + ";";

        // pass string to player (they will send it to the client)
        player1.sendBallsPositions(toSend);
        // player2.sendBallsPositions(toSend);
    }

    public int checkEndGame() {

        if (player1.balls.isEmpty() && balls.get(8).isPotted)
            return balls.get(0).isPotted ? 2 : 1;
        else if (player2.balls.isEmpty() && balls.get(8).isPotted)
            return balls.get(0).isPotted ? 1 : 2;

        return 0;
    }
}