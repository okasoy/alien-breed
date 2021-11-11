package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool<A extends Actor> extends AbstractActor implements Usable<A>{
    private int remainingUses;

    public BreakableTool(int remainingUses){
        this.remainingUses = remainingUses;
    }

    public int getRemainingUses() {
        return this.remainingUses;
    }

    @Override
    public void useWith(A actor) {
        if(this.remainingUses == 0) return;
        this.remainingUses -= 1;
        if(this.remainingUses == 0){
            this.getScene().removeActor(this);
        }
    }
}
