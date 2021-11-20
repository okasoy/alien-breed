package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

public class Move<A extends Movable> implements Action<A> {
    private A actor;
    private Direction Direction;
    private float Duration;
    private boolean isDone;
    private boolean check;
    private float timePassed;

    public Move(Direction direction, float duration) {
        this.Direction = direction;
        this.Duration = duration;
        this.isDone = false;
        this.check = false;
        this.timePassed = 0;
    }
    public Move(Direction direction){
        this.Direction = direction;
        this.Duration = 0;
        this.isDone = false;
        this.check = false;
        this.timePassed = 0;
    }

    @Override
    public @Nullable A getActor() {
        return this.actor;
    }

    @Override
    public void setActor(@Nullable A actor) {
        this.actor = actor;
    }

    @Override
    public boolean isDone() {
        return this.isDone;
    }

    @Override
    public void execute(float deltaTime) {
        if(this.actor == null) return;
        if(this.isDone == true) return;
        if(this.check == false){
            this.check = true;
            this.actor.startedMoving(this.Direction);
        }
        this.timePassed += deltaTime;
        if(this.timePassed - this.Duration >= 1e-5){
            this.stop();
        }
        else{
            this.actor.setPosition(this.actor.getPosX() + this.Direction.getDx() * this.actor.getSpeed(), this.actor.getPosY() + this.Direction.getDy() * this.actor.getSpeed());
            if (this.actor.getScene().getMap().intersectsWithWall(this.actor)) this.actor.setPosition(this.actor.getPosX() - this.Direction.getDx() * this.actor.getSpeed(), this.actor.getPosY() - this.Direction.getDy() * this.actor.getSpeed());
        }
    }

    @Override
    public void reset() {
        this.isDone = false;
        this.timePassed = 0;
        this.check = false;
    }

    public void stop(){
        this.isDone = true;
        this.actor.stoppedMoving();
        this.check = false;
    }
}
