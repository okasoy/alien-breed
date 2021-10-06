package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Hammer extends AbstractActor {
    private Animation animationState;
    private int usage;

    public Hammer(){
        this.animationState = new Animation("sprites/hammer.png");
        setAnimation(this.animationState);
        this.usage = 1;
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
