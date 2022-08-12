package com.example.starship;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Joystick {

    private final Paint outerCirclePaint, innerCirclePaint;

    private final int outerCircleCenterPositionX, outerCircleCenterPositionY;
    private int innerCircleCenterPositionX, innerCircleCenterPositionY;

    private final int outerCircleRadius_,innerCircleRadius_;

    private double joystickCenterToTouchDistance;

    private double actuatorX, actuatorY, angle;
    private boolean isPressed;

    /**
     * Initialize the joystick its center and the radius of both the borders and the joystick itself
     * @param centerPositionX center X of the Joystick
     * @param centerPositionY center Y of the Joystick
     * @param outerCircleRadius radius of the borders
     * @param innerCircleRadius radius of the joystick
     */
    public Joystick(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius) {
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;

        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;

        outerCircleRadius_ = outerCircleRadius;
        innerCircleRadius_ = innerCircleRadius;

        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.RED);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        angle =0;

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

    /**
     * check if the center of the joystick (your finger) is out of the joystick zone which reset the joystick and stops the movement
     * @param touchPositionX event X of the position
     * @param touchPositionY event Y of the position
     * @return if the joystick is out of its zone then the joystick isn't pressed anymore
     */
    public boolean isPressed(double touchPositionX, double touchPositionY)
    {
        joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(outerCircleCenterPositionX - touchPositionX, 2) + Math.pow(outerCircleCenterPositionY - touchPositionY, 2)
                );
        return joystickCenterToTouchDistance < outerCircleRadius_;
    }

    public void setIsPressed(boolean isPressed)
    {
            this.isPressed = isPressed;
    }

    public boolean getIsPressed()
    {
        return isPressed;
    }

    /**set the joystick to an actualized location in its zone
     *
     * @param touchPositionX event X of the position
     * @param touchPositionY event Y of the position
     */
    public void setActuator(double touchPositionX, double touchPositionY)
    {
        double deltaX, deltaY;
        deltaX = touchPositionX - outerCircleCenterPositionX;
        deltaY = touchPositionY -outerCircleCenterPositionY;

        //we take the global distance
        double deltaDistance = Math.sqrt( Math.pow(deltaX, 2)+ Math.pow(deltaY, 2) );

        /*
         *  checks if the distance of the joystick is smaller than the radius of the circle
         *  in which case the actualization is based on the radius instead
         */

        if (deltaDistance<outerCircleRadius_)
        {
            actuatorX = deltaX/outerCircleRadius_;
            actuatorY = deltaY/outerCircleRadius_;
        }
        else
        {
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }

    }

    //reset the joystick to the center
    public void resetActuator()
    {
        actuatorX = 0.0; actuatorY = 0.0;
    }

    public double getActuatorX() {
        return actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }

    /**
     * Moves the joystick visually
     */
    private void updateInnerCirclePosition()
    {
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX*outerCircleRadius_);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY*outerCircleRadius_);
    }

    public void update()
    {
        updateInnerCirclePosition();
    }

    private void setAngle(){
    if(actuatorY ==0 && actuatorX ==0)
    { angle = angle;} else { angle = Math.atan2(actuatorY, actuatorX)*57;}
    }

    public double getAngle(){ return angle;}
}
