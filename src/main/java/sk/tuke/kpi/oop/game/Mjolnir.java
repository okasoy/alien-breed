package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Mjolnir extends Hammer{
    private Animation animationState;
    private int usage;

    public Mjolnir(){
        this.animationState = new Animation("sprites/hammer.png");
        setAnimation(this.animationState);
        this.usage = 4;
    }

    public int getUsage() {
        return this.usage;
    }

    public void use(){
        if(this.usage == 0) return;
        this.usage -= 1;
        if(this.usage == 0){
            this.getScene().removeActor(this);
        }
    }
}
