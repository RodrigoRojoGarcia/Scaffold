package dadm.scaffold.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dadm.scaffold.space.Projectile;
import dadm.scaffold.space.SpaceShipPlayer;
import dadm.scaffold.space.Spaceship;

public class StandardGameView extends View implements GameView {


    private Map<Integer, Projectile> projectiles;
    private Map<Integer, Spaceship> spaceships;
    private SpaceShipPlayer player;


    public StandardGameView(Context context) {
        super(context);
        this.projectiles = new ConcurrentHashMap<>();
        this.spaceships = new ConcurrentHashMap<>();
    }

    public StandardGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.projectiles = new ConcurrentHashMap<>();
        this.spaceships = new ConcurrentHashMap<>();
    }

    public StandardGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.projectiles = new ConcurrentHashMap<>();
        this.spaceships = new ConcurrentHashMap<>();
    }

    @Override
    public void draw() {
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        synchronized (projectiles) {
            for(Projectile proj : projectiles.values()){
                proj.onDraw(canvas);
            }
        }
        synchronized (spaceships){
            for(Spaceship sp : spaceships.values()){
                sp.onDraw(canvas);
            }
        }
        player.onDraw(canvas);
    }

    public void setProjectiles(Map<Integer, Projectile> projectiles){this.projectiles = projectiles;}
    public void setSpaceships(Map<Integer, Spaceship> spaceships){this.spaceships = spaceships;}
    public void setPlayer(SpaceShipPlayer player){this.player = player;}
}
