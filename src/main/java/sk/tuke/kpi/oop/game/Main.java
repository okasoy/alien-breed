package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;

public class Main {
    public static void main(String[] args) {
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        Game game = new GameApplication(windowSetup, new LwjglBackend());

        Scene escapeRoom = new World("escape-room", "maps/escape-room.tmx", new EscapeRoom.Factory());

        SceneListener EscapeRoom = new EscapeRoom();

        escapeRoom.addListener(EscapeRoom);

        game.addScene(escapeRoom);

        game.start();

        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
    }
}
