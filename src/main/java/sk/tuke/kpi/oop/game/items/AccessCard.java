package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class AccessCard extends AbstractActor implements Collectible, Usable<LockedDoor>{

    public AccessCard(){
        Animation accessCard = new Animation("sprites/key.png");
        setAnimation(accessCard);
    }

    @Override
    public void useWith(LockedDoor lockedDoor) {
        if(lockedDoor == null) return;
        lockedDoor.unlock();
    }

    @Override
    public Class<LockedDoor> getUsingActorClass() {
        return LockedDoor.class;
    }
}
