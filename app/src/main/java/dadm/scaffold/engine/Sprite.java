package dadm.scaffold.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public abstract class Sprite extends GameObject {

    protected double positionX;
    protected double positionY;
    protected double rotation;

    protected double pixelFactor;

    private final Bitmap bitmap;
    protected final int imageHeight;
    protected final int imageWidth;

    private final Matrix matrix = new Matrix();

    private int collisionFactor;

    protected Sprite (int drawableRes) {
        Resources r = GameEngine.Instance.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);

        this.pixelFactor = GameEngine.Instance.pixelFactor;

        this.imageHeight = (int) (spriteDrawable.getIntrinsicHeight() * this.pixelFactor);
        this.imageWidth = (int) (spriteDrawable.getIntrinsicWidth() * this.pixelFactor);

        this.bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
    }


    public void setCollisionFactor(int radius){
        this.collisionFactor = radius;
    }

    public int getCollisionFactor(){return this.collisionFactor;}

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


    public boolean checkCollision(Sprite other){
        int maxRadiusToCollide = this.collisionFactor + other.getCollisionFactor();
        double x = this.positionX - other.getPositionX();
        double y = this.positionY - other.getPositionY();
        return (maxRadiusToCollide > (Math.pow(x, 2) + Math.pow(y, 2)));
    }




    @Override
    public void onUpdate(long elapseMillis){

    }

}
