package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.gamelib.inspector.InspectableScene;
import sk.tuke.kpi.oop.game.scenarios.FirstSteps;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        Game game = new GameApplication(windowSetup, new LwjglBackend());

        //Scene scene = new World("world");

        //game.addScene(scene);

        Scene scene = new InspectableScene(new World("world"), List.of("sk.tuke.kpi"));
        game.addScene(scene);

        FirstSteps beginning = new FirstSteps();

        scene.addListener(beginning);

        game.start();

        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
    }
}
