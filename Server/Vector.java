public class Vector {
    public double angle;
    public double speed; //magnitude

    Vector(){
        angle = 0;
        speed = 0;
    }

    public Coord getCoord(){
        double dx = speed * Math.cos(angle);
        double dy = speed * Math.sin(angle);

        return new Coord(dx, dy);
    }
}