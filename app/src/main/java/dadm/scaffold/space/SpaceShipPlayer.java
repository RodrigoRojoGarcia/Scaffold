package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;

public class SpaceShipPlayer extends SpaceShip {


    private final int HEALTH = 3;
    private final int COLLISION_FACTOR = 14000;
    protected static final long TIME_BETWEEN_BULLETS = 250;

    private int maxX;
    private int maxY;
    private double speedFactor;

    private boolean tripleShot = false;

    private int points = 0;




    public SpaceShipPlayer(GameEngine gameEngine){
        super(gameEngine, R.drawable.ship);
        speedFactor = pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;

        initBulletPool(gameEngine);
    }


    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY / 2;
        setHealth(HEALTH);
        setCollisionFactor(COLLISION_FACTOR);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.theInputController);
        checkFiring(elapsedMillis, gameEngine);

        checkCollisions(gameEngine);

    }

    private void updatePosition(long elapsedMillis, InputController inputController) {
        positionX += speedFactor * inputController.horizontalFactor * elapsedMillis;
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += speedFactor * inputController.verticalFactor * elapsedMillis;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (gameEngine.theInputController.isFiring && timeSinceLastFire > TIME_BETWEEN_BULLETS) {

            if(this.tripleShot){
                
            }else{
                Bullet bullet = getBullet();
                if (bullet == null) {
                    return;
                }
                bullet.init(this, positionX + imageWidth, positionY+ imageHeight/2, 1);
                gameEngine.addGameObject(bullet);
                timeSinceLastFire = 0;
            }

        }
        else {
            timeSinceLastFire += elapsedMillis;
        }
    }

    private void checkCollisions(GameEngine gameEngine){
        for(GameObject go : gameEngine.getGameObjects()){
            if(go instanceof Bullet){
                if(intersect((Sprite) go)){
                    System.out.println("owo");
                    if(((Bullet) go).getParent() != this){
                        System.out.println("OwO");
                        gameEngine.removeGameObject(go);
                        ((Bullet) go).getParent().releaseBullet((Bullet) go);
                        addHealth(-1);
                    }
                }

            }
        }
    }

    @Override
    public void doTheThing(Sprite sprite){

    }

    public void setTripleShot(boolean tripleShot){this.tripleShot = tripleShot;}

    private void resetPowerUps(){
        this.tripleShot = false;
    }

    public void addPoints(int points){this.points += points;}


}
