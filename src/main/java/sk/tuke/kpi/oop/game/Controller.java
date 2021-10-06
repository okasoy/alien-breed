package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Controller extends AbstractActor {
    private Reactor reactor;
    private Animation controllerAnimation;

    public Controller(Reactor thisReactor) {
        this.controllerAnimation = new Animation("sprites/switch.png");
        setAnimation(controllerAnimation);
        this.reactor = thisReactor;
    }

    public void toggle(){
        if(this.reactor.isRunning() == true) this.reactor.turnOff();
        else this.reactor.turnOn();
    }
}
