package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Wrench extends BreakableTool{
    private Animation wrenchAnimation;

    public Wrench(){
        super(2);
        this.wrenchAnimation = new Animation("sprites/wrench.png");
        setAnimation(this.wrenchAnimation);
    }

}
