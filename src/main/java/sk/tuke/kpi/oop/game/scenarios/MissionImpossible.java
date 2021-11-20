package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

import java.util.Objects;

public class MissionImpossible implements SceneListener {

    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if(Objects.equals(name, "access card")) return new AccessCard();
            else if(Objects.equals(name, "door")) return new LockedDoor();
            else if(Objects.equals(name, "ellen")) return new Ripley();
            else if(Objects.equals(name, "energy")) return new Energy();
            else if(Objects.equals(name, "locker")) return new Locker();
            else if(Objects.equals(name, "ventilator")) return new Ventilator();
            return null;
        }
    }

    @Override
    public void sceneInitialized(Scene scene){
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        scene.follow(ripley);
        MovableController movableController = new MovableController(ripley);
        Disposable movable = scene.getInput().registerListener(movableController);
        KeeperController keeperController = new KeeperController(ripley);
        Disposable keeper = scene.getInput().registerListener(keeperController);
        scene.getGame().pushActorContainer(ripley.getBackpack());
        Ventilator ventilator = scene.getFirstActorByType(Ventilator.class);
        new ActionSequence<>(
            new Wait<>(7),
            new Invoke<>(() -> ventilator.setBroken(true))
        ).scheduleFor(ventilator);
        new ActionSequence<>(
            new Wait<>(7),
            new Invoke<>(() -> ventilator.getAnimation().pause())
        ).scheduleFor(ventilator);
        scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Ripley) -> new Invoke<>(ripley::decreaseEnergy).scheduleFor(ripley));
        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, (Ripley) -> ripley.stopDecreasingEnergy().dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> movable.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keeper.dispose());
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        SceneListener.super.sceneUpdating(scene);
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        if(ripley != null) ripley.showRipleyState();
    }
}
