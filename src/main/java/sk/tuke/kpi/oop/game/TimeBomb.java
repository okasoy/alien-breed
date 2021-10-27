package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {
    private Animation bombActivatedAnimation;
    private Animation explosionAnimation;
    private float time;
    private boolean activated;

    public TimeBomb(float deltaTime){
        Animation timeBombAnimation = new Animation("sprites/bomb.png");
        this.bombActivatedAnimation = new Animation("sprites/bomb_activated.png", 16, 16, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        this.explosionAnimation = new Animation("sprites/small_explosion.png",16,16,0.1f, Animation.PlayMode.ONCE);
        setAnimation(timeBombAnimation);
        this.time = deltaTime;
        this.activated = false;
    }

    public void activate(){
        this.activated = true;
        setAnimation(this.bombActivatedAnimation);
        new ActionSequence<>(
            new Wait<>(this.time),
            new Invoke<>(this::explode)
        ).scheduleFor(this);
        new When<>(
            () -> this.explosionAnimation.getCurrentFrameIndex() >= 7,
            new Invoke<>(() -> this.getScene().removeActor(this))
        ).scheduleFor(this);
    }

    public boolean isActivated(){
        return this.activated;
    }

    public void explode(){
        setAnimation(this.explosionAnimation);
    }
}
