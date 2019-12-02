package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;

public class TripleShotPowerUp extends Powerup {

    public TripleShotPowerUp(GameEngine gameEngine){
        super(gameEngine, R.drawable.bold_silver);
    }

    @Override
    public void effect(SpaceShipPlayer player){
        player.setTripleShot(true);
    }
}
