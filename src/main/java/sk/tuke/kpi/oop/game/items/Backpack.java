package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Backpack implements ActorContainer<Collectible> {
    private String name;
    private int capacity;
    private List<Collectible> backpack;

    public Backpack(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
        this.backpack = new ArrayList<>();
    }

    @Override
    public @NotNull List<Collectible> getContent() {
        return List.copyOf(this.backpack);
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public int getSize() {
        return this.backpack.size();
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if(this.getSize() == this.capacity) throw new IllegalStateException(getName()+" is full");
        else this.backpack.add(actor);
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        if(this.getSize() == 0) return;
        this.backpack.remove(actor);
    }

    @Override
    public @Nullable Collectible peek() {
        if(this.getSize() == 0) return null;
        return this.backpack.get(getSize() - 1);
    }

    @Override
    public void shift() {
        if(this.getSize() == 0) return;
        Collections.rotate(this.backpack, 1);
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return this.backpack.iterator();
    }
}
