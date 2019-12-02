package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public abstract class Powerup extends Sprite {

    private double speedFactor;
    private double directionX;
    private double directionY;
    private final int COLLISION_FACTOR = 2000;

    public Powerup(GameEngine gameEngine, int sprite){
        super(gameEngine, sprite);

        speedFactor = gameEngine.pixelFactor * 300d / 1000d;
    }

    @Override
    public void startGame(){
        setCollisionFactor(COLLISION_FACTOR);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){
        updatePosition(elapsedMillis, gameEngine);

    }

    public void init(double positionX, double positionY, double directionX, double directionY){
        this.positionX = positionX - imageWidth/2;
        this.positionY = positionY -imageHeight/2;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    @Override
    public void doTheThing(Sprite sprite){

    }


    private void updatePosition(long elapsedMillis, GameEngine gameEngine){
        positionX += speedFactor * elapsedMillis * this.directionX;
        positionY += speedFactor * elapsedMillis * this.directionY;
        if(positionY > gameEngine.height || positionY < -imageHeight){
            this.directionY *= -1;
        }
        if(positionX < -imageWidth){
            gameEngine.removeGameObject(this);
        }
    }


    public abstract void effect(SpaceShipPlayer player);

}
