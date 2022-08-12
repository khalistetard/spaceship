package com.example.starship;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Laser extends Projectiles{

    /**
     * Fast projectile with low damage
     * @param resources image of the projectile
     * @param spaceship retrieve the spaceship data to launch the projectiles from its position and its orientation
     *                  and manage the size of the projectiles compared to the spaceship
     */
    Laser(Resources resources, Spaceship spaceship)
    {
        super(spaceship);
        projectile = BitmapFactory.decodeResource(resources, R.drawable.laser);
        // Failed attempt at 360 rotation : projectile = RotateBitmap(projectile,angle);
        if (orientation == 1) {
            projectile = RotateBitmap(projectile,180);
        }
        if(orientation == 4) {
            projectile = RotateBitmap(projectile,90);
        }
        if(orientation == 3) {
            projectile = RotateBitmap(projectile,-90);
        }
        projectile = Bitmap.createScaledBitmap(projectile, (int) (length), (int) (height),false);
        damage =1;
    }
    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(projectile,x-length/2,y,paint);
    }

    @Override
    public void update(long fps){
        //Attempt at rotating 360 the projectiles
        /*double maxSpeed = 500;
        if (velocityX<0.5f){velocityX *=2;}
        if (velocityY<0.5f){velocityY *=2;}

        x += velocityX*maxSpeed/fps;
        y += velocityY*maxSpeed/fps;*/

        double maxSpeed =30;
        if (orientation == 1) {
            x -= maxSpeed;
        }
        if(orientation == 2) {
            x += maxSpeed;
        }
        if(orientation == 4) {
            y += maxSpeed;
        }
        if(orientation == 3) {
            y -= maxSpeed;
        }
    }
}
