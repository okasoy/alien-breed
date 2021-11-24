package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.List;

public class Alien extends AbstractActor implements Movable, Alive, Enemy {
    private Animation alien;
    private Health health;
    private Behaviour<? super Alien> behaviour;

    public Alien(){
        this.alien = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(this.alien);
        this.health = new Health(100);
        this.health.onExhaustion(() -> {this.getScene().removeActor(this);});
    }

    public Alien(Behaviour<? super Alien> behaviour){
        this.alien = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(this.alien);
        this.health = new Health(100);
        this.behaviour = behaviour;
        this.health.onExhaustion(() -> {this.getScene().removeActor(this);});
    }

    @Override
    public int getSpeed() {
        return 2;
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        if(this.behaviour != null) this.behaviour.setUp(this);
        new Loop<>(
            new ActionSequence<>(
                new Wait<>(0.5f),
                new Invoke<>(this::decreaseEnergy)
            )
        ).scheduleFor(this);
    }

    public void decreaseEnergy() {
        if (getScene() == null) return;
        List<Actor> aliveActors = getScene().getActors();
        for (Actor aliveActor : aliveActors) {
            if (aliveActor instanceof Alive && !(aliveActor instanceof Enemy) && aliveActor.intersects(this)) {
                new Invoke<>(() -> ((Alive) aliveActor).getHealth().drain(3)).scheduleFor(this);
            }
        }
    }

}
