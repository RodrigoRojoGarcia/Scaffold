package dadm.scaffold.engine;

import android.content.Context;

import java.util.List;
import java.util.Map;

import dadm.scaffold.space.Projectile;
import dadm.scaffold.space.SpaceShipPlayer;
import dadm.scaffold.space.Spaceship;

public interface GameView {

    void draw();

    void setProjectiles(Map<Integer, Projectile> projectiles);
    void setSpaceships(Map<Integer, Spaceship> spaceships);
    void setPlayer(SpaceShipPlayer player);

    int getWidth();

    int getHeight();

    int getPaddingLeft();

    int getPaddingRight();

    int getPaddingTop();

    int getPaddingBottom();

    Context getContext();
}
