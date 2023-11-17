public class Coord {
    public double x;
    public double y;
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public Coord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Coord c){
        return Math.sqrt(Math.pow(x - c.x, 2) + Math.pow(y - c.y, 2));
    }
}
