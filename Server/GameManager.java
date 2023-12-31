import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * The GameManager class represents the game manager for the 8 Ball game.
 * It manages the players, balls, turns, collisions, and game logic.
 */
public class GameManager {
    Player player1 = null;
    Player player2 = null;
    boolean turn; // True --> Player 1 | False --> Player 2
    List<Ball> balls;
    CollisionCheck collisionCheck;
    ThreadSend threadSend;

    boolean potInTurn = false;
    boolean foulInTurn = false;

    public boolean debugMode = true; //Activates debug mode

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
        if (player1 == null) {
            player1 = p;

            System.out.println("Player 1 Connected");
            return debugMode;
        }

        player2 = p;
        System.out.println("Player 2 Connected");
        return true; // if both players are sets
    }

    /**
     * Starts the game by selecting a random player to start and setting up the balls' positions.
     * Continues the game until a winner is determined.
     * If a foul occurs or a player fails to pot any ball in their turn, the turn passes to the other player.
     * Updates the players' scores accordingly at the end of the game.
     *
     * @throws IOException if an I/O error occurs during the game.
     */
    public void StartGame() throws IOException {
        
        // Select random player to start
        this.turn = new Random().nextBoolean();

        // Set Balls position
        int winner = 0;
        while (winner == 0) {

            // Sends initial balls positions
            sendBallsPosition();

            if (debugMode)
                turn = true;

            Turn();

            winner = checkEndGame();

            // If in the turn a foul happened or the player didn't pot any ball...
            if (foulInTurn || !potInTurn)
                turn = !turn; // ...turn passes to the other player
        }

        if (winner == 1) {
            player1.win();

            if (!debugMode)
                player2.lose();
        } else if (winner == 2) {
            player1.lose();

            if (!debugMode)
                player2.win();
        }
    }

    /**
     * Executes a turn in the game.
     * This method resets the turn variables, gets the cue direction and force from the current player,
     * sets the cue ball velocity, moves the balls, checks for fouls and collisions,
     * updates the player's ball types if necessary, handles potted balls, and updates the players' ball lists.
     *
     * @throws IOException if a player disconnects during the turn.
     */
    public void Turn() throws IOException {
        // Reset turn var
        potInTurn = false;
        foulInTurn = false;

        // At every turn, get cue direction and force while the other player waits
        if (!turn)
            player1.pWait();
        else if (!debugMode)
            player2.pWait();

        Vector cue = turn ? player1.yourTurn() : player2.yourTurn();

        // ...and set cue ball velocity (speed and direction)
        balls.get(0).velocity = cue;

        try {
            moveBalls();
        } catch (InterruptedException e) {
        }

        // Checks if the first hitted ball has the player's type...
        if (collisionCheck.firstCueHit != null) {
            Boolean playerHasHalf = turn ? player1.hasHalf : player2.hasHalf;

            // ...if not, a foul has been committed
            if (playerHasHalf != null && playerHasHalf != collisionCheck.firstCueHit.isHalf) {
                foulInTurn = true;
                System.out.println("First Hit Foul");
            }
        } else {// no ball hitted, foul
            foulInTurn = true;
            System.out.println("No ball hitted, foul");
        }

        // If halfs and fulls are not set and a ball has been potted...
        if (player1.hasHalf == null && collisionCheck.firstPot != null && collisionCheck.firstPot.number > 0) {
            // ... the player in turn has the potted ball's type
            if (turn) {
                player1.hasHalf = collisionCheck.firstPot.isHalf;

                for (int i = 1; i < 8; i++)
                    player1.balls.add(balls.get(i));

                if (!debugMode) {
                    player2.hasHalf = !collisionCheck.firstPot.isHalf;

                    for (int i = 9; i <= 15; i++)
                        player2.balls.add(balls.get(i));
                }

            } else {

                if (!debugMode) {
                    player2.hasHalf = collisionCheck.firstPot.isHalf;

                    for (int i = 1; i < 8; i++)
                        player2.balls.add(balls.get(i));
                }

                player1.hasHalf = !collisionCheck.firstPot.isHalf;

                for (int i = 9; i <= 15; i++)
                    player1.balls.add(balls.get(i));
            }

            System.out.println("Player 1 has " + (player1.hasHalf ? "halfs" : "fulls"));

            if (!debugMode)
                System.out.println("Player 2 has " + (player2.hasHalf ? "halfs" : "fulls"));
        }

        // Check if the cue ball has been potted (foul)
        if (balls.get(0).isPotted || foulInTurn) {
            balls.get(0).coordinate = Constants.getBallsInitialPositions()[0];
            balls.get(0).isPotted = false;
            foulInTurn = true;

            System.out.println("Cue Ball Potted, foul");
        }

        // Removes potted balls from players' balls list
        for (Iterator<Ball> it = player1.balls.iterator(); it.hasNext();) {
            Ball ball = it.next();

            if (ball.isPotted) {
                it.remove();

                if (turn)
                    potInTurn = true;
            }
        }

        if (!debugMode) {
            for (Iterator<Ball> it = player2.balls.iterator(); it.hasNext();) {
                Ball ball = it.next();

                if (ball.isPotted) {
                    it.remove();

                    if (!turn)
                        potInTurn = true;
                }
            }
        }
    }

    /**
     * Moves the balls in the game by starting the threads for each ball and performing collision checks.
     * This method waits for the threads to finish before returning.
     *
     * @throws InterruptedException if the thread is interrupted while waiting for the threads to finish.
     */
    public void moveBalls() throws InterruptedException {

        // Makes threads from runnable objects every time so i can "re-start" them
        List<Thread> runnableBalls = new ArrayList<Thread>();
        for (Ball ball : balls) {
            runnableBalls.add(new Thread(ball));
        }

        Thread runnablThreadSend = new Thread(threadSend);
        Thread runnableCollisionCheck = new Thread(collisionCheck);

        for (Ball ball : balls) {
            ball.stop = false;
        }

        for (Thread ball : runnableBalls) {
            ball.start();
        }

        runnableCollisionCheck.start();
        runnablThreadSend.start();

        runnablThreadSend.join();
        runnableCollisionCheck.join();

        for (Ball ball : balls) {
            ball.stop = true;
        }

        for (Thread ball : runnableBalls) {
            // ball.stop(); //Brutal Method
            ball.join();
        }
    }

    /**
     * Sends the positions of the balls to the players.
     * Creates a string with the positions of all the balls and sends it to the players.
     * The string is formatted as "paint;ball1;ball2;ball3;...".
     * The players will then send this string to the client.
     * If debug mode is disabled, the positions are sent to both players.
     */
    public void sendBallsPosition() {
        // create string with balls position
        String toSend = "paint;";

        for (Ball b : balls)
            toSend += b.toString() + ";";

        // pass string to player (they will send it to the client)
        player1.sendBallsPositions(toSend);

        if (!debugMode)
            player2.sendBallsPositions(toSend);
    }

    /**
     * Checks if the game has ended and determines the winner.
     * 
     * @return 0 if the game has not ended, 1 if player 1 wins, 2 if player 2 wins.
     */
    public int checkEndGame() {

        if (player1.balls.isEmpty() && balls.get(8).isPotted)
            return balls.get(0).isPotted ? 2 : 1;
        else if (!debugMode && player2.balls.isEmpty() && balls.get(8).isPotted)
            return balls.get(0).isPotted ? 1 : 2;
        else if (balls.get(8).isPotted) // 8 ball has been potted
            return turn ? 2 : 1;

        return 0;
    }

    public boolean ballsMoving() {
        for (Ball ball : balls) {
            if (ball.isMoving)
                return true;
        }

        return false;
    }
}
