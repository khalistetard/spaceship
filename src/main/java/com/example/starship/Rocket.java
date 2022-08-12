package com.example.starship;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Rocket extends Projectiles {

    /**
     * High damage projectile, slow movement
     * @param resources image of the projectile
     * @param spaceship retrieve the spaceship data to launch the projectiles from its position and its orientation
     *                  and manage the size of the projectiles compared to the spaceship
     */
    Rocket(Resources resources, Spaceship spaceship)
    {
        super(spaceship);
        projectile = BitmapFactory.decodeResource(resources, R.drawable.rocket);

        if (orientation == 1) {
            projectile = RotateBitmap(projectile,-90);
        }
        if(orientation == 2) {
            projectile = RotateBitmap(projectile,90);
        }
        if(orientation == 3) {
            projectile = RotateBitmap(projectile,180);
        }
        projectile = Bitmap.createScaledBitmap(projectile, (int) (length), (int) (height),false);
        damage = 4;
    }
    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(projectile,x-length/2,y,paint);
    }

    @Override
    public void update(long fps){
        double maxSpeed =10;
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
        /*if(orientation == 5) {
            this.centerPositionX += maxSpeed * RatioX;
            this.centerPositionY -= maxSpeed * RatioY;
        }
        if(orientation == 6) {
            this.centerPositionX += maxSpeed * RatioX;
            this.centerPositionY += maxSpeed * RatioY;
        }
        if(orientation == 7) {
            this.centerPositionX -= maxSpeed * RatioX;
            this.centerPositionY += maxSpeed * RatioY;
        }
        if(orientation == 8) {
            this.centerPositionX -= maxSpeed * RatioX;
            this.centerPositionY -= maxSpeed * RatioY;
        }*/
    }
}
