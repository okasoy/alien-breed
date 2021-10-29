package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

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

    public void teleportPlayer(Player player) {
        if(this.itsTarget == null) return;
        int x1, y1, x2, y2, x3, y3;
        x1 = this.getPosX() + this.getWidth() / 2;
        y1 = this.getPosY() + this.getHeight() / 2;
        x2 = this.itsTarget.getPosX() + this.itsTarget.getWidth() / 6;
        y2 = this.itsTarget.getPosY() + this.itsTarget.getHeight() / 6;
        x3 = player.getPosX() + player.getWidth() / 2;
        y3 = player.getPosY() + player.getHeight() / 2;
        if((x3 > x1 - this.getWidth()/2) && (y3 < y1 + this.getHeight()/2) && (x3 < x1 + this.getWidth()/2) && (y3 > y1 - this.getHeight()/2) ) {
            player.setPosition(x2, y2);
            this.itsTarget.isTeleported = true;
        }
    }

    public boolean wasTeleported(Player player){
        if(this.isTeleported == true){
            if(this.intersects(player)) return false;
            else this.isTeleported = false;
        }
        return true;
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        Player player = (Player) getScene().getFirstActorByName("Player");
        new Loop<>(
            new When<>(
                () -> this.wasTeleported(player) == true,
                new Invoke<>(this::teleportPlayer)
            )
        ).scheduleFor(player);
    }
}
