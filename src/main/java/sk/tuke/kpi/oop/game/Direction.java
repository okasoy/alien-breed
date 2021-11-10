package sk.tuke.kpi.oop.game;

public enum Direction {
    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0);
    private final int dx;
    private final int dy;

    Direction(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public float getAngel(){
        if(this.dx == -1){
            if(this.dy == -1) return 135;
            else if(this.dy == 0) return 90;
            else if(this.dy == 1) return 45;
        }
        else if(this.dx == 0){
            if(this.dy == -1) return 180;
            else return 0;
        }
        else if(this.dx == 1){
            if(this.dy == -1) return 225;
            else if(this.dy == 0) return 270;
            else if(this.dy == 1) return 315;
        }
        return 0;
    }
}
