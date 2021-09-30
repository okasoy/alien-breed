package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Reactor extends AbstractActor {
    private int temperature;
    private int damage;
    private Animation AnimationState;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;

    public Reactor(){
        this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.AnimationState = this.normalAnimation;
        this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        this.brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(this.AnimationState);
        this.temperature = 0;
        this.damage = 0;
    }

    public int getTemperature(){
        return this.temperature;
    }

    public int getDamage() {
        return this.damage;
    }

    private void updateAnimation(Animation changeAnimation){
        this.AnimationState = changeAnimation;
        setAnimation(this.AnimationState);
    }

    public void increaseTemperature(int increment){
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
            if(this.temperature > 4000 && this.temperature < 6000){
                updateAnimation(this.hotAnimation);
            }
            if(this.temperature >= 6000){
                updateAnimation(this.brokenAnimation);
            }
        }
    }

    public void decreaseTemperature(int decrement){
        if(this.damage >= 50){
            double newDecrement = decrement / 2;
            decrement = (int) Math.round(newDecrement);
        }
        this.temperature -= decrement;
        if(this.damage < 100 && this.temperature <= 4000){
            updateAnimation(this.normalAnimation);
        }
    }
}
