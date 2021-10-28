package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer{
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

    @Override
    public boolean isOn() {
        return this.isOn;
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        if(this.isElectricity == true) setAnimation(this.LightIsOn);
        else setAnimation(this.LightIsOff);
    }

    @Override
    public void turnOff() {
        this.isOn = false;
        setAnimation(this.LightIsOff);
    }

    public void toggle(){
        if(this.isOn == true) turnOff();
        else{
            //if(this.isElectricity == true) {
                turnOn();
            //}
        }
    }

    public void setElectricityFlow(boolean electricityFlow){
        if(electricityFlow == true) this.isElectricity = true;
        else{
            this.isElectricity = false;
            setAnimation(this.LightIsOff);
        }
    }

    @Override
    public void setPowered(boolean isPowered) {
        setElectricityFlow(isPowered);
        toggle();
    }
}
