package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;

public class SpaceShipPlayer extends Spaceship {



    private int maxX;
    private int maxY;
    private double speedFactor;

    private AtomicInteger life = new AtomicInteger();

    public SpaceShipPlayer(GameEngine gameEngine){
        super(R.drawable.ship);
        speedFactor = pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;


    }
    public AtomicInteger getLife(){return this.life;}

    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY / 2;
    }

    @Override
    public void onUpdate(long elapsedMillis) {
        // Get the info from the inputController
        positionX += speedFactor * GameEngine.Instance.theInputController.horizontalFactor * elapsedMillis;
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += speedFactor * GameEngine.Instance.theInputController.verticalFactor * elapsedMillis;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }

        checkFiring(elapsedMillis, GameEngine.Instance.theInputController.isFiring);
    }





}
