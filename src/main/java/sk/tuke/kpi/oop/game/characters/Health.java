package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {
    private int currentHealth;
    private int maxHealth;
    private List<ExhaustionEffect> effects;


    public Health(int current, int max){
        this.currentHealth = current;
        this.maxHealth = max;
        this.effects = new ArrayList<>();
    }

    public Health(int health){
        this.currentHealth = health;
        this.maxHealth = health;
        this.effects = new ArrayList<>();
    }

    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }

    public void onExhaustion(ExhaustionEffect effect){
        if(this.effects == null) return;
        this.effects.add(effect);
    }

    public int getValue(){
        return this.currentHealth;
    }

    public void refill(int amount){
        this.currentHealth += amount;
        if(this.currentHealth > this.maxHealth) this.currentHealth = this.maxHealth;
    }

    public void restore(){
        this.currentHealth = this.maxHealth;
    }

    public void drain(int amount){
        if(this.currentHealth <= amount) this.exhaust();
        else this.currentHealth -= amount;
    }

    public void exhaust(){
        if(this.effects == null) return;
        this.currentHealth = 0;
        this.effects.forEach(ExhaustionEffect::apply);
        this.effects.clear();
    }
}
