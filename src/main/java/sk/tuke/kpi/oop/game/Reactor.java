package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable{
    private int temperature;
    private int damage;
    private boolean isOn;
    private Animation AnimationState;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation turnedOffAnimation;
    private Animation extinguisherAnimation;
    private Set<EnergyConsumer> devices;

    public Reactor(){
        this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.turnedOffAnimation = new Animation("sprites/reactor.png");
        this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        this.brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        this.extinguisherAnimation = new Animation("sprites/reactor_extinguished.png");
        turnOff();
        this.temperature = 0;
        this.damage = 0;
        devices = new HashSet<>();
    }

    public int getTemperature(){
        return this.temperature;
    }

    public int getDamage() {
        return this.damage;
    }

    private void turnOffDevices(EnergyConsumer device){
        device.setPowered(isOn());
    }

    private void updateAnimation(){
        if(this.temperature > 4000 && this.temperature < 6000) {
            this.AnimationState = this.hotAnimation;
            setAnimation(this.AnimationState);
        }
        if(this.temperature >= 6000){
            this.AnimationState = this.brokenAnimation;
            setAnimation(this.AnimationState);
            this.isOn = false;
            if(this.devices != null) this.devices.forEach(this::turnOffDevices);
        }
        if(this.damage < 100 && this.temperature <= 4000){
            this.AnimationState = this.normalAnimation;
            setAnimation(this.AnimationState);
        }
    }

    public void increaseTemperature(int increment){
        if(increment < 0 || this.isOn == false) return;
        int newIncrement = increment;
        if(this.damage >= 33 && this.damage <= 66) newIncrement = (int) Math.round(newIncrement * 1.5);
        else if(this.damage > 66) newIncrement = newIncrement * 2;
        this.temperature += newIncrement;
        if(this.temperature > 2000 && this.damage < 100){
            this.damage = (this.temperature - 2000) * 100 / 4000;
            if(this.damage > 100) this.damage = 100;
            updateAnimation();
        }
    }

    public void decreaseTemperature(int decrement){
        if(decrement < 0 || this.isOn == false) return;
        int newDecrement = decrement;
        if(this.damage >= 50) newDecrement = Math.round(newDecrement / 2);
        this.temperature -= newDecrement;
        updateAnimation();
    }

    @Override
    public boolean repair(){
        if(this.damage == 0 || this.damage == 100) return false;
        int newDamage = this.damage - 50;
        if(newDamage >= 0) this.damage = newDamage;
        else this.damage = 0;
        this.temperature = 2000 + newDamage * 4000 / 100;
        updateAnimation();
        return true;
    }

    @Override
    public boolean isOn(){
        return this.isOn;
    }

    @Override
    public void turnOn(){
        this.isOn = true;
        updateAnimation();
        if(this.devices != null) this.devices.forEach(this::turnOffDevices);
    }

    @Override
    public void turnOff(){
        this.isOn = false;
        this.AnimationState = this.turnedOffAnimation;
        setAnimation(AnimationState);
        if(this.devices != null) this.devices.forEach(this::turnOffDevices);
    }

    public void addDevice(EnergyConsumer device){
        this.devices.add(device);
        if(this.isOn == false) return;
        device.setPowered(true);
    }

    public void removeDevice(EnergyConsumer device){
        device.setPowered(false);
        this.devices.remove(device);
    }

    public boolean extinguish(){
        if(this.AnimationState != this.brokenAnimation) return false;
        this.temperature -= 4000;
        this.AnimationState = this.extinguisherAnimation;
        setAnimation(this.AnimationState);
        return true;
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        scene.scheduleAction(new PerpetualReactorHeating(1), this);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }

}
