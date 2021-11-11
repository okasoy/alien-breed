package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;

public class Ripley extends AbstractActor implements Movable, Keeper {
    private int speed;
    private int energy;
    private int ammo;
    private Animation ripleyAnimation;
    private Backpack backpack;

    public Ripley(){
        super("Ellen");
        this.ripleyAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(this.ripleyAnimation);
        this.ripleyAnimation.pause();
        this.speed = 2;
        this.energy = 100;
        this.ammo = 0;
        this.backpack = new Backpack("Ripley's backpack", 10);
    }

    public int getEnergy(){
        return this.energy;
    }

    public void setEnergy(int energy) {
        if(energy > 100) return;
        this.energy = energy;
    }

    public int getAmmo(){
        return this.ammo;
    }

    public void setAmmo(int Ammo){
        if(Ammo > 500) return;
        this.ammo = Ammo;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        this.ripleyAnimation.setRotation(direction.getAngel());
        this.ripleyAnimation.play();
    }

    @Override
    public void stoppedMoving() {
        this.ripleyAnimation.pause();
    }

    @Override
    public Backpack getBackpack() {
        return this.backpack;
    }
}
