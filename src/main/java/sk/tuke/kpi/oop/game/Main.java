package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.MissionImpossible;
import sk.tuke.kpi.oop.game.scenarios.MyLevel;

public class Main {
    public static void main(String[] args) {
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 1200, 600);

        Game game = new GameApplication(windowSetup, new LwjglBackend());

        Scene myLevel = new World("my-level", "maps/map.tmx", new MyLevel.Factory());

        SceneListener MyLevel = new MyLevel();

        myLevel.addListener(MyLevel);

        game.addScene(myLevel);

        game.start();

        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
    }
}
