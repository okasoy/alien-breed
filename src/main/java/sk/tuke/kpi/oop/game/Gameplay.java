package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.tools.FireExtinguisher;
import sk.tuke.kpi.oop.game.tools.Hammer;
import sk.tuke.kpi.oop.game.tools.Wrench;

import java.util.Map;

public class Gameplay extends Scenario {

    private void turnOnCooler(Cooler cooler){
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);
    }

    private void useHammer(Reactor reactor, Hammer hammer){
        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(() -> reactor.repair())
        ).scheduleFor(reactor);
        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(() -> hammer.useWith(reactor))
        ).scheduleFor(reactor);
    }

    private void useExtinguisher(Reactor reactor, FireExtinguisher extinguisher){
        new When<>(
            () -> reactor.getDamage() == 100,
            new Invoke<>(() -> reactor.extinguish())
        ).scheduleFor(reactor);
        new When<>(
            () -> reactor.getDamage() == 100,
            new Invoke<>(() -> extinguisher.useWith(reactor))
        ).scheduleFor(reactor);
    }

    private void repairDefectiveLight(DefectiveLight defectiveLight, Wrench wrench){
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(defectiveLight::repair)
        ).scheduleFor(defectiveLight);
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(wrench::useWith)
        ).scheduleFor(defectiveLight);
    }

    @Override
    public void setupPlay(@NotNull Scene scene) {
        Map<String, MapMarker> markers = scene.getMap().getMarkers();
        Reactor reactor = new Reactor();
        MapMarker reactorArea1 = markers.get("reactor-area-1");
        scene.addActor(reactor, reactorArea1.getPosX(), reactorArea1.getPosY());
        reactor.turnOn();
        Reactor reactor2 = new Reactor();
        MapMarker reactorArea2 = markers.get("reactor-area-2");
        scene.addActor(reactor2, reactorArea2.getPosX(), reactorArea2.getPosY());
        reactor2.turnOn();
        Cooler cooler = new Cooler(reactor);
        MapMarker coolerArea1 = markers.get("cooler-area-1");
        scene.addActor(cooler, coolerArea1.getPosX(), coolerArea1.getPosY());
        Cooler cooler2 = new Cooler(reactor2);
        MapMarker coolerArea2 = markers.get("cooler-area-2");
        scene.addActor(cooler2, coolerArea2.getPosX(), coolerArea2.getPosY());
        Cooler cooler3 = new Cooler(reactor2);
        MapMarker coolerArea3 = markers.get("cooler-area-3");
        scene.addActor(cooler3, coolerArea3.getPosX(), coolerArea3.getPosY());
        turnOnCooler(cooler);
        turnOnCooler(cooler2);
        turnOnCooler(cooler3);
        Hammer hammer = new Hammer(1);
        scene.addActor(hammer, 152, 132);
        useHammer(reactor, hammer);
        FireExtinguisher extinguisher = new FireExtinguisher();
        scene.addActor(extinguisher, 200, 200);
        useExtinguisher(reactor, extinguisher);
        DefectiveLight defectiveLight = new DefectiveLight();
        scene.addActor(defectiveLight, 152,170);
        reactor.addDevice(defectiveLight);
        defectiveLight.turnOn();
        Wrench wrench = new Wrench();
        scene.addActor(wrench, 200, 170);
        repairDefectiveLight(defectiveLight, wrench);
        Computer computer = new Computer();
        MapMarker computerArea1 = markers.get("computer-area");
        scene.addActor(computer, computerArea1.getPosX(), computerArea1.getPosY());
        reactor.addDevice(computer);
    }

}
