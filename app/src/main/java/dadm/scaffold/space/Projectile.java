package dadm.scaffold.space;

import dadm.scaffold.engine.Sprite;

public abstract class Projectile extends Sprite {


    private int id;

    public Projectile(int sprite){
        super(sprite);
    }

    public void setId(int id) {this.id = id;}
    public int getId(){return this.id;}

    public abstract void doTheThing(SpaceShipPlayer player);

}
