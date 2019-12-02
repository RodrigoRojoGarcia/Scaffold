package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public abstract class SpaceShip extends Sprite {


    protected int health;

    protected static final int INITIAL_BULLET_POOL_AMOUNT = 12;

    List<Bullet> bullets = new ArrayList<Bullet>();
    protected long timeSinceLastFire;


    public SpaceShip(GameEngine gameEngine, int sprite){
        super(gameEngine, sprite);
    }

    protected void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }
    }

    protected Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    protected void releaseBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void setHealth(int health){ this.health = health;}
    public void addHealth(int health){
        this.health += health;
    }
    public int getHealth(){return this.health;}



}
