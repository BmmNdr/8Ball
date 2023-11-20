public class ThreadSend extends Thread{
    GameManager game;

    public ThreadSend(GameManager game) {
        this.game = game;
    }

    @Override
    public void run() {
        while(ballsMoving()){
            sendBallsPosition();

            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public void sendBallsPosition() {
        // create string with balls position
        // String toSend = "paint;";
        String toSend = "";

        for (Ball b : game.balls)
            toSend += b.toString() + ";";

        // pass string to player (they will send it to the client)
        game.player1.sendBallsPositions(toSend);
        // player2.sendBallsPositions(toSend);
    }

    private boolean ballsMoving(){
        for (Ball ball : game.balls) {
            if(ball.isMoving)
                return true;
        }

        return false;
    }
}
