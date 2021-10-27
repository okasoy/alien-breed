package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer{
    private Animation computerOffAnimation;
    private Animation computerOnAnimation;
    private boolean isOn;

    public Computer(){
        this.computerOnAnimation = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        this.computerOffAnimation = new Animation("sprites/computer.png", 80, 48, 0.0f, Animation.PlayMode.ONCE_REVERSED);
        setAnimation(computerOffAnimation);
        this.isOn = false;
    }

    public int add(int a, int b){
        if(this.isOn == false) return 0;
        return a + b;
    }

    public float add(float a, float b){
        if(this.isOn == false) return 0.0f;
        return a + b;
    }

    public int sub(int a, int b){
        if(this.isOn == false) return 0;
        return a - b;
    }

    public float sub(float a, float b){
        if(this.isOn == false) return 0.0f;
        return a - b;
    }

    @Override
    public void setPowered(boolean isPowered){
        if(isPowered == true){
            this.isOn = true;
            setAnimation(computerOnAnimation);
        }
        else{
            this.isOn = false;
            setAnimation(computerOffAnimation);
            this.add(0, 0);
            this.add(0.0f,0.0f);
            this.sub(0,0);
            this.sub(0.0f,0.0f);
        }
    }
}
