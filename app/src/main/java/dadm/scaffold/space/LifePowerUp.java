package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;

public class LifePowerUp extends Powerup {

    public LifePowerUp(GameEngine gameEngine){
        super(gameEngine, R.drawable.powerupgreenshield);

        this.speedFactor = gameEngine.pixelFactor * 100d/ 1000d;
    }

    @Override
    public void effect(GameObject collider){
        if(collider instanceof SpaceShipPlayer)
            ((SpaceShipPlayer) collider).addHealth(1);
        if(collider instanceof Bullet){
            if(((Bullet) collider).getParent() instanceof SpaceShipPlayer){
                (((Bullet) collider).getParent()).addHealth(1);
            }
        }
    }
}
