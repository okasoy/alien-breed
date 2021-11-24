package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class AlienMother extends Alien{
    private Health health;

    public AlienMother(){
        Animation mother = new Animation("sprites/mother.png", 112, 162, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(mother);
        this.health = new Health(200);
    }

    public AlienMother(Behaviour<? super Alien> behaviour){
        super(behaviour);
        Animation mother = new Animation("sprites/mother.png", 112, 162, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(mother);
        this.health = new Health(200);
    }
}
