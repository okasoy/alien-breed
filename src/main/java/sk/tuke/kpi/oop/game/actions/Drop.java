package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;


public class Drop <A extends Keeper> extends AbstractAction<A> {
    @Override
    public void execute(float deltaTime) {
        if(this.getActor() == null || this.isDone() == true || this.getActor().getBackpack().getSize() == 0) return;
        Scene scene = this.getActor().getScene();
        Collectible item = this.getActor().getBackpack().peek();
        this.getActor().getBackpack().remove(item);
        scene.addActor(item, this.getActor().getPosX() + this.getActor().getWidth()/2 - item.getWidth()/2, this.getActor().getPosY() + this.getActor().getHeight()/2 - item.getHeight()/2);
        this.setDone(true);
    }
}
