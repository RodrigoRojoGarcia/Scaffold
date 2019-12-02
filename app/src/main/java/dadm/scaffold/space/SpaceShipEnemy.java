package dadm.scaffold.space;

import java.util.Random;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;
import dadm.scaffold.engine.Sprite;

public class SpaceShipEnemy extends SpaceShip {


    private final int HEALTH = 2;

    private final int COLLISION_FACTOR = 14000;
    private final Integer POINTS = 10;
    protected static final long TIME_BETWEEN_BULLETS = 1500;

    private int maxX;
    private int maxY;
    private double speedFactor= pixelFactor * 00d / 1000d;

    private Random rand;
    private int randomPos;

    public SpaceShipEnemy(GameEngine gameEngine, int sprite){
        super(gameEngine, sprite);

        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;
        initBulletPool(gameEngine);

        rand = new Random();
        randomPos = rand.nextInt((10 - 1) + 1) + 1;
    }

    @Override
    public void startGame(){
        setHealth(HEALTH);
        setCollisionFactor(COLLISION_FACTOR);
        positionX = maxX;
        positionY = maxY/randomPos;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){
        updatePosition(elapsedMillis, gameEngine);
        checkFiring(elapsedMillis, gameEngine);
        checkCollision(gameEngine);
        if(this.health <= 0){
            gameEngine.removeGameObject(this);
            gameEngine.numEnemies--;
            gameEngine.addPoints(POINTS);

        }
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine){
        if(timeSinceLastFire > TIME_BETWEEN_BULLETS){
            Bullet bullet = getBullet();
            if(bullet == null){
                return;
            }
            bullet.init(this, positionX, positionY + imageHeight/2, -1, 0);
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

    private void checkCollision (GameEngine gameEngine){
        for(GameObject go : gameEngine.getGameObjects()){
            if(go instanceof  Bullet){
                if(intersect((Sprite) go)){
                    if(((Bullet) go).getParent() instanceof SpaceShipPlayer){
                        gameEngine.removeGameObject(go);
                        ((Bullet) go).getParent().releaseBullet((Bullet)go);
                        addHealth(-1);
                    }
                }
            }
        }
    }

}
