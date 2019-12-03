package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;
import dadm.scaffold.engine.Sprite;

public abstract class Powerup extends Sprite {

    protected double speedFactor;
    private double directionX;
    private double directionY;
    private final int COLLISION_FACTOR = 2000;


    public Powerup(GameEngine gameEngine, int sprite){
        super(gameEngine, sprite);


    }

    @Override
    public void startGame(){
        setCollisionFactor(COLLISION_FACTOR);
        setRotation(0);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){
        updatePosition(elapsedMillis, gameEngine);
        checkCollision(gameEngine);

    }

    public void init(double positionX, double positionY, double directionX, double directionY){
        System.out.println(imageWidth);

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
        if(positionY > gameEngine.height - imageHeight || positionY < -imageHeight/2){
            this.directionY *= -1;
        }
        if(positionX < -imageWidth){
            gameEngine.removeGameObject(this);
        }
    }


    public abstract void effect(GameObject collider);


    private void checkCollision(GameEngine gameEngine){
        for(GameObject go : gameEngine.getGameObjects()){
            if(go instanceof Bullet){

                if(intersect((Sprite) go)){

                    System.out.println("ME HAN DISPARAO");
                    if(((Bullet)go).getParent() instanceof  SpaceShipPlayer){
                        gameEngine.removeGameObject(this);
                        gameEngine.removeGameObject(go);
                        ((Bullet) go).getParent().releaseBullet((Bullet)go);
                        effect(go);
                    }
                }
            }
        }
    }

}
