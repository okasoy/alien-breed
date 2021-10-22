package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Hammer extends BreakableTool {
    private Animation animationState;

    public Hammer(){
        super(1);
        this.animationState = new Animation("sprites/hammer.png");
        setAnimation(this.animationState);
    }
}
