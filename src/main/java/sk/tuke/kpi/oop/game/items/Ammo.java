package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Ammo extends AbstractActor implements Usable<Ripley>{

    public Ammo(){
        Animation ammoAnimation = new Animation("sprites/ammo.png");
        setAnimation(ammoAnimation);
    }

    @Override
    public void useWith(Ripley actor) {
        if(actor == null || actor.getAmmo() > 500) return;
        actor.setAmmo(actor.getAmmo() + 50);
        if(actor.getAmmo() > 500) actor.setAmmo(500);
        this.getScene().removeActor(this);
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
