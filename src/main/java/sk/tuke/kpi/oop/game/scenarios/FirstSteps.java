package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;

public class FirstSteps implements SceneListener {
    private Ripley ripley;

    @Override
    public void sceneInitialized(Scene scene){
        this.ripley = new Ripley();
        //this.ripley.setEnergy(55);
        scene.addActor(ripley, 0, 0);
        /*new Move(Direction.WEST, 1f).scheduleFor(ripley);
        new ActionSequence<>(
            new Wait<>(1),
            new Move(Direction.NORTH, 1f)
        ).scheduleFor(ripley);*/
        MovableController movableController = new MovableController(ripley);
        scene.getInput().registerListener(movableController);
        Energy energy = new Energy();
        scene.addActor(energy, 50, 50);
        new When<>(
            () -> energy.intersects(ripley),
            new Invoke<>(() -> new Use<>(energy).scheduleFor(ripley))
        ).scheduleFor(ripley);
        Ammo ammo = new Ammo();
        scene.addActor(ammo, 150, 150);
        new When<>(
            () -> ammo.intersects(ripley),
            new Invoke<>(() -> new Use<>(ammo).scheduleFor(ripley))
        ).scheduleFor(ripley);
        Wrench wrench = new Wrench();
        Hammer hammer = new Hammer();
        FireExtinguisher fireExtinguisher = new FireExtinguisher();
        /*scene.addActor(wrench, 100, 100);
        scene.addActor(hammer, -50, -50);
        scene.addActor(fireExtinguisher, -100, -100);*/
        this.ripley.getBackpack().add(wrench);
        this.ripley.getBackpack().add(hammer);
        this.ripley.getBackpack().add(fireExtinguisher);
        scene.getGame().pushActorContainer(ripley.getBackpack());
        new ActionSequence<>(
            new Wait<>(4),
            new Invoke<>(() -> this.ripley.getBackpack().shift())
        ).scheduleFor(ripley);
        KeeperController keeperController = new KeeperController(ripley);
        scene.getInput().registerListener(keeperController);
        Hammer hammer1 = new Hammer();
        FireExtinguisher fireExtinguisher1 = new FireExtinguisher();
        Wrench wrench1 = new Wrench();
        Hammer hammer2 = new Hammer();
        FireExtinguisher fireExtinguisher2 = new FireExtinguisher();
        Wrench wrench2 = new Wrench();
        Hammer hammer3 = new Hammer();
        FireExtinguisher fireExtinguisher3 = new FireExtinguisher();
        Wrench wrench3 = new Wrench();
        Hammer hammer4 = new Hammer();
        FireExtinguisher fireExtinguisher4 = new FireExtinguisher();
        Wrench wrench4 = new Wrench();

        scene.addActor(hammer, 300, -50);
        scene.addActor(fireExtinguisher, 100, 40);
        scene.addActor(wrench, -150, 200);
        scene.addActor(hammer1, 200, -50);
        scene.addActor(fireExtinguisher1, 50, 40);
        scene.addActor(wrench1, -190, 200);
        scene.addActor(hammer2, 230, -50);
        scene.addActor(fireExtinguisher2, 130, 40);
        scene.addActor(wrench2, -258, 200);
        scene.addActor(hammer3, 360, -50);
        scene.addActor(fireExtinguisher3, 1, 40);
        scene.addActor(wrench3, -30, 200);
        scene.addActor(hammer4, 310, -50);
        scene.addActor(fireExtinguisher4, 100, 80);
        scene.addActor(wrench4, -150, 20);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        SceneListener.super.sceneUpdating(scene);
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        int windowWidth = scene.getGame().getWindowSetup().getWidth();
        int xTextPos = windowWidth - 5*GameApplication.STATUS_LINE_OFFSET;
        scene.getGame().getOverlay().drawText("Energy: " + this.ripley.getHealth().getValue(), xTextPos, yTextPos);
        int yTextPos1 = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        int xTextPos1 = windowWidth - 10*GameApplication.STATUS_LINE_OFFSET;
        scene.getGame().getOverlay().drawText("Ammo: " + this.ripley.getAmmo(), xTextPos1, yTextPos1);
    }
}
