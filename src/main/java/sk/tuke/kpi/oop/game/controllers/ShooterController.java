package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.*;
import sk.tuke.kpi.oop.game.characters.Armed;

public class ShooterController implements KeyboardListener {
    private Armed actor;

    public ShooterController(Armed armed){
        this.actor = armed;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key){
        if(this.actor == null || this.actor.getFirearm() == null) return;
        if(key == Input.Key.SPACE) new Fire<>().scheduleFor(this.actor);
    }
}
