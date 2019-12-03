package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;

public class Meteor extends Powerup {

    private final int POINTS = 5;


    private GameEngine theGameEngine;

    public Meteor(GameEngine gameEngine){
        super(gameEngine, R.drawable.meteorgreysmall1);

        if(Math.random() >= 0.5){
            setDrawable(gameEngine, R.drawable.meteorbrownsmall1);
        }
        theGameEngine = gameEngine;
        speedFactor = gameEngine.pixelFactor * 50d / 1000d;
    }

    @Override
    public void effect(GameObject collider){
        if(collider instanceof SpaceShipPlayer) {
            ((SpaceShipPlayer) collider).addHealth(-1);

        }
        if(collider instanceof Bullet){
            if(((Bullet) collider).getParent() instanceof SpaceShipPlayer){
                theGameEngine.addPoints(POINTS);
            }
        }
    }
}
