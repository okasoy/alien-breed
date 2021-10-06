package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor {
    private Animation LightIsOn;
    private Animation LightIsOff;
    private boolean isOn;
    private boolean isElectricity;

    public Light(){
        this.LightIsOn = new Animation("sprites/light_on.png");
        this.LightIsOff = new Animation("sprites/light_off.png");
        this.isOn = false;
        setAnimation(this.LightIsOff);
    }

    public void toggle(){
        if(this.isOn == true) {
            this.isOn = false;
            setAnimation(this.LightIsOff);
        }
        else{
            if(this.isElectricity == true) {
                this.isOn = true;
                setAnimation(this.LightIsOn);
            }
        }
    }

    public void setElectricityFlow(boolean electricityFlow){
        if(electricityFlow == true) this.isElectricity = true;
        else{
            this.isElectricity = false;
            setAnimation(this.LightIsOff);
        }
    }
}
