package dadm.scaffold.space;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class SpaceShipEnemy extends SpaceShip {


    private final int HEALTH = 2;

    private final int COLLISION_FACTOR = 5;

    private int maxX;
    private int maxY;
    private double speedFactor= pixelFactor * 00d / 1000d;


    public SpaceShipEnemy(GameEngine gameEngine, int sprite){
        super(gameEngine, sprite);

        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;
        initBulletPool(gameEngine);

    }

    @Override
    public void startGame(){
        setHealth(HEALTH);
        setCollisionFactor(COLLISION_FACTOR);
        positionX = maxX;
        positionY = maxY/2;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){
        updatePosition(elapsedMillis, gameEngine);
        checkFiring(elapsedMillis, gameEngine);
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine){
        if(timeSinceLastFire > TIME_BETWEEN_BULLETS){
            Bullet bullet = getBullet();
            if(bullet == null){
                return;
            }
            bullet.init(this, positionX, positionY + imageHeight/2, -1);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
        }else{
            timeSinceLastFire += elapsedMillis;
        }
    }

    private void updatePosition(long elapsedMillis, GameEngine gameEngine){
        positionX -= speedFactor * elapsedMillis;

        if(positionX < 0){
            gameEngine.removeGameObject(this);
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }

    }


    @Override
    public void doTheThing(Sprite sprite){

    }

}
