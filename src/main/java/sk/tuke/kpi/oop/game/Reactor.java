package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Reactor extends AbstractActor {
    private int temperature;
    private int damage;
    private boolean isOn;
    private Animation AnimationState;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation turnedOffAnimation;
    private Animation extinguisherAnimation;
    private Light itsLight;
    private int lightCounter;

    public Reactor(){
        this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.turnedOffAnimation = new Animation("sprites/reactor.png");
        this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        this.brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        this.extinguisherAnimation = new Animation("sprites/reactor_extinguished.png");
        turnOff();
        this.temperature = 0;
        this.damage = 0;
        this.lightCounter = 0;
    }

    public int getTemperature(){
        return this.temperature;
    }

    public int getDamage() {
        return this.damage;
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
            if(this.itsLight != null) this.itsLight.setElectricityFlow(this.isRunning());
        }
        if(this.damage < 100 && this.temperature <= 4000){
            this.AnimationState = this.normalAnimation;
            setAnimation(this.AnimationState);
        }
    }

    public void increaseTemperature(int increment){
        if(increment < 0 || this.isOn == false) return;
        if(this.damage >= 33 && this.damage <= 66){
            double newIncrement = increment * 1.5;
            increment = (int) Math.round(newIncrement);
        }
        else if(this.damage > 66){
            increment = increment * 2;
        }
        this.temperature += increment;
        if(this.temperature > 2000 && this.damage < 100){
            this.damage = (this.temperature - 2000) * 100 / 4000;
            updateAnimation();
        }
    }

    public void decreaseTemperature(int decrement){
        if(decrement < 0 || this.isOn == false) return;
        if(this.damage >= 50){
            double newDecrement = decrement / 2;
            decrement = (int) Math.round(newDecrement);
        }
        this.temperature -= decrement;
        updateAnimation();
    }

    public void repairWith(@NotNull Hammer hammer){
        if(this.damage == 0 || this.damage == 100) return;
        if(hammer.getUsage() == 0) return;
        hammer.use();
        int newDamage = this.damage - 50;
        if(newDamage >= 0) this.damage = newDamage;
        else this.damage = 0;
        this.temperature = 2000 + newDamage * 4000 / 100;
        updateAnimation();
    }

    public boolean isRunning(){
        return this.isOn;
    }

    public void turnOn(){
        this.isOn = true;
        updateAnimation();
    }

    public void turnOff(){
        this.isOn = false;
        this.AnimationState = this.turnedOffAnimation;
        setAnimation(AnimationState);
    }

    public void addLight(Light light){
        if(this.lightCounter > 1) return;
        this.lightCounter = 1;
        this.itsLight = light;
        this.itsLight.setElectricityFlow(this.isRunning());
        this.itsLight.toggle();
    }

    public void removeLight(){
        this.itsLight.setElectricityFlow(false);
        this.itsLight = null;
        this.lightCounter--;
    }

    public void extinguishWith(FireExtinguisher extinguisher){
        if(this.AnimationState != this.brokenAnimation) return;
        this.temperature -= 4000;
        this.AnimationState = this.extinguisherAnimation;
        setAnimation(this.AnimationState);
        extinguisher.use();
    }
}
