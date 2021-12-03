package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Tunnel extends AbstractActor {
    private Animation closed;
    private Animation opened;
    private boolean isDone;

    public Tunnel(){
        this.closed = new Animation("sprites/tunnel_transparent.png", 32, 32,0.1f, Animation.PlayMode.ONCE_REVERSED);
        this.opened = new Animation("sprites/tunnel_transparent.png", 32, 32,0.1f, Animation.PlayMode.ONCE);
        this.setAnimation(this.closed);
        this.isDone = false;
    }

    public boolean isDone(){
        return this.isDone;
    }

    public void setDone(boolean isDone){
        this.isDone = isDone;
    }

    public void changeAnimation(boolean switched){
        if(switched == true){
            this.getAnimation().resetToFirstFrame();
            this.setAnimation(this.closed);
        }
        else{
            this.getAnimation().resetToFirstFrame();
            this.setAnimation(this.opened);
        }
    }

}
