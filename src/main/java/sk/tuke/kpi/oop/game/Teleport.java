package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Teleport extends AbstractActor {
    private Teleport itsTarget;
    private boolean isTeleported;

    public Teleport(Teleport target){
        Animation teleportAnimation = new Animation("sprites/lift.png");
        setAnimation(teleportAnimation);
        this.itsTarget = target;
        this.isTeleported = false;
    }

    public Teleport getDestination(){
        return this.itsTarget;
    }

    public void setDestination(Teleport destinationTeleport){
        if(destinationTeleport == this) return;
        this.itsTarget = destinationTeleport;
    }

    public void teleportPlayer(Ripley ripley) {
        if(ripley == null) return;
        ripley.setPosition(this.getPosX() + this.getWidth() / 6, this.getPosY() + this.getHeight() / 6);
        this.isTeleported = true;
    }

    public boolean wasTeleported(Ripley ripley){
        if(this.isTeleported == true){
            if(this.intersects(ripley)) return false;
            else this.isTeleported = false;
        }
        return true;
    }

    public void canTeleport(Ripley ripley){
        int x1, y1, x3, y3;
        x1 = this.getPosX() + this.getWidth() / 2;
        y1 = this.getPosY() + this.getHeight() / 2;
        x3 = ripley.getPosX() + ripley.getWidth() / 2;
        y3 = ripley.getPosY() + ripley.getHeight() / 2;
        if(this.wasTeleported(ripley) == true && (x3 > x1 - this.getWidth()/2) && (y3 < y1 + this.getHeight()/2) && (x3 < x1 + this.getWidth()/2) && (y3 > y1 - this.getHeight()/2) && this.itsTarget != null) this.itsTarget.teleportPlayer(ripley);
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        Ripley ripley = getScene().getFirstActorByType(Ripley.class);
        if(ripley != null) new Loop<>(new Invoke<>(this::canTeleport)).scheduleFor(getScene().getFirstActorByType(Ripley.class));
    }
}
