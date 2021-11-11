package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.items.Usable;

public class Use<A extends AbstractActor> extends AbstractAction<A> {
    private Usable<A> usableActor;

    public Use(Usable<A> item){
        this.usableActor = item;
    }

    @Override
    public void execute(float deltaTime) {
        if(this.usableActor == null || this.isDone() == true) return;
        this.usableActor.useWith(getActor());
        this.setDone(true);
    }
}
