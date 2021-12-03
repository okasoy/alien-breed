package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Electricity extends AbstractActor {
    private Animation electricityAnimation;

    public Electricity(){
        this.electricityAnimation = new Animation("sprites/electricity.png", 16, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(this.electricityAnimation);
    }

    public void decreaseEnergy(){
        Ripley ripley = this.getScene().getFirstActorByType(Ripley.class);
        if(ripley == null || ripley.getHealth().getValue() == 0) return;
        if(this.electricityAnimation.getCurrentFrameIndex() >= 3 && ripley.intersects(this)) ripley.getHealth().drain(1);
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        if(ripley != null) new Loop<>(new Invoke<>(this::decreaseEnergy)).scheduleFor(ripley);
    }

}
