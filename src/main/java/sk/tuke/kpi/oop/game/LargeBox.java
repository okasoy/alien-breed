package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.items.AccessCard;

public class LargeBox extends AbstractActor {
    private boolean used;
    private int attempts;

    public LargeBox(){
        Animation boxAnimation = new Animation("sprites/box_large.png");
        setAnimation(boxAnimation);
        this.used = false;
        this.attempts = 3;
    }

    public int getAttempts() {
        return this.attempts;
    }

    public void giveAccessCard(){
        if(this.used == true) return;
        this.used = true;
        this.getScene().addActor(new AccessCard(), this.getPosX(), this.getPosY());
        this.getScene().removeActor(this);
    }

    public void decreaseAttempts(){
        if(this.attempts == 0) return;
        this.attempts--;
        if(this.attempts == 0) this.giveAccessCard();
    }
}
