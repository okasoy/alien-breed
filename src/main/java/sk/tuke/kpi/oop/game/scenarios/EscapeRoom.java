package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.Objects;

public class EscapeRoom implements SceneListener {

    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if (Objects.equals(name, "alien")) {
                if(Objects.equals(type, "running")) return new Alien(new RandomlyMoving());
                else if(Objects.equals(type, "waiting1")) return new Alien(new Observing<>(Door.DOOR_OPENED, door -> door.getName().equals("front door"), new RandomlyMoving()));
                else if(Objects.equals(type, "waiting2")) return new Alien(new Observing<>(Door.DOOR_OPENED, door -> door.getName().equals("back door"), new RandomlyMoving()));
            }
            else if (Objects.equals(name, "ammo")) return new Ammo();
            else if (Objects.equals(name, "ellen")) return new Ripley();
            else if (Objects.equals(name, "energy")) return new Energy();
            else if (Objects.equals(name, "alien mother")) return new AlienMother(new Observing<>(Door.DOOR_OPENED, door -> door.getName().equals("back door"), new RandomlyMoving()));
            else if (Objects.equals(name, "front door") || Objects.equals(name, "exit door")) return new Door(name, Door.Orientation.VERTICAL);
            else if (Objects.equals(name, "back door")) return new Door(name, Door.Orientation.HORIZONTAL);
            return null;
        }
    }

    @Override
    public void sceneInitialized(Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        if(ripley != null) scene.follow(ripley);
        MovableController movableController = new MovableController(ripley);
        Disposable movable = scene.getInput().registerListener(movableController);
        KeeperController keeperController = new KeeperController(ripley);
        Disposable keeper = scene.getInput().registerListener(keeperController);
        scene.getGame().pushActorContainer(ripley.getBackpack());
        ShooterController shooterController = new ShooterController(ripley);
        Disposable shooter = scene.getInput().registerListener(shooterController);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        SceneListener.super.sceneUpdating(scene);
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        if (ripley != null) ripley.showRipleyState();
    }

}
