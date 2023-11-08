import java.util.List;

public class GameManager {
    Player player1 = null;
    Player player2 = null;
    List<Ball> balls;

    GameManager(){
        this.player1 = null;
        this.player2 = null;
    }

    public boolean setPlayer(Player p){
        if(player1 == null){
            player1 = p;
            return false;
        }
        
        player2 = p;
        return true; //if both players are sets
    }

    public void StartGame(){
        //Select random player to start

        //At every turn, get cue direction and force

        //move balls

        //next turn
    }
}