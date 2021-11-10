package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

public class Ripley extends AbstractActor implements Movable {
    private int speed;
    private Animation ripleyAnimation;

    public Ripley(){
        super("Ellen");
        this.ripleyAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(this.ripleyAnimation);
        this.ripleyAnimation.pause();
        this.speed = 2;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        this.ripleyAnimation.setRotation(direction.getAngel());
        this.ripleyAnimation.play();
    }

    @Override
    public void stoppedMoving() {
        this.ripleyAnimation.pause();
    }
}
