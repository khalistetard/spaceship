package com.example.starship;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Malus extends Portal{

    /**
     * Damaging portal
     * @param screenX for the size of the portal
     * @param screenY for the size of the portal
     * @param spaceship to avoid initializing the portal at the spaceship place
     * @param resources image of the portal
     */
    Malus(int screenX, int screenY, Spaceship spaceship, Resources resources)
    {
        super(screenX, screenY, spaceship);
        portal = BitmapFactory.decodeResource(resources, R.drawable.blackhole);
        portal = Bitmap.createScaledBitmap(portal, (int) (length), (int) (height),false);
        type = 1; //malus type
        points = 1000;

    }
    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(portal,x-length/2,y,paint);
    }

    @Override
    public boolean gotHit(Spaceship spaceship)
    {
        return entered(spaceship);
    }
}
