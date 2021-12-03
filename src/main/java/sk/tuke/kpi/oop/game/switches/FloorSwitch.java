package sk.tuke.kpi.oop.game.switches;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.items.AccessCard;

import java.util.Map;

public class FloorSwitch extends Switch {
    private boolean switched;

    public FloorSwitch(){
        Animation floorSwitchAnimation = new Animation("sprites/floor_switch.png");
        setAnimation(floorSwitchAnimation);
        this.switched = false;
    }

    @Override
    public void addActor(Actor actor) {
        if(actor == null || this.switched == true) return;
        Map<String, MapMarker> markers = this.getScene().getMap().getMarkers();
        MapMarker accessCard = markers.get("access card");
        getScene().addActor(new AccessCard(), accessCard.getPosX(), accessCard.getPosY());
        this.switched = true;
    }

}

