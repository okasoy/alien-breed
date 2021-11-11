package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class FireExtinguisher extends BreakableTool<Reactor> implements Collectible{

    public FireExtinguisher(){
        super(1);
        Animation fireExtinguisherAnimation = new Animation("sprites/extinguisher.png");
        setAnimation(fireExtinguisherAnimation);
    }

    public void useWith(Reactor reactor){
        if(reactor == null) return;
        if(reactor.extinguish() == true) super.useWith(reactor);
    }
}
