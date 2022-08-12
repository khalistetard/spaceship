package com.example.starship;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import static com.example.starship.SpaceGameView.RatioX;
import static com.example.starship.SpaceGameView.RatioY;

//import static com.example.starship.ModeView.RX;
//import static com.example.tankwar.ModeView.RY;

public class StartStop {
    private Bitmap start, stop;
    private final int centerPositionX, centerPositionY;
    private int width, height;

    private boolean isStopped, isPressed;

    public StartStop(int centerPositionX, int centerPositionY, Resources res, int... view)
    {
        /*if (view[0] ==1)
        {
            RatioX = RX;
            RatioY = RY;
        }*/

        this.centerPositionX = centerPositionX;
        this.centerPositionY = centerPositionY;

        start = BitmapFactory.decodeResource(res, R.drawable.start);
        stop = BitmapFactory.decodeResource(res, R.drawable.pause);

        width = start.getWidth()/8;
        height = start.getHeight()/8;

        width /= 4;
        height /= 4;

        width = (int)(RatioX * width);
        height = (int)(RatioY * height);
        start = Bitmap.createScaledBitmap(start,width,height,false);

        stop = Bitmap.createScaledBitmap(stop,width,height,false);
        isStopped = false;
        isPressed = false;
    }

    public void draw(Canvas canvas, Paint paint)
    {
        if (isStopped)
        {
            canvas.drawBitmap(start,centerPositionX,centerPositionY,paint);
        }else{
            canvas.drawBitmap(stop,centerPositionX,centerPositionY,paint);
        }
    }

    //switch the button from resume to pause
    public void Switch()
    {
        isStopped = !isStopped;
    }

    public boolean getIsStopped(){return isStopped;}

    //check if we press the button
    public void isPressed(double touchedX, double touchedY)
    {
        if ( (int)touchedX >= centerPositionX - width
            && (int) touchedX <= centerPositionX + width
                && (int)touchedY >= centerPositionY - height
            && (int) touchedY <= centerPositionY + height )
        { isPressed = true; return; }
        isPressed = false;
    }

    public void setIsPressed(boolean b){isPressed = b;}
    public boolean getIsPressed(){return isPressed;}
}
