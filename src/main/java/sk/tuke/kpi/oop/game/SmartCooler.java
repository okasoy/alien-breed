package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler{
    private Reactor reactor;

    public SmartCooler(Reactor thisReactor) {
        super(thisReactor);
        this.reactor = thisReactor;
    }

    private void coolReactor(){
        if(this.reactor.getTemperature() > 2500) {
            this.turnOn();
        }
        if(this.reactor.getTemperature() < 1500){
            this.turnOff();
        }
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        new Invoke<>(this::coolReactor).scheduleFor(this);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }

}
