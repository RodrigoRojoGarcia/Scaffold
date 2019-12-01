package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class Bullet extends Projectile {

    private double speedFactor;

    private Spaceship parent;

    public Bullet(){
        super(R.drawable.bullet);

        speedFactor = GameEngine.Instance.pixelFactor * -300d / 1000d;
    }

    public Spaceship getParent(){return parent;}


    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis ) {
        positionY += speedFactor * elapsedMillis;

    }


    public void init(Spaceship parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
        parent = parentPlayer;
    }

    @Override
    public void doTheThing(SpaceShipPlayer player){

    }

}
