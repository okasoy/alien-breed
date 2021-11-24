package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {
    private int speed;
    private Animation ripleyAnimation;
    private Backpack backpack;
    private Disposable disposable;
    private Health health;
    private Firearm gun;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);

    public Ripley(){
        super("Ellen");
        this.ripleyAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(this.ripleyAnimation);
        this.ripleyAnimation.pause();
        this.speed = 2;
        this.backpack = new Backpack("Ripley's backpack", 10);
        this.disposable = null;
        this.health = new Health(100, 100);
        this.health.onExhaustion(() -> {
            this.setAnimation(new Animation("sprites/player_die.png",32,32,0.1f, Animation.PlayMode.ONCE));
            getScene().getMessageBus().publish(RIPLEY_DIED,this);
        });
        this.gun = new Gun(50, 200);
    }

    public void decreaseEnergy(){
        if(this.health.getValue() == 0) {
            Animation died = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
            setAnimation(died);
            getScene().getMessageBus().publish(RIPLEY_DIED, this);
            if(this.disposable != null) this.disposable.dispose();
            return;
        }
        this.disposable = new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() -> {
                    if (this.health.getValue() <= 0) {
                        Animation died = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
                        setAnimation(died);
                        getScene().getMessageBus().publish(RIPLEY_DIED, this);
                        if(this.disposable != null) this.disposable.dispose();
                        return;
                    }
                    else this.health.drain(1);
                }),
                new Wait<>(1)
            )
        ).scheduleFor(this);
    }

    public Disposable stopDecreasingEnergy() {
        return this.disposable;
    }

    public int getAmmo(){
        return this.gun.getAmmo();
    }

    public void setAmmo(int Ammo){
        this.gun.reload(Ammo);
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        this.ripleyAnimation.setRotation(direction.getAngle());
        this.ripleyAnimation.play();
    }

    @Override
    public void stoppedMoving() {
        this.ripleyAnimation.pause();
    }

    @Override
    public Backpack getBackpack() {
        return this.backpack;
    }

    public void showRipleyState(){
        int windowHeight = getScene().getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        int windowWidth = getScene().getGame().getWindowSetup().getWidth();
        int xTextPos = windowWidth - 5*GameApplication.STATUS_LINE_OFFSET;
        getScene().getGame().getOverlay().drawText("Energy: " + this.health.getValue(), xTextPos, yTextPos);
        int yTextPos1 = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        int xTextPos1 = windowWidth - 10*GameApplication.STATUS_LINE_OFFSET;
        getScene().getGame().getOverlay().drawText("Ammo: " + this.getAmmo(), xTextPos1, yTextPos1);
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    @Override
    public Firearm getFirearm() {
        return this.gun;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        this.gun = weapon;
    }
}
