import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {
    Player player1 = null;
    Player player2 = null;
    boolean turn; // True --> Player 1 | False --> Player 2
    boolean ballsMoving;
    List<Ball> halfs = new ArrayList<Ball>();
    List<Ball> fulls = new ArrayList<Ball>();
    Ball eightBall;
    Ball cueBall;

    GameManager() {
        this.player1 = null;
        this.player2 = null;

        ballsMoving = false;

        Coord[] balls = Constants.getBallsInitialPositions();

        for (int i = 0; i < balls.length; i++) {
            if (i == 0) {
                cueBall = new Ball(balls[i], true, i);
            } else if (i == 8) {
                eightBall = new Ball(balls[i], false, i);
            } else if (i % 2 == 0) { // TODO divide as in real life
                fulls.add(new Ball(balls[i], false, i));
            } else {
                halfs.add(new Ball(balls[i], true, i));
            }
        }
    }

    public boolean setPlayer(Player p) {
        //TODO send player configs (table coordinates and ball radius)
        if (player1 == null) {
        player1 = p;
        return false;
        }
    
        player2 = p;
        return true; // if both players are sets
    }

    public void StartGame() {

        sendBallsPosition();

        // Select random player to start
        this.turn = new Random().nextBoolean();

        // Set Balls position
        int winner = 0;
        while (winner == 0) {

            Turn();

            turn = !turn;
            ballsMoving = false;

            winner = checkEndGame();
        }
    }

    public void Turn() {
        // At every turn, get cue direction and force
        Vector cue = turn ? player1.yourTurn() : player2.yourTurn();

        // move cue ball

        ballsMoving = true;
    }

    public void sendBallsPosition() {
        // create string with balls position
        // String toSend = "paint;";
        String toSend = "";

        for (Ball b : fulls)
            toSend += b.toString() + ";";

        for (Ball b : halfs)
            toSend += b.toString() + ";";

        toSend += cueBall.toString() + ";";

        toSend += eightBall.toString();

        // pass string to player (they will send it to the client)
        player1.sendBallsPositions(toSend);
        player2.sendBallsPositions(toSend);
    }

    public int checkEndGame() {

        if (halfs.isEmpty() && eightBall.isPotted) {
            if (cueBall.isPotted)
                return player1.hasHalf ? 2 : 1;

            return player1.hasHalf ? 1 : 2;
        } else if (fulls.isEmpty() && eightBall.isPotted) {
            if (cueBall.isPotted)
                return player1.hasHalf ? 1 : 2;

            return player1.hasHalf ? 2 : 1;
        }

        return 0;
    }
}