package com.example.starship;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Shoot {

    private Paint outerCirclePaint, innerCirclePaint;
    private int outerCircleCenterPositionX, outerCircleCenterPositionY;
    private int innerCircleCenterPositionX, innerCircleCenterPositionY;

    private int outerCircleRadius_,innerCircleRadius_;

    private double buttonCenterToTouchDistance;
    private boolean isPressed;

    public Shoot(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius)
    {
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;

        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;

        outerCircleRadius_ = outerCircleRadius;
        innerCircleRadius_ = innerCircleRadius;

        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.WHITE);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.rgb(178,34,34));
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public boolean isPressed(double touchPositionX, double touchPositionY)
    {
        buttonCenterToTouchDistance = Math.sqrt(
                Math.pow(innerCircleCenterPositionX - touchPositionX, 2) + Math.pow(innerCircleCenterPositionY - touchPositionY, 2)
        );
        return buttonCenterToTouchDistance < innerCircleRadius_;
    }

    public void setIsPressed(boolean isPressed)
    {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed()
    {
        return isPressed;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawCircle(
                outerCircleCenterPositionX,
                outerCircleCenterPositionY,
                outerCircleRadius_,
                outerCirclePaint);

        canvas.drawCircle(
                innerCircleCenterPositionX,
                innerCircleCenterPositionY,
                innerCircleRadius_,
                innerCirclePaint);
    }

    public void update()
    {
        if (isPressed)
        { outerCirclePaint.setColor(Color.GRAY); }
        else { outerCirclePaint.setColor(Color.WHITE); }
    }
}
