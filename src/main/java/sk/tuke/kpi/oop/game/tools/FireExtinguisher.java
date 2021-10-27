package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;

public class FireExtinguisher extends BreakableTool<Repairable> {
    private Animation fireExtinguisher;

    public FireExtinguisher(){
        super(1);
        this.fireExtinguisher = new Animation("sprites/extinguisher.png");
        setAnimation(this.fireExtinguisher);
    }
}
