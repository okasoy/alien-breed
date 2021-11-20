package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.MissionImpossible;


public class Main {
    public static void main(String[] args) {
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        Game game = new GameApplication(windowSetup, new LwjglBackend());

        Scene missionImpossible = new World("mission-impossible", "maps/mission-impossible.tmx", new MissionImpossible.Factory());

        SceneListener MissionImpossible = new MissionImpossible();

        missionImpossible.addListener(MissionImpossible);

        game.addScene(missionImpossible);

        game.start();

        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
    }
}
