package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Movable actor;
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST)
    );
    private Move<Movable> move;
    private Set<Direction> directions;

    public MovableController(Movable movable){
        this.actor = movable;
        this.directions = new HashSet<>();
    }

    @Override
    public void keyPressed(Input.@NotNull Key key){
        if(keyDirectionMap.containsKey(key) == false) return;
        if(keyDirectionMap.get(key) == null) return;
        if(this.move != null) this.move.stop();
        Direction direction = this.keyDirectionMap.get(key);
        this.directions.add(direction);
        if(this.directions.size() > 1){
            Direction[] other = this.directions.toArray(new Direction[this.directions.size()]);
            if(direction == other[1]) direction = direction.combine(other[0]);
            else direction = direction.combine(other[1]);
        }
        this.move = new Move<>(direction, Float.MAX_VALUE);
        this.move.scheduleFor(this.actor);
    }

    @Override
    public void keyReleased(Input.@NotNull Key key){
        if(keyDirectionMap.containsKey(key) == false) return;
        if(this.move == null) return;
        this.directions.clear();
        this.move.stop();
        this.move = null;
    }
}


