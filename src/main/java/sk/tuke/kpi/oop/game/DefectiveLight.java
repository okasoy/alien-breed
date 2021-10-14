package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light{

    public void lightsBehavior(){
        int max = 20;
        int min = 0;
        int range = max - min;
        int rand = (int)(Math.random()*range) + min;
        if(rand == 1){
            toggle();
        }
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        new Invoke<>(this::lightsBehavior).scheduleFor(this);
        new Loop<>(new Invoke<>(this::lightsBehavior)).scheduleFor(this);
    }

}
