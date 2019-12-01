package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class Bullet extends Sprite {

    private double speedFactor;

    private int direction;

    private final int COLLSION_FACTOR = 2000;

    private SpaceShip parent;

    public Bullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.bullet);

        speedFactor = gameEngine.pixelFactor * 300d / 1000d;
    }

    @Override
    public void startGame() {
        setCollisionFactor(COLLSION_FACTOR);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionX += speedFactor * elapsedMillis * this.direction;
        if (positionX < -imageWidth || positionX > gameEngine.width - imageWidth) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this);
        }
    }


    public void init(SpaceShip parentSpaceShip, double initPositionX, double initPositionY, int direction) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
        parent = parentSpaceShip;
        this.direction = direction;
    }

    @Override
    public void doTheThing(Sprite sprite){

    }

    public SpaceShip getParent(){return parent;}

}
