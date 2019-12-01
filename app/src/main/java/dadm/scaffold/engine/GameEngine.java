package dadm.scaffold.engine;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import dadm.scaffold.input.InputController;
import dadm.scaffold.space.Bullet;
import dadm.scaffold.space.Projectile;
import dadm.scaffold.space.SpaceShipPlayer;
import dadm.scaffold.space.Spaceship;

public class GameEngine {

    public static GameEngine Instance = new GameEngine();


    private List<Projectile> projectilesToAdd = new ArrayList<>();
    private List<Spaceship> spaceshipsToAdd = new ArrayList<>();

    private Map<Integer, Projectile> projectilesToRemove = new ConcurrentHashMap<>();
    private Map<Integer, Spaceship> spaceshipsToRemove = new ConcurrentHashMap<>();

    private Map<Integer, Projectile> projectiles = new ConcurrentHashMap<>();
    private Map<Integer, Spaceship> spaceships = new ConcurrentHashMap<>();
    private SpaceShipPlayer player;


    private UpdateThread theUpdateThread;
    private DrawThread theDrawThread;
    public InputController theInputController;
    private GameView theGameView;

    public int width;
    public int height;
    public double pixelFactor;



    //#region GAMEOBJECTS QUANTITIES
    private AtomicInteger numProjectiles = new AtomicInteger();
    private AtomicInteger numSpaceships = new AtomicInteger();
    //#endregion

    private Activity mainActivity;


    private GameEngine(){}

    public void setActivity(Activity activity){
        mainActivity = activity;
    }
    public void setGameView(GameView gameView){
        theGameView = gameView;
        theGameView.setProjectiles(this.projectiles);
        theGameView.setSpaceships(this.spaceships);
        this.width = theGameView.getWidth()
                - theGameView.getPaddingRight() - theGameView.getPaddingLeft();
        this.height = theGameView.getHeight()
                - theGameView.getPaddingTop() - theGameView.getPaddingTop();

        this.pixelFactor = this.height / 400d;

        this.player = new SpaceShipPlayer(this);
    }


    public void setTheInputController(InputController inputController) {
        theInputController = inputController;
    }

    public void startGame() {
        // Stop a game if it is running
        stopGame();

        for(Projectile proj : projectiles.values()){
            proj.startGame();
        }
        for(Spaceship sp : spaceships.values()){
            sp.startGame();
        }

        player.startGame();

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

    //public void addGameObject(GameObject gameObject) {
    //    if (isRunning()) {
    //        objectsToAdd.add(gameObject);
    //    } else {
    //        gameObjects.add(gameObject);
    //    }
    //    mainActivity.runOnUiThread(gameObject.onAddedRunnable);
    //}

    public void addProjectile(Projectile proj){
        proj.setId(numProjectiles.get());
        if(isRunning()){
            projectiles.put(numProjectiles.incrementAndGet(), proj);
        }else{
            projectiles.put(numProjectiles.incrementAndGet(), proj);
        }
        mainActivity.runOnUiThread((proj.onAddedRunnable));
    }

    //public void removeGameObject(GameObject gameObject) {
    //    objectsToRemove.add(gameObject);
    //    mainActivity.runOnUiThread(gameObject.onRemovedRunnable);
    //}

    public void onUpdate(long elapsedMillis) {
        for(final Projectile proj : projectiles.values()){
            //executor.submit(new Runnable(){
            //    @Override
            //    public void run(){
            //        proj.onUpdate(elapsedMillis);
            //    }
            //});
        }
        for(final Spaceship spaceship : spaceships.values()){
            //executor.submit(new Runnable(){
            //    @Override
            //    public void run(){
            //        spaceship.onUpdate(elapsedMillis);
            //    }
            //});
        }
        player.onUpdate(elapsedMillis);





        final AtomicBoolean removeProjectiles = new AtomicBoolean();
        final AtomicBoolean removeSpaceships = new AtomicBoolean();

        //executor.submit(new Runnable() {
        //    @Override
        //    public void run() {
        //        removeProjectiles(projectilesToRemove, removeProjectiles);
        //    }
        //});
        //executor.submit(new Runnable(){
        //    @Override
        //    public void run(){
        //        removeSpaceships(spaceshipsToRemove, removeSpaceships);
        //    }
        //});
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

    public void removeProjectiles(Map<Integer,Projectile> projectiles2remove, AtomicBoolean removeProjectiles){
        //try{
        //projectilesInfoProcessed.await();
        if(removeProjectiles.get()){
            for(Integer i : projectiles2remove.keySet()){
                projectiles.remove(i);
                mainActivity.runOnUiThread(projectiles2remove.get(i).onRemovedRunnable);
                numProjectiles.decrementAndGet();
            }
        }


        //}catch (InterruptedException e){
        //    e.printStackTrace();
        //}
    }
    public void removeSpaceships(Map<Integer,Spaceship> spaceships2remove, AtomicBoolean removeSpaceships){
        //try{
        //projectilesInfoProcessed.await();
        if(removeSpaceships.get()){
            for(Integer i : spaceships2remove.keySet()){
                projectiles.remove(i);
                mainActivity.runOnUiThread(spaceships2remove.get(i).onRemovedRunnable);
                numProjectiles.decrementAndGet();
            }
        }


        //}catch (InterruptedException e){
        //    e.printStackTrace();
        //}
    }

    public void evaluateProjectile(Projectile projectile, Map<Integer, Projectile> projectiles2remove){
        if (projectile.positionY < -projectile.imageHeight) {
            projectiles2remove.put(projectile.getId(), projectile);
            // And return it to the pool
            if(projectile instanceof Bullet){
                ((Bullet) projectile).getParent().releaseBullet((Bullet) projectile);
            }

        }
    }

}
