package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;

public class Asteroid extends Projectile {

    private final int ASTEROID_COLLISION_FACTOR = 2;


    public Asteroid(GameEngine gameEngin){
        super(R.drawable.ship);
        this.setCollisionFactor(ASTEROID_COLLISION_FACTOR);
    }

    @Override
    public void startGame(){

    }
    @Override
    public void onUpdate(long elapsedMillis){

    }

    @Override
    public void doTheThing(SpaceShipPlayer player){
        player.getLife().decrementAndGet();
    }



}
