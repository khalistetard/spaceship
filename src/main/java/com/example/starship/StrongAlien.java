package com.example.starship;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public class StrongAlien extends BasicAlien{
    int random;

    /**
     * Aliens with built up speed and a bit more strength and lives, they give more points
     * @param resources for the alien visual
     * @param screenX for the size of the alien
     * @param screenY for the size of the alien
     */
    StrongAlien(Resources resources, int screenX, int screenY){
        super(resources, screenX, screenY);
        Random r= new Random();
        speed = 200;
        lives = 2 ;
        points = 100;
        strength = 2;
        random = r.nextInt(2);

        body = BitmapFactory.decodeResource(resources, R.drawable.strongalien);
        body = Bitmap.createScaledBitmap(body, (int) (length), (int) (height),false);
    }

    @Override
    public void draw(Canvas canvas, Paint p)
    {
        super.draw(canvas, p);
    }

    /**
     *  This Alien goes from right to left in a Z shape from top to bottom and dies reaching the left border of the phone
     *  if they kill or get killed
     */
    @Override
    public void update(long fps, int screenY, Spaceship spaceship)
    {

        if (random ==0){
            y -= speed /fps;
        }
        if (random ==1)
        {
            y += speed/fps;
        }
        if (y >= screenY-height){ random = 0;}
        if (y <= 0){ random = 1;}
        super.update(fps,screenY, spaceship);
    }
}
