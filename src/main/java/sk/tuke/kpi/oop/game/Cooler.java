package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable{
    private Reactor reactor;
    private Animation coolerOnAnimation;
    private Animation coolerOffAnimation;
    private boolean isOn;

    public Cooler(Reactor thisReactor){
        this.reactor = thisReactor;
        this.coolerOnAnimation = new Animation("sprites/fan.png",32, 32, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        this.coolerOffAnimation = new Animation("sprites/fan.png", 32, 32, 0.0f, Animation.PlayMode.ONCE_REVERSED);
        turnOff();
    }

    @Override
    public void turnOn(){
        this.isOn = true;
        setAnimation(this.coolerOnAnimation);
    }

    @Override
    public void turnOff(){
        this.isOn = false;
        setAnimation(this.coolerOffAnimation);
    }

    @Override
    public boolean isOn(){
        return this.isOn;
    }

    private void coolReactor(){
        if(this.isOn == false || this.reactor == null) return;
        //if(this.reactor.getTemperature() > 0)
            this.reactor.decreaseTemperature(1);
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }

}
