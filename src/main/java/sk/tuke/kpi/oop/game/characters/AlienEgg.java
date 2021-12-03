package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.openables.StrongDoor;

public class AlienEgg extends AbstractActor implements Alive, Enemy {
    private Health health;
    private Animation eggAnimation;

    public AlienEgg(int health){
        this.eggAnimation = new Animation("sprites/alien_egg.png", 32, 32, 0.5f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(this.eggAnimation);
        this.eggAnimation.pause();
        this.health = new Health(health);
        this.health.onExhaustion(() -> {this.getScene().removeActor(this);});
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    public void addAlien(){
        if(this.getScene() == null || this.health.getValue() == 0) return;
        this.eggAnimation.play();
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() -> this.getScene().addActor(new Alien(40, new RandomlyMoving()), this.getPosX(), this.getPosY())),
                new Wait<>(3)
            )
        ).scheduleFor(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        StrongDoor strongDoor = scene.getFirstActorByType(StrongDoor.class);
        new When<>(
            () -> strongDoor != null && strongDoor.isOpen() == true,
            new Invoke<>(this::addAlien)
        ).scheduleFor(this);
    }
}
