package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable{
    private Disposable disposedLight;
    private boolean isRepaired;

    public DefectiveLight(){
        super();
        this.isRepaired = false;
    }

    public void lightsBehavior(){
        this.isRepaired = false;
        int max = 20;
        int min = 0;
        int range = max - min;
        int rand = (int)(Math.random()*range) + min;
        if(rand == 1){
            toggle();
        }
    }

    @Override
    public boolean repair(){
        if(this.isRepaired == true) return false;
        this.isRepaired = true;
        this.disposedLight.dispose();
        super.turnOn();
        this.disposedLight = new ActionSequence<>(
            new Wait<>(10),
            new Loop<>(new Invoke<>(this::lightsBehavior))
        ).scheduleFor(this);
        return true;
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        this.disposedLight = new Loop<>(new Invoke<>(this::lightsBehavior)).scheduleFor(this);
    }

}
