package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int currentAmmo;
    private int maxAmmo;

    public Firearm(int current, int max){
        this.currentAmmo = current;
        this.maxAmmo = max;
    }

    public Firearm(int health){
        this.currentAmmo = health;
        this.maxAmmo = health;
    }

    public int getAmmo(){
        return this.currentAmmo;
    }

    public void reload(int newAmmo){
        this.currentAmmo += newAmmo;
        if(this.currentAmmo > this.maxAmmo) this.currentAmmo = this.maxAmmo;
    }

    public Fireable fire(){
        if(this.currentAmmo == 0) return null;
        this.currentAmmo--;
        return this.createBullet();
    }

    protected abstract Fireable createBullet();
}
