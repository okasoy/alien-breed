package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Shift <A extends Keeper> extends AbstractAction<A> {
    private A ripley;

    public Shift(A ripley){
        this.ripley = ripley;
    }

    @Override
    public void execute(float deltaTime) {
        if(this.ripley == null || this.isDone() == true || this.ripley.getBackpack().getSize() == 0) return;
        this.ripley.getBackpack().shift();
        this.setDone(true);
    }
}
