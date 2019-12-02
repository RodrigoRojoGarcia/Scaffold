package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;

public class Meteor extends Powerup {

    public Meteor(GameEngine gameEngine){
        super(gameEngine, R.drawable.star_bronze);

        speedFactor = gameEngine.pixelFactor * 50d / 1000d;
    }

    @Override
    public void effect(GameObject collider){
        if(collider instanceof SpaceShipPlayer)
        ((SpaceShipPlayer) collider).addHealth(-1);
    }
}
