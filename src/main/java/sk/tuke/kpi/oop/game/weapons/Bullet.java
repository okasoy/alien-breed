package sk.tuke.kpi.oop.game.weapons;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.List;

public class Bullet extends AbstractActor implements Fireable {
    private Animation bulletAnimation;
    private int speed;

    public Bullet(){
        this.bulletAnimation = new Animation("sprites/bullet.png");
        setAnimation(this.bulletAnimation);
        this.speed = 4;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        if(direction == null || direction == Direction.NONE) return;
        this.bulletAnimation.setRotation(direction.getAngle());
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::decreaseEnergy)).scheduleFor(this);
    }

    public void decreaseEnergy() {
        if (getScene() == null) return;
        List<Actor> aliveActors = getScene().getActors();
        for (Actor aliveActor : aliveActors) {
            if (aliveActor instanceof Alive && !(aliveActor instanceof Ripley)  && aliveActor.intersects(this)) {
                ((Alive) aliveActor).getHealth().drain(10);
                this.collidedWithWall();
            }
        }
    }

    @Override
    public void collidedWithWall() {
        getScene().removeActor(this);
    }

}
