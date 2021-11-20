package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public class LockedDoor extends Door{
    private boolean isLocked;

    public LockedDoor(){
        super();
        this.isLocked = true;
    }

    public void lock(){
        this.isLocked = true;
        super.close();
    }

    public void unlock(){
        this.isLocked = false;
        super.open();
    }

    boolean isLocked(){
        return this.isLocked;
    }

    @Override
    public void useWith(Actor actor) {
        if (isLocked() == true) return;
        super.useWith(actor);
    }
}
