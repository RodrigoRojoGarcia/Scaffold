package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;

public class TripleShotPowerUp extends Powerup {

    public TripleShotPowerUp(GameEngine gameEngine){
        super(gameEngine, R.drawable.bold_silver);

        this.speedFactor = gameEngine.pixelFactor * 100d / 1000d;
    }

    @Override
    public void effect(GameObject collider){

        if(collider instanceof SpaceShipPlayer)
            ((SpaceShipPlayer) collider).setTripleShot(true);
        if(collider instanceof Bullet){
            if(((Bullet) collider).getParent() instanceof SpaceShipPlayer){
                ((SpaceShipPlayer) ((Bullet) collider).getParent()).setTripleShot(true);
            }
        }
    }
}
