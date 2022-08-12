package com.example.starship;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bonus extends Portal{

    /**
     * Benefic portals, adding rockets and giving points
     * @param screenX for the size of the portal
     * @param screenY for the size of the portal
     * @param spaceship  to avoid initializing the portal at the spaceship place
     * @param resources image of the portal
     */
    Bonus(int screenX, int screenY, Spaceship spaceship, Resources resources)
    {
        super(screenX, screenY, spaceship);
        type = 2; //bonus type
        points = 150;
        portal = BitmapFactory.decodeResource(resources, R.drawable.spiral);
        portal = Bitmap.createScaledBitmap(portal, (int) (length), (int) (height),false);

    }
    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(portal,x-length/2,y,paint);
    }

    @Override
    public boolean gotHit(Spaceship spaceship)
    {
        if (entered(spaceship)) {
            spaceship.addRockets();
            return true;
        }
        return false;

    }

}
