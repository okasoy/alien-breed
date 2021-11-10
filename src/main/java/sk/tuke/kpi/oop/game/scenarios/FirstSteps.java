package sk.tuke.kpi.oop.game.scenarios;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.MovableController;

public class FirstSteps implements SceneListener {

    @Override
    public void sceneInitialized(Scene scene){
        Ripley ripley = new Ripley();
        scene.addActor(ripley, 0, 0);
        /*new Move(Direction.WEST, 1f).scheduleFor(ripley);
        new ActionSequence<>(
            new Wait<>(1),
            new Move(Direction.NORTH, 1f)
        ).scheduleFor(ripley);*/
        MovableController movableController = new MovableController(ripley);
        scene.getInput().registerListener(movableController);
    }
}
