import java.util.List;
import java.util.Random;

public class GameManager {
    Player player1 = null;
    Player player2 = null;
    boolean turn; // True --> Player 1 | False --> Player 2
    boolean ballsMoving;
    List<Ball> halfs;
    List<Ball> fulls;
    Ball eightBall;
    Ball cueBall;

    GameManager() {
        this.player1 = null;
        this.player2 = null;

        ballsMoving = false;
    }

    public boolean setPlayer(Player p) {
        if (player1 == null) {
            player1 = p;
            return false;
        }

        player2 = p;
        return true; // if both players are sets
    }

    public void StartGame() {
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