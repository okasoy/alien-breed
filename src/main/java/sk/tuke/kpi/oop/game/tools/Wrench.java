package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;

public class Wrench extends BreakableTool<DefectiveLight> {

    public Wrench(){
        super(2);
        Animation wrenchAnimation = new Animation("sprites/wrench.png");
        setAnimation(wrenchAnimation);
    }
}