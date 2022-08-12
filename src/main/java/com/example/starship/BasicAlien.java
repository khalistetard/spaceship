package com.example.starship;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BasicAlien extends Alien {

    /**
     * Weakest aliens, slow, only 1 lives and strength, they do not give many points
     * @param resources for the alien visual
     * @param ScreenX for the size of the alien
     * @param ScreenY for the size of the alien
     */
    BasicAlien(Resources resources, int ScreenX, int ScreenY){
        super(ScreenX,ScreenY);

        speed = 150;
        lives = 1;
        points = 50;
        strength = 1;

        body = BitmapFactory.decodeResource(resources, R.drawable.alien);
        body = Bitmap.createScaledBitmap(body, (int) (length), (int) (height),false);
    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(body,x-length/2,y,paint);
    }

    /**
     *  This Alien goes from right to left and dies reaching the left border of the phone if they kill or get killed
     */
    @Override
    public void update(long fps, int screenY, Spaceship spaceship){

        x -= speed /fps;
        if (x<=0)
        {
            dead = true;
        }

        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;
    }


}
