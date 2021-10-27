package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {

    public Helicopter(){
        Animation helicopterAnimation = new Animation("sprites/heli.png",64,64,0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(helicopterAnimation);
        super.addedToScene(this.getScene());
    }

    public void searchAndDestroy(){
        new Loop<>(new Invoke<>(this::following)).scheduleFor(this);
    }

    private void following() {
        Player player = (Player) getScene().getFirstActorByName("Player");
        if(player.getPosX() < this.getPosX()) this.setPosition(getPosX()-1, getPosY());
        if(player.getPosX() > this.getPosX()) this.setPosition(getPosX()+1, getPosY());
        if(player.getPosY() < this.getPosY()) this.setPosition(getPosX(), getPosY()-1);
        if(player.getPosY() > this.getPosY()) this.setPosition(getPosX(), getPosY()+1);
        if(this.intersects(player) == true) player.setEnergy(player.getEnergy()-1);
    }
}
