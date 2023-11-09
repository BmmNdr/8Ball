public class Ball extends Thread{

    private GameManager game;
    public Vector coordinate;
    public Vector speed; //direction and intesity
    public boolean isHalf;
    public boolean isPotted;
    //Mass is equal for every ball
    

    public Ball(GameManager game, Vector coordinate, boolean isHalf) {
        this.game = game;
        this.coordinate = coordinate;
        this.isHalf = isHalf;

        this.speed = new Vector();
    }



    @Override
    public void run() {
       //Move balls

       //Detects collision with other balls
    }
}
