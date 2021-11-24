package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    private Animation openedDoorAnimation;
    private Animation closedDoorAnimation;
    private boolean isOpen;
    public enum Orientation{
        VERTICAL,
        HORIZONTAL;
    }
    private Orientation orientation;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);

    public Door() {
        this.closedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
        this.openedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(this.closedDoorAnimation);
        this.isOpen = false;
    }

    public Door(String name, Orientation orientation){
        super(name);
        if(orientation == Orientation.VERTICAL) {
            this.closedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
            this.openedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE);
            setAnimation(this.closedDoorAnimation);
            this.isOpen = false;
            this.orientation = Orientation.VERTICAL;
        }
        else if(orientation == Orientation.HORIZONTAL){
            this.closedDoorAnimation = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED);
            this.openedDoorAnimation = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE);
            setAnimation(this.closedDoorAnimation);
            this.isOpen = false;
            this.orientation = Orientation.HORIZONTAL;
        }
    }

    @Override
    public void open() {
        this.isOpen = true;
        setAnimation(this.openedDoorAnimation);
        if(this.orientation == Orientation.VERTICAL && this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).isWall()){
            this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
            this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.CLEAR);
        }
        else if(this.orientation == Orientation.HORIZONTAL && this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).isWall()){
            this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
            this.getScene().getMap().getTile(this.getPosX() / 16 + 1, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
        }
        getScene().getMessageBus().publish(DOOR_OPENED, this);
    }

    @Override
    public void close() {
        this.isOpen = false;
        setAnimation(this.closedDoorAnimation);
        if (this.orientation == Orientation.VERTICAL && !this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).isWall()) {
            this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.WALL);
        }
        else if (this.orientation == Orientation.HORIZONTAL && !this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).isWall()) {
            this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            this.getScene().getMap().getTile(this.getPosX() / 16 + 1, this.getPosY() / 16).setType(MapTile.Type.WALL);
        }
        getScene().getMessageBus().publish(DOOR_CLOSED, this);
    }

    @Override
    public boolean isOpen() {
        return this.isOpen;
    }

    @Override
    public void useWith(Actor actor) {
        if(this.isOpen) this.close();
        else this.open();
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
        if(this.orientation == Orientation.VERTICAL) this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.WALL);
        else if(this.orientation == Orientation.HORIZONTAL) this.getScene().getMap().getTile(this.getPosX() / 16 + 1, this.getPosY() / 16).setType(MapTile.Type.WALL);
    }
}
