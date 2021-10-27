package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool<A extends Actor> extends AbstractActor implements Usable<A>{
    private int remainingUses;

    public BreakableTool(int remainingUses){
        this.remainingUses = remainingUses;
    }

    public int getUsage() {
        return this.remainingUses;
    }

    @Override
    public void useWith(Actor actor) {
        if(this.remainingUses == 0) return;
        this.remainingUses -= 1;
        if(this.remainingUses == 0){
            this.getScene().removeActor(this);
        }
    }
}
