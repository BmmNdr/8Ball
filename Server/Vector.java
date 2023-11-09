public class Vector {
    public int x;
    public int y;
    public float m; // magnitude

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;

        this.m = 0;
    }

    public Vector() {
        this.x = 0;
        this.y = 0;
        this.m = 0;
    }
}
