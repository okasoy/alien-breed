package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.Repairable;

public class Hammer extends BreakableTool<Repairable> {

    public Hammer(){
        this(1);
    }

    public Hammer(int uses){
        super(uses);
        Animation hammerAnimation = new Animation("sprites/hammer.png");
        setAnimation(hammerAnimation);
    }

    public void useWith(Reactor reactor){
        if(reactor == null) return;
        if(reactor.repair() == true) super.useWith(reactor);
    }
}
