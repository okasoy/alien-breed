package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.openables.StrongDoor;

public class Button extends AbstractActor {

    public Button(){
        Animation red = new Animation("sprites/button_red.png");
        setAnimation(red);
    }

    public void openDoor(){
        StrongDoor strongDoor = getScene().getFirstActorByType(StrongDoor.class);
        if(strongDoor == null || strongDoor.isOpen()) return;
        Animation green = new Animation("sprites/button_green.png");
        setAnimation(green);
        strongDoor.open();
    }

}
