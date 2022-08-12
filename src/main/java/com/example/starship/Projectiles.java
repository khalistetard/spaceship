package com.example.starship;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public abstract class Projectiles {

    protected float x,y,length,height;

    //protected float velocityX, velocityY, angle;

    protected int damage,orientation;
    protected Bitmap projectile;
    protected boolean destroyed;

    /**
     * Abstract Creation of the Projectiles base
     * @param spaceship retrieve the spaceship data to launch the projectiles from its position and its orientation
     *                  and manage the size of the projectiles compared to the spaceship
     */
    Projectiles(Spaceship spaceship)
    {
        length = spaceship.getLength();
        height = spaceship.getHeight();
        orientation = spaceship.getOrientation();

        /* Try at rotating 360 the projectiles without working, the data from joystick is permanently updated and gets to 0 when I shoot
        velocityX = spaceship.getSpecialX();
        velocityY = spaceship.getSpecialY();

        angle = spaceship.getAngle();*/

        x = spaceship.getX();
        y = spaceship.getY();
        damage =0;
    }

    public abstract void draw(Canvas canvas, Paint paint);
    public abstract void update(long fps);

    //Retrieve coordinates outside the class
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public void setDestroyed(boolean destroyed) { this.destroyed = destroyed; }
    public boolean isDestroyed(){ return destroyed; }

    /**
     * Rotates the image
     * @param source the initial bitmap we want to rotate
     * @param angle the angle we want to rotate it
     * @return
     */
    public Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.setRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
