package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

public class RandomlyMoving implements Behaviour<Movable>{

    @Override
    public void setUp(Movable actor) {
        if(actor == null) return;
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::moveRandomly),
                new Wait<>(2))
        ).scheduleFor(actor);
    }

    public void moveRandomly(Movable actor){
        int x = (int)(Math.random() * (3)) - 1;
        int y = (int)(Math.random() * (3)) - 1;
        Direction direction = null;
        for(Direction value : Direction.values()){
            if(x == value.getDx() && y == value.getDy()){
                direction = value;
            }
        }
        actor.getAnimation().setRotation(direction.getAngle());
        new Move<>(direction, 1).scheduleFor(actor);
    }
}
