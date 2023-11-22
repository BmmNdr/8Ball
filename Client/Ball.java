// Ball.java
public class Ball {
    public int number;
    public int x;
    public int y;
    public String type; // 'half', 'full', or 'not chosen'

    public Ball(int number, int x, int y) {
        this.number = number;
        this.x = x;
        this.y = y;
    }

    public Ball(int number, int x, int y, String type) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }
}