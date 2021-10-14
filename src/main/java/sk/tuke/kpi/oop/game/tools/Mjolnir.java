package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Mjolnir extends Hammer{
    private Animation animationState;

    public Mjolnir(){
        this.animationState = new Animation("sprites/hammer.png");
        setAnimation(this.animationState);
        super.remainingUses = 4;
    }
}
