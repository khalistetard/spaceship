package com.example.starship;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Boss extends Alien{

    /**
     * Boss Alien that follows you until they die. These do a lot of damage (almost kill you),
     * have  lot of lives and gives a lot of points. They are slow so take advantage of that to survive !
     * @param resources
     * @param ScreenX
     * @param screenY
     */
    Boss(Resources resources, int ScreenX, int screenY){
        super(ScreenX, screenY);

        speed = 100;
        lives = 4;
        points = 400;
        strength = 3;

        body = BitmapFactory.decodeResource(resources, R.drawable.alienboss);
        body = Bitmap.createScaledBitmap(body, (int) (length), (int) (height),false);

    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(body,x-length/2,y,paint);
    }


    @Override
    public void update(long fps, int screenY, Spaceship spaceship){
        x -= spaceship.getX();
        y -= spaceship.getY();

        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;
    }

}
