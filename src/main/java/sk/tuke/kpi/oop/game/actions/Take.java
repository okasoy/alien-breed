package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.List;

public class Take<A extends Keeper> extends AbstractAction<A> {
   @Override
    public void execute(float deltaTime) {
        if(getActor() == null || this.isDone() == true){
            this.setDone(true);
            return;
        }
        Scene scene = this.getActor().getScene();
        List<Actor> items= scene.getActors();
        for(Actor item : items){
            if(item instanceof Collectible && item.intersects(this.getActor())){
                try {
                    this.getActor().getBackpack().add((Collectible) item);
                    scene.removeActor(item);
                }
                catch (IllegalStateException exception) {
                    scene.getOverlay().drawText(exception.getMessage(), 0, 0).showFor(2);
                }
            }
        }
        this.setDone(true);
    }
}
