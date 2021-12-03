package sk.tuke.kpi.oop.game.switches;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Tunnel;
import sk.tuke.kpi.oop.game.LargeBox;

public class Switch extends AbstractActor implements Switching {
    private boolean switched;

    public Switch(){
        Animation switchAnimation = new Animation("sprites/switch.png");
        setAnimation(switchAnimation);
        this.switched = false;
    }

    @Override
    public void addActor(Actor actor) {
        if(actor == null) return;
        Tunnel tunnel = actor.getScene().getFirstActorByType(Tunnel.class);
        tunnel.changeAnimation(this.switched);
        if(this.switched == false){
            if(!tunnel.isDone()) {
                new When<>(
                    () -> tunnel.getAnimation().getCurrentFrameIndex() >= 7,
                    new Invoke<>(() -> actor.getScene().addActor(new LargeBox(), tunnel.getPosX() + 8, tunnel.getPosY() + 8))
                ).scheduleFor(this);
                tunnel.setDone(true);
            }
            this.switched = true;
        }
        else this.switched = false;
    }

}
