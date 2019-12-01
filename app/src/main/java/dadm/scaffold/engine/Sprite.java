package dadm.scaffold.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import dadm.scaffold.space.SpaceShipPlayer;

public abstract class Sprite extends GameObject {

    protected double positionX;
    protected double positionY;
    protected double rotation = 90;

    protected double pixelFactor;

    private final Bitmap bitmap;
    protected final int imageHeight;
    protected final int imageWidth;

    private final Matrix matrix = new Matrix();

    private int collisionFactor;

    protected Sprite (GameEngine gameEngine, int drawableRes) {
        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);

        this.pixelFactor = gameEngine.pixelFactor;

        this.imageHeight = (int) (spriteDrawable.getIntrinsicHeight() * this.pixelFactor);
        this.imageWidth = (int) (spriteDrawable.getIntrinsicWidth() * this.pixelFactor);

        this.bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
    }

    public void setCollisionFactor(int collisionFactor){this.collisionFactor = collisionFactor;}

    public int getCollisionFactor(){return  this.collisionFactor;}

    public double getPositionX(){return this.positionX;}
    public double getPositionY(){return this.positionY;}

    @Override
    public void onDraw(Canvas canvas) {
        if (positionX > canvas.getWidth()
                || positionY > canvas.getHeight()
                || positionX < - imageWidth
                || positionY < - imageHeight) {
            return;
        }
        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor);
        matrix.postTranslate((float) positionX, (float) positionY);
        matrix.postRotate((float) rotation, (float) (positionX + imageWidth/2), (float) (positionY + imageHeight/2));
        canvas.drawBitmap(bitmap, matrix, null);
    }

    // Handle collision
    public boolean intersect(Sprite other) {
        int maxRadiusToCollide = this.collisionFactor + other.getCollisionFactor();
        double x = (this.positionX+ imageWidth/2) - (other.getPositionX()+other.imageWidth/2);
        double y = (this.positionY+imageHeight/2) - (other.getPositionY()+other.imageHeight/2);
        return (maxRadiusToCollide > (Math.pow(x, 2) + Math.pow(y, 2)));
    }

    public abstract void doTheThing(Sprite sprite);
}
