package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;

public class Ventilator extends AbstractActor implements Repairable {
    boolean broken;
    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("ventilator repaired", Ventilator.class);

    public Ventilator(){
        Animation ventilator = new Animation("sprites/ventilator.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(ventilator);
        this.broken = false;
    }

    public void setBroken(boolean broken){
        this.broken = broken;
    }

    @Override
    public boolean repair() {
        if(this.broken = false) return false;
        this.broken = false;
        getAnimation().play();
        getScene().getMessageBus().publish(VENTILATOR_REPAIRED, this);
        return true;
    }
}
