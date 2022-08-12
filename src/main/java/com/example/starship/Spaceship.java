package com.example.starship;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
import android.graphics.RectF;

public class Spaceship {

    RectF rect;
    private Bitmap bitmap;
    private Bitmap bitmapup;
    private Bitmap bitmapleft;
    private Bitmap bitmapright;
    private Bitmap bitmapdown;
    final private float height;
    final private float length;
    private float x;
    private float y;

    /* Failed attempt at 360 rotation
    private float angle; //last angle direction for projectiles
    private float SpecialVX, SpecialVY; // Last direction for projectiles*/

    //private float SpaceShipSpeed;
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    public final int UP = 3;
    public final int DOWN = 4;

    ///maybe more movement than this
    private int SpaceShipMoving = STOPPED;
    private int orientation;
    final private int shipSpeed;

    //number of rockets in possession
    private int nb_rockets;

    public Spaceship(Context context, int screenX, int screenY){

        rect = new RectF();

        length = (float)screenX/10;
        height = (float)screenY/10;

        x = (float) screenX / 2;
        y = (float) screenY / 2;

        //angle = 0;
        shipSpeed = 350;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipright);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (length), (int) (height),false);

        bitmapup = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipup);
        bitmapup = Bitmap.createScaledBitmap(bitmapup, (int) (length), (int) (height),false);

        bitmapright = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipright);
        bitmapright = Bitmap.createScaledBitmap(bitmapright, (int) (length), (int) (height),false);

        bitmapleft = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipleft);
        bitmapleft = Bitmap.createScaledBitmap(bitmapleft, (int) (length), (int) (height),false);

        bitmapdown = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipdown);
        bitmapdown = Bitmap.createScaledBitmap(bitmapdown, (int) (length), (int) (height),false);


        nb_rockets = 0;
    }

    public void setMovementState(int state){
        SpaceShipMoving = state;
    }

    public void move(long fps, Joystick joystick) {
        //movement of the spaceship
        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (length),
                (int) (height),
                false);


        double velocityX = joystick.getActuatorX() * shipSpeed;
        double velocityY = joystick.getActuatorY() * shipSpeed;

        x += velocityX /fps;
        y += velocityY /fps;
        if (velocityX == 0 && velocityY == 0)
        { setMovementState(0); }
        if (velocityX < 0 && velocityY >= -0.3 * shipSpeed && velocityY <= 0.3 * shipSpeed) {
            setMovementState(1);
        }
        if (velocityX > 0 && velocityY >= -0.3 * shipSpeed && velocityY <= 0.3 * shipSpeed) {
            setMovementState(2);
        }
        if (velocityY > 0 && velocityX >= -0.3 * shipSpeed && velocityX <= 0.3 * shipSpeed) {
            setMovementState(4);
        }
        if (velocityY < 0 && velocityX >= -0.3 * shipSpeed && velocityX <= 0.3 * shipSpeed) {
            setMovementState(3);
        }

        /*if (velocityX > 0.3 * shipSpeed && velocityY < -0.3 * shipSpeed) {
            setOrientation(5);
        }
        if (velocityX > 0.3 * shipSpeed && velocityY > 0.3 * shipSpeed) {
            setOrientation(6);
        }
        if (velocityX < -0.3 * shipSpeed && velocityY > 0.3 * shipSpeed) {
            tank.setOrientation(7);
        }
        if (velocityX < -0.3 * shipSpeed && velocityY < -0.3 * shipSpeed) {
            tank.setOrientation(8);
        }*/

        if (SpaceShipMoving == LEFT)
        { bitmap = bitmapleft; setOrientation(1); }
        if (SpaceShipMoving == RIGHT)
        {bitmap = bitmapright; setOrientation(2); }
        if (SpaceShipMoving == UP)
        {bitmap = bitmapup; setOrientation(3); }
        if (SpaceShipMoving == DOWN)
        {bitmap = bitmapdown; setOrientation(4); }


    }

    public void update(long fps, Joystick joystick, int screenX, int screenY){
        /* Failed attempt at 360 rotation
        if ((float)joystick.getAngle() != angle)
        {
            angle = (float)joystick.getAngle();
            RotateBitmap(bitmap, angle);
            SpecialVX = (float)joystick.getActuatorX();
            SpecialVY = (float)joystick.getActuatorY();
        }*/

        move(fps, joystick);

        //ScreenWrap
        if (x > screenX) {
            x = screenX;
        }
        if (x < 1) {
            x = 1;
        }
        if (y > screenY-height) {
            y= screenY-height;
        }
        if (y < 1) {
            y = 1;
        }
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;

    }


    public RectF getRect(){
        return rect;
    }

    //Retrieve image outside the class
    public Bitmap getBitmap(){

        return bitmap;
    }

    public void addRockets(){nb_rockets++;}
    public int getNb_rockets(){return nb_rockets;}

    //orientation of the spaceship for shooting
    public void setOrientation(int x){ orientation = x;}
    public int getOrientation(){ return orientation;}

    //Retrieve coordinates outside the class
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    /*public float getSpecialX(){
        return SpecialVX;
    }
    public float getSpecialY(){
        return SpecialVY;
    }

    public float getAngle(){
        return angle;
    }*/

    //Retrieve size outside the class
    public float getLength(){
        return length;
    }
    public float getHeight(){
        return height;
    }

    /* Failed attempt at 360 rotation
    public Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.setRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }*/


}
