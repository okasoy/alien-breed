package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
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
        if(player == null) return;
        player.setPosition(this.getPosX() + this.getWidth() / 6, this.getPosY() + this.getHeight() / 6);
        this.isTeleported = true;
    }

    public boolean wasTeleported(Player player){
        if(this.isTeleported == true){
            if(this.intersects(player)) return false;
            else this.isTeleported = false;
        }
        return true;
    }

    public void canTeleport(Player player){
        int x1, y1, x3, y3;
        x1 = this.getPosX() + this.getWidth() / 2;
        y1 = this.getPosY() + this.getHeight() / 2;
        x3 = player.getPosX() + player.getWidth() / 2;
        y3 = player.getPosY() + player.getHeight() / 2;
        if(this.wasTeleported(player) == true && (x3 > x1 - this.getWidth()/2) && (y3 < y1 + this.getHeight()/2) && (x3 < x1 + this.getWidth()/2) && (y3 > y1 - this.getHeight()/2) && this.itsTarget != null) this.itsTarget.teleportPlayer(player);
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        Player player = (Player) getScene().getFirstActorByName("Player");
        new Loop<>(new Invoke<>(this::canTeleport)).scheduleFor(player);
    }

}
