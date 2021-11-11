package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Energy<A extends Ripley> extends AbstractActor implements Usable<A>{

    public Energy(){
        Animation energyAnimation = new Animation("sprites/energy.png");
        setAnimation(energyAnimation);
    }

    @Override
    public void useWith(A actor) {
        if(actor == null || actor.getEnergy() == 100) return;
        actor.setEnergy(100);
        this.getScene().removeActor(this);
    }
}
