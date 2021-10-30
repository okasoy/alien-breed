package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class ChainBomb extends TimeBomb {

    public ChainBomb(float deltaTime){
        super(deltaTime);
    }

    @Override
    public void explode(){
        super.explode();
        Ellipse2D.Float ellipse = new Ellipse2D.Float(this.getPosX() - 42, this.getPosY() - 58, 102, 102);
        List<Actor> bombs = getScene().getActors();
        for(Actor actor: bombs){
            if(actor instanceof ChainBomb && !((ChainBomb) actor).isActivated()){
                Rectangle2D.Float nextBomb = new Rectangle2D.Float(actor.getPosX() + 8, actor.getPosY() - 8, actor.getWidth(), actor.getHeight());
                if(ellipse.intersects(nextBomb)) ((ChainBomb) actor).activate();
            }
        }
    }
}
