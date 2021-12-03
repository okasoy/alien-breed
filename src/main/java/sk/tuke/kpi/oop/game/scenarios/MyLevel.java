package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.*;
import sk.tuke.kpi.oop.game.behaviours.FollowingRipley;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.*;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;
import sk.tuke.kpi.oop.game.openables.StrongDoor;
import sk.tuke.kpi.oop.game.switches.FloorSwitch;
import sk.tuke.kpi.oop.game.switches.Switch;
import sk.tuke.kpi.oop.game.weapons.Trap;

import java.util.Map;
import java.util.Objects;

public class MyLevel implements SceneListener {

    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if(Objects.equals(name, "access card")) return new sk.tuke.kpi.oop.game.items.AccessCard();
            else if(Objects.equals(name, "hdoor") || Objects.equals(name, "hdoor1")) return new Door(name, Door.Orientation.HORIZONTAL);
            else if(Objects.equals(name, "vdoor")) return new Door(name, Door.Orientation.VERTICAL);
            else if(Objects.equals(name, "locked door") || Objects.equals(name, "locked door2")) return new LockedDoor(name, Door.Orientation.VERTICAL);
            else if(Objects.equals(name, "strong door")) return new StrongDoor(name, Door.Orientation.VERTICAL);
            else if(Objects.equals(name, "energy")) return new Energy();
            else if(Objects.equals(name, "ammo")) return new Ammo();
            else if(Objects.equals(name, "locker")) return new Locker();
            else if(Objects.equals(name, "ventilator")) return new Ventilator();
            else if(Objects.equals(name, "reactor")) return new Reactor();
            else if(Objects.equals(name, "trap")) return new Trap();
            else if (Objects.equals(name, "alien")) {
                if(Objects.equals(type, "running")) return new Alien(40, new FollowingRipley());
                else if(Objects.equals(type, "waiting1")) return new Alien(40, new Observing<>(Door.DOOR_OPENED, door -> door.getName().equals("hdoor"), new RandomlyMoving()));
                else if(Objects.equals(type, "waiting2")) return new Alien(40, new Observing<>(Door.DOOR_OPENED, door -> door.getName().equals("strong door"), new RandomlyMoving()));
                else if(Objects.equals(type, "waiting2 following")) return new Alien(40, new Observing<>(Door.DOOR_OPENED, door -> door.getName().equals("strong door"), new FollowingRipley()));
            }
            else if(Objects.equals(name, "alien mother")) return new AlienMother(300, new Observing<>(Door.DOOR_OPENED, door -> door.getName().equals("strong door"), new FollowingRipley()));
            else if(Objects.equals(name, "tunnel")) return new Tunnel();
            else if(Objects.equals(name, "switch")) return new Switch();
            else if(Objects.equals(name, "button")) return new Button();
            else if(Objects.equals(name, "floor switch")) return new FloorSwitch();
            else if(Objects.equals(name, "electricity")) return new Electricity();
            else if(Objects.equals(name, "alien egg")) return new AlienEgg(55);
            return null;
        }
    }

    @Override
    public void sceneInitialized(Scene scene){
        Map<String, MapMarker> markers = scene.getMap().getMarkers();
        MapMarker tel1 = markers.get("teleport1");
        MapMarker tel2 = markers.get("teleport2");
        MapMarker tel3 = markers.get("teleport3");
        Teleport teleport1 = new Teleport(null);
        Teleport teleport2 = new Teleport(null);
        Teleport teleport3 = new Teleport(null);
        scene.addActor(teleport1, tel1.getPosX(),tel1.getPosY());
        scene.addActor(teleport2, tel2.getPosX(),tel2.getPosY());
        scene.addActor(teleport3, tel3.getPosX(),tel3.getPosY());
        teleport1.setDestination(teleport2);
        teleport2.setDestination(teleport3);
        teleport3.setDestination(teleport1);

        Ripley ripley = new Ripley();
        MapMarker ellen = markers.get("ellen");
        scene.addActor(ripley, ellen.getPosX(), ellen.getPosY());
        scene.follow(ripley);
        teleport1.addedToScene(scene);
        teleport2.addedToScene(scene);
        teleport3.addedToScene(scene);

        Ventilator ventilator = scene.getFirstActorByType(Ventilator.class);
        ventilator.setBroken(true);
        ventilator.getAnimation().pause();

        Electricity electricity1 = scene.getFirstActorByType(Electricity.class);
        Electricity electricity2 = scene.getLastActorByType(Electricity.class);
        electricity1.addedToScene(scene);
        electricity2.addedToScene(scene);

        Door door = (Door) scene.getFirstActorByName("hdoor1");
        new When<>(
            () -> door.isOpen(),
            new Invoke<>(() -> ripley.decreaseEnergy())
        ).scheduleFor(ripley);
        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, (Ripley) -> ripley.stopDecreasingEnergy().dispose());

        Button button = scene.getFirstActorByType(Button.class);
        Door door1 = (Door) scene.getFirstActorByName("hdoor");
        new When<>(
            () -> door1 != null && door1.isOpen() == true,
            new Invoke<>(() -> button.openDoor())
        ).scheduleFor(ripley);

        LockedDoor lockedDoor = scene.getLastActorByType(LockedDoor.class);
        Reactor reactor = scene.getFirstActorByType(Reactor.class);
        new When<>(
            () -> lockedDoor != null && lockedDoor.isOpen(),
            new Invoke<>(() -> reactor.turnOn())
        ).scheduleFor(reactor);
        new When<>(
            () -> reactor.isOn(),
            new Invoke<>(() -> reactor.increaseTemperature(4000))
        ).scheduleFor(reactor);

        MovableController movableController = new MovableController(ripley);
        Disposable movable = scene.getInput().registerListener(movableController);
        KeeperController keeperController = new KeeperController(ripley);
        Disposable keeper = scene.getInput().registerListener(keeperController);
        ShooterController shooterController = new ShooterController(ripley);
        Disposable shooter = scene.getInput().registerListener(shooterController);
        scene.getGame().pushActorContainer(ripley.getBackpack());

        scene.getMessageBus().subscribe(Reactor.REACTOR_REPAIRED, (Disposable) -> movable.dispose());
        scene.getMessageBus().subscribe(Reactor.REACTOR_REPAIRED, (Disposable) -> keeper.dispose());
        scene.getMessageBus().subscribe(Reactor.REACTOR_REPAIRED, (Disposable) -> shooter.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> movable.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keeper.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> shooter.dispose());

        scene.getGame().getInput().onKeyPressed(Input.Key.ESCAPE, scene.getGame()::stop);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        SceneListener.super.sceneUpdating(scene);
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        if(ripley != null) ripley.showRipleyState();
        Reactor reactor = scene.getFirstActorByType(Reactor.class);
        if(reactor.isRepaired()) win(scene);
        if(!reactor.isRepaired() && reactor.getDamage() == 100 || ripley.getHealth().getValue() == 0) lose(scene);
    }

    public void win(@NotNull Scene scene){
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        Animation goodGob = new Animation("sprites/popup_level_done.png");
        int y = ripley.getPosY() - 64;
        int x = ripley.getPosX() - 144;
        scene.getOverlay().drawAnimation(goodGob, x, y);
        scene.cancelActions(ripley);
    }

    public void lose(@NotNull Scene scene){
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        Animation failed = new Animation("sprites/popup_level_failed.png");
        int y = ripley.getPosY() - 64;
        int x = ripley.getPosX() - 144;
        scene.getOverlay().drawAnimation(failed, x, y);
        scene.cancelActions(ripley);
    }
}
