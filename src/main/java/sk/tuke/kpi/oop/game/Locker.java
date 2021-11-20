package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Usable;

public class Locker extends AbstractActor implements Usable<Ripley> {
    boolean used;

    public Locker(){
        Animation locker = new Animation("sprites/locker.png");
        setAnimation(locker);
        this.used = false;
    }

    @Override
    public void useWith(Ripley actor) {
        if(this.used == true) return;
        this.used = true;
        getScene().addActor(new Hammer(), this.getPosX(), this.getPosY());
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
