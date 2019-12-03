package dadm.scaffold.engine;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.input.InputController;

import dadm.scaffold.space.Meteor;
import dadm.scaffold.space.SpaceShip;

import dadm.scaffold.space.SpaceShipEnemy;
import dadm.scaffold.space.SpaceShipPlayer;
import dadm.scaffold.space.TripleShotPowerUp;

public class GameEngine {


    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private List<GameObject> objectsToAdd = new ArrayList<GameObject>();
    private List<GameObject> objectsToRemove = new ArrayList<GameObject>();

    private UpdateThread theUpdateThread;
    private DrawThread theDrawThread;
    public InputController theInputController;
    private final GameView theGameView;
    private final GameFragment theGameFragment;

    public int width;
    public int height;
    public double pixelFactor;

    public int numEnemies;

    private Activity mainActivity;

    private Integer points = 0;

    private long timeSinceLastEnemy = 5000;

    private long timeToSpawnNewEnemy = 6000;

    private long timeSinceLastMeteor = 2500;

    private long timeToSpawnNewMeteor = 3500;

    private long timeToSpawnPowerUp = 12000;

    private long timeSinceBeggining = 8000;



    public GameEngine(Activity activity, GameView gameView, int nEnemies, GameFragment gf) {
        mainActivity = activity;

        theGameFragment = gf;
        theGameView = gameView;
        theGameView.setGameObjects(this.gameObjects);
        this.width = theGameView.getWidth()
                - theGameView.getPaddingRight() - theGameView.getPaddingLeft();
        this.height = theGameView.getHeight()
                - theGameView.getPaddingTop() - theGameView.getPaddingTop();

        this.pixelFactor = this.height / 400d;

        numEnemies = nEnemies;

        ((ScaffoldActivity)mainActivity).points = 0;

    }

    public void setTheInputController(InputController inputController) {
        theInputController = inputController;
    }

    public void startGame() {
        // Stop a game if it is running
        stopGame();

        //for(int i = 0; i < numEnemies; i++){
        //    addGameObject(new SpaceShipEnemy(this, R.drawable.enemyblack1));
        //}






        // Setup the game objects
        int numGameObjects = gameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            gameObjects.get(i).startGame();
        }

        // Start the update thread
        theUpdateThread = new UpdateThread(this);
        theUpdateThread.start();

        // Start the drawing thread
        theDrawThread = new DrawThread(this);
        theDrawThread.start();
    }

    public void stopGame() {
        if (theUpdateThread != null) {
            theUpdateThread.stopGame();
        }
        if (theDrawThread != null) {
            theDrawThread.stopGame();
        }
    }

    public void pauseGame() {
        if (theUpdateThread != null) {
            theUpdateThread.pauseGame();
        }
        if (theDrawThread != null) {
            theDrawThread.pauseGame();
        }
    }

    public void resumeGame() {
        if (theUpdateThread != null) {
            theUpdateThread.resumeGame();
        }
        if (theDrawThread != null) {
            theDrawThread.resumeGame();
        }
    }

    public void addGameObject(GameObject gameObject) {
        if (isRunning()) {
            objectsToAdd.add(gameObject);
        } else {
            gameObjects.add(gameObject);
        }
        mainActivity.runOnUiThread(gameObject.onAddedRunnable);
    }

    public void removeGameObject(GameObject gameObject) {
        objectsToRemove.add(gameObject);
        mainActivity.runOnUiThread(gameObject.onRemovedRunnable);
    }

    private void drawLifes(final GameObject go){
        mainActivity.runOnUiThread(new Runnable(){
            @Override
            public void run() {
                ImageView iv1 = mainActivity.findViewById(R.id.life1);
                ImageView iv2 = mainActivity.findViewById(R.id.life2);
                ImageView iv3 = mainActivity.findViewById(R.id.life3);
                if(iv1 != null && iv2 != null && iv3 != null) {
                    switch (((SpaceShipPlayer) go).getHealth()) {
                        case 3:
                            iv1.setAlpha(1.0f);
                            iv2.setAlpha(1.0f);
                            iv3.setAlpha(1.0f);

                            break;
                        case 2:
                            iv1.setAlpha(1.0f);
                            iv2.setAlpha(1.0f);
                            iv3.setAlpha(0.0f);

                            break;
                        case 1:
                            iv1.setAlpha(1.0f);
                            iv2.setAlpha(0.0f);
                            iv3.setAlpha(0.0f);

                            break;
                        case 0:
                            iv1.setAlpha(0.0f);
                            iv2.setAlpha(0.0f);
                            iv3.setAlpha(0.0f);

                            break;
                    }
                }
            }
        });
    }

    public void onUpdate(long elapsedMillis) {

        generateRandomEnemies(elapsedMillis);
        generateRandomMeteor(elapsedMillis);
        generatePowerUp(elapsedMillis);


        if(numEnemies <= 0){
            stopGame();
            ((ScaffoldActivity)mainActivity).startScore();
        }

        //ACTUALIZAR PUNTUACION
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView texto = (TextView)mainActivity.findViewById(R.id.scoreText);
                if(texto != null)
                    texto.setText("SCORE: "+((ScaffoldActivity)mainActivity).points);
            }
        });

        for (GameObject go : gameObjects) {

            go.onUpdate(elapsedMillis, this);
            if(go instanceof SpaceShipPlayer){

                //ACTUALIZAR VIDAS
                drawLifes(go);

                if(((SpaceShipPlayer) go).getHealth() <= 0){
                    stopGame();
                    ((ScaffoldActivity)mainActivity).startGameOver();
                }
            }
        }
        synchronized (gameObjects) {
            while (!objectsToRemove.isEmpty()) {
                gameObjects.remove(objectsToRemove.remove(0));
            }
            while (!objectsToAdd.isEmpty()) {
                gameObjects.add(objectsToAdd.remove(0));
            }
        }
    }

    public void onDraw() {
        theGameView.draw();
    }

    public boolean isRunning() {
        return theUpdateThread != null && theUpdateThread.isGameRunning();
    }

    public boolean isPaused() {
        return theUpdateThread != null && theUpdateThread.isGamePaused();
    }

    public Context getContext() {
        return theGameView.getContext();
    }

    public List<GameObject> getGameObjects(){return gameObjects;}

    public void addPoints(int points){((ScaffoldActivity)mainActivity).points += points;}


    public void generateRandomEnemies(long elapsedMillis){
        if(timeSinceLastEnemy >= timeToSpawnNewEnemy){
            //Spawn Enemy
            SpaceShipEnemy se = new SpaceShipEnemy(this, R.drawable.enemyblack1);
            se.startGame();
            addGameObject(se);
            timeSinceLastEnemy = 0;
        }else{
            timeSinceLastEnemy += elapsedMillis;
        }
    }

    public void generateRandomMeteor(long elapsedMillis){
        if(timeSinceLastMeteor >= timeToSpawnNewMeteor){
            Meteor meteor = new Meteor(this);

            Random rand = new Random();
            int randomPos = rand.nextInt((10 - 1) + 1) + 1;

            meteor.init(this.width, this.height/randomPos, -1, -1);
            addGameObject(meteor);
            meteor.startGame();
            timeSinceLastMeteor = 0;
        }else{
            timeSinceLastMeteor += elapsedMillis;
        }
    }

    public void generatePowerUp(long elapsedMillis){
        if(timeSinceBeggining >= timeToSpawnPowerUp){
            TripleShotPowerUp pu = new TripleShotPowerUp(this);
            pu.init(this.width, this.height/2, -1, 1);
            pu.startGame();
            addGameObject(pu);
            timeSinceBeggining = 0;
        }else{
            timeSinceBeggining += elapsedMillis;
        }
    }


}
