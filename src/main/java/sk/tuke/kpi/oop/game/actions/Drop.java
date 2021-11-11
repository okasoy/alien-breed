package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;


public class Drop <A extends Keeper> extends AbstractAction<A> {
    private A ripley;

    public Drop(A ripley){
        this.ripley = ripley;
    }

    @Override
    public void execute(float deltaTime) {
        if(this.ripley == null || this.isDone() == true || this.ripley.getBackpack().getSize() == 0) return;
        Scene scene = this.ripley.getScene();
        Collectible item = this.ripley.getBackpack().peek();
        this.ripley.getBackpack().remove(item);
        scene.addActor(item, this.ripley.getPosX() + this.ripley.getWidth()/2 - item.getWidth()/2, this.ripley.getPosY() + this.ripley.getHeight()/2 - item.getHeight()/2);
        this.setDone(true);
    }
}
