package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class FollowingRipley implements Behaviour<Movable> {
    private Disposable disposable;
    private Move<Movable> move;

    @Override
    public void setUp(Movable actor) {
        if (actor == null) return;
        this.disposable = new Loop<>(new Invoke<>(this::follow)).scheduleFor(actor);
    }

    public void follow(Movable actor) {
        int x = 0;
        int y = 0;
        Ripley ripley = actor.getScene().getFirstActorByType(Ripley.class);
        if(actor.getPosX() < ripley.getPosX()) x = 1;
        else if(actor.getPosX() > ripley.getPosX()) x = -1;
        if(actor.getPosY() < ripley.getPosY()) y = 1;
        else if(actor.getPosY() > ripley.getPosY()) y = -1;
        Direction direction = null;
        for(Direction value : Direction.values()){
            if(x == value.getDx() && y == value.getDy()){
                direction = value;
            }
        }
        if(this.move != null){
            this.move.stop();
            this.move = null;
        }
        if(direction != null) {
            actor.getAnimation().setRotation(direction.getAngle());
            this.move = new Move<>(direction, Float.MAX_VALUE);
            this.move.scheduleFor(actor);
        }
    }

}
