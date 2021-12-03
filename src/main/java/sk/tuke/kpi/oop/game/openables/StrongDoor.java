package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;

public class StrongDoor extends Door{
    private Animation openedAnimation;
    private Animation closedAnimation;
    private boolean isOpen;

    public StrongDoor(String name, Orientation orientation){
        super(name, orientation);
        this.closedAnimation = new Animation("sprites/vdoor_strong.png", 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
        this.openedAnimation = new Animation("sprites/vdoor_strong.png", 16, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(this.closedAnimation);
        this.isOpen = false;
    }

    @Override
    public void open(){
        this.isOpen = true;
        setAnimation(this.openedAnimation);
        if(this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).isWall()) {
            this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
            this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.CLEAR);
        }
        getScene().getMessageBus().publish(DOOR_OPENED, this);
    }

    @Override
    public boolean isOpen() {
        return this.isOpen;
    }

    @Override
    public void close() {
    }

    @Override
    public void useWith(Actor actor) {
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.WALL);
    }
}
