package com.example.starship;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

public abstract class Portal {

    RectF rect;
    protected float x, y, length, height;
    protected Bitmap portal;
    protected int points;
    protected int type;

    /**
     * Abstract Base of Portal creations
     * @param screenX for the size of the portal
     * @param screenY for the size of the portal
     * @param spaceship to avoid initializing the portal at the spaceship place
     */
    Portal(int screenX, int screenY,Spaceship spaceship)
    {
        rect = new RectF();
        Random random = new Random();
        length = (float)screenX/20;
        height = (float)screenY/15;
        type = 0; //no type yet

        x = random.nextInt(screenX);
        y = random.nextInt(screenY);

        for(int i=0; i<10 && (x>= spaceship.rect.top && x<= spaceship.rect.bottom && y >= spaceship.rect.left && y <= spaceship.rect.right); i++) {
            x = random.nextInt(screenX);
            y = random.nextInt(screenY);
        }

        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;
    }

    public abstract void draw(Canvas canvas, Paint paint);

    public abstract boolean gotHit(Spaceship spaceship);

    public int getType(){return type;}

    public boolean entered(Spaceship spaceship) {
        return ((rect.top >= spaceship.rect.top+10
                && rect.top <= spaceship.rect.bottom-10
                && rect.left>= spaceship.rect.left+10
                && rect.left <= spaceship.rect.right-10 )
                ||(rect.bottom >= spaceship.rect.top+10
                && rect.bottom <= spaceship.rect.bottom-10
                && rect.left>= spaceship.rect.left+10
                && rect.left <= spaceship.rect.right-10 )
                || (rect.top >= spaceship.rect.top+10
                && rect.top <= spaceship.rect.bottom-10
                && rect.right>= spaceship.rect.left+10
                && rect.right <= spaceship.rect.right-10 )
                || (rect.bottom >= spaceship.rect.top+10
                && rect.bottom <= spaceship.rect.bottom-10
                && rect.right>= spaceship.rect.left+10
                && rect.right <= spaceship.rect.right-10 )
        );
    }
}
