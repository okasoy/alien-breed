package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Map;

public class MovableController implements KeyboardListener {
    private Movable actor;
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST)
    );
    private Move<Movable> move;

    public MovableController(Movable movable){
        this.actor = movable;
    }

    @Override
    public void keyPressed(Input.@NotNull Key key){
        if(keyDirectionMap.containsKey(key) == false) return;
        if(keyDirectionMap.get(key) == null) return;
        if(this.move != null) this.move.stop();
        this.move = new Move<>(this.keyDirectionMap.get(key), Float.MAX_VALUE);
        this.move.scheduleFor(this.actor);
    }

    @Override
    public void keyReleased(Input.@NotNull Key key){
        if(keyDirectionMap.containsKey(key) == false) return;
        if(this.move == null) return;
        this.move.stop();
        this.move = null;
    }
}
