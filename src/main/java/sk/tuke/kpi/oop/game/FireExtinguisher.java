package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class FireExtinguisher extends AbstractActor {
    private int usage;
    private Animation fireExtinguisher;

    public FireExtinguisher(){
        this.fireExtinguisher = new Animation("sprites/extinguisher.png");
        setAnimation(this.fireExtinguisher);
        this.usage = 1;
    }

    public int getUsage(){
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
