package dadm.scaffold.space;

import android.widget.ImageView;

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


    protected static long TIME_BETWEEN_BULLETS = 250;

    private int maxX;
    private int maxY;
    private double speedFactor;

    private boolean tripleShot = false;

    public SpaceShipPlayer(GameEngine gameEngine, int sprite){
        super(gameEngine, sprite);
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
        resetPowerUps();
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
                Bullet bulletUp = getBullet();
                Bullet bulletMid = getBullet();
                Bullet bulletDown = getBullet();
                if(bulletUp == null || bulletMid == null || bulletDown == null){
                    return;
                }
                bulletUp.init(this, positionX + imageWidth, positionY + imageHeight/2, 1, 1);
                bulletUp.setRotation(135);
                bulletMid.init(this, positionX + imageWidth, positionY + imageHeight/2, 1, 0);
                bulletMid.setRotation(90);
                bulletDown.init(this, positionX + imageWidth, positionY + imageHeight/2, 1, -1);
                bulletDown.setRotation(45);

                gameEngine.addGameObject(bulletUp);
                gameEngine.addGameObject(bulletMid);
                gameEngine.addGameObject(bulletDown);
                timeSinceLastFire = 0;

            }else{
                Bullet bullet = getBullet();
                if (bullet == null) {
                    return;
                }
                bullet.init(this, positionX + imageWidth, positionY+ imageHeight/2, 1, 0);
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
            if(go instanceof Powerup){
                if(intersect((Sprite) go)){
                    ((Powerup) go).effect(this);
                    gameEngine.removeGameObject(go);
                }
            }
            if(go instanceof Bullet){
                if(intersect((Sprite) go)){
                    if(((Bullet) go).getParent() != this){
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

    public void setTripleShot(boolean tripleShot){
        this.tripleShot = tripleShot;
        System.out.println("POWERUP");
        if(this.tripleShot){
            TIME_BETWEEN_BULLETS = 500;
        }else{
            TIME_BETWEEN_BULLETS = 250;
        }

    }


    private void resetPowerUps(){
        this.tripleShot = false;
    }



}
