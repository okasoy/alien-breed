package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class FireExtinguisher extends BreakableTool {
    private Animation fireExtinguisher;

    public FireExtinguisher(){
        super(1);
        this.fireExtinguisher = new Animation("sprites/extinguisher.png");
        setAnimation(this.fireExtinguisher);
    }
}
