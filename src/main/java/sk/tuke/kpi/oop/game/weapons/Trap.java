package sk.tuke.kpi.oop.game.weapons;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.List;

public class Trap extends AbstractActor {

    public Trap(){
        Animation trapAnimation = new Animation("sprites/trap_system.png");
        setAnimation(trapAnimation);
    }

    public void catchAliens(){
        if(getScene() == null) return;
        List<Actor> aliveActors = getScene().getActors();
        for(Actor aliveActor : aliveActors) {
            if(aliveActor instanceof Alive && !(aliveActor instanceof Ripley) && this.intersectsActor(aliveActor)){
                Alien alien = (Alien) aliveActor;
                this.getScene().cancelActions(alien);
            }
        }
    }

    public boolean intersectsActor(Actor actor){
        int x1, y1, x3, y3;
        x1 = this.getPosX() + this.getWidth() / 2;
        y1 = this.getPosY() + this.getHeight() / 2;
        x3 = actor.getPosX() + actor.getWidth() / 2;
        y3 = actor.getPosY() + actor.getHeight() / 2;
        if((x3 > x1 - (this.getWidth()/2 + 5)) && (y3 < y1 + (this.getHeight()/2 + 5)) && (x3 < x1 + (this.getWidth()/2 + 5)) && (y3 > y1 - (this.getHeight()/2) + 5)) return true;
        return false;
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::catchAliens)).scheduleFor(this);
    }
}
