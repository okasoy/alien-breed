package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool extends AbstractActor {
    protected int remainingUses;

    public BreakableTool(int remainingUses){
        this.remainingUses = remainingUses;
    }

    public int getUsage() {
        return this.remainingUses;
    }

    public void use(){
        if(this.remainingUses == 0) return;
        this.remainingUses -= 1;
        if(this.remainingUses == 0){
            this.getScene().removeActor(this);
        }
    }
}
