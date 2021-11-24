package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

public class KeeperController implements KeyboardListener {
    private Keeper keeper;

    public KeeperController(Keeper keeper){
        this.keeper = keeper;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key){
        if(this.keeper == null) return;
        if(key == Input.Key.ENTER) new Take<>().scheduleFor(this.keeper);
        if(key == Input.Key.BACKSPACE) new Drop<>().scheduleFor(this.keeper);
        if(key == Input.Key.S) new Shift<>().scheduleFor(this.keeper);
        if(key == Input.Key.U) this.pressedU();
        if(key == Input.Key.B) this.pressedB();
    }

    public void pressedU(){
        for (Actor actor : this.keeper.getScene().getActors()) {
            if (actor instanceof Usable && this.keeper.intersects(actor)) {
                new Use<>((Usable<?>) actor).scheduleForIntersectingWith(this.keeper);
            }
        }
    }

    public void pressedB(){
        Collectible item = this.keeper.getBackpack().peek();
        if(item instanceof Usable && new Use<>((Usable<?>) item).scheduleForIntersectingWith(this.keeper) != null) this.keeper.getBackpack().remove(item);
    }
}
