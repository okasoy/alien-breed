package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;

public class Wrench extends BreakableTool<DefectiveLight> {
    private Animation wrenchAnimation;

    public Wrench(){
        super(2);
        this.wrenchAnimation = new Animation("sprites/wrench.png");
        setAnimation(this.wrenchAnimation);
    }

}
