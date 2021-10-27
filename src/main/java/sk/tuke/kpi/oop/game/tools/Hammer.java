package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;

public class Hammer extends BreakableTool<Repairable> {
    private Animation animationState;

    public Hammer(){
        super(1);
        this.animationState = new Animation("sprites/hammer.png");
        setAnimation(this.animationState);
    }
}
