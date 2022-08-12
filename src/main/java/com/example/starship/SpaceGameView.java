package com.example.starship;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@SuppressLint("ViewConstructor")
public class SpaceGameView extends SurfaceView implements Runnable{

    //had to change context into activity to create the opening page and be able to navigate to it
    private SpaceActivity activity;

    // This is our thread
    private Thread gameThread = null;

    // Our SurfaceHolder to lock the surface before we draw our graphics
    private SurfaceHolder ourHolder;

    // A boolean which we will set and unset
    // when the game is running- or not.
    private volatile boolean playing;

    // Game is paused at the start
    private boolean paused = true;

    private Paint paint;
    //Moving background initialization
    private Background background1, background2;

    // This variable tracks the game frame rate
    private long fps;

    // The size of the screen in pixels
    private final int screenX,screenY;
    public static float RatioX, RatioY;

    // The score
    public int score = 0;

    //Creation of stockage
    private final SharedPreferences prefs;

    // sound of the game
    private MediaPlayer player = null;

    //Buttons
    private StartStop ss;
    private Joystick joystick;
    private Shoot shoot;

    //timers needed in different part of the program
    long shotStartTimer = 0; //starts the holding of the shot
    long shotTimer = 0; //takes the difference of both timers for the shot

    // Lives
    private int lives = 4;
    Spaceship spaceShip;

    //Spawn Manager
    Random spawn;
    //Enemies and projectiles
    private List<Projectiles> projectiles;
    private List<Alien> aliens;
    private List<Portal> portals;

    // This special constructor method runs
    public SpaceGameView(SpaceActivity activity, int x, int y) {

        // The next line of code asks the
        // SurfaceView class to set up our object.
        // How kind.
        super(activity);

        // Make a globally available copy of the context so we can use it in another method
        this.activity = activity;
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        //Initialization of music
        if (!prefs.getBoolean("isMute", false)) {
            player = MediaPlayer.create(activity, R.raw.no_copyright_arcade);
            player.setLooping(true);
            player.setVolume(1.0f, 1.0f);
        }

        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;

        //to make the game viable on all plateforms
        RatioX = 1920f / screenX;
        RatioY = 1080f / screenY;

        paint = new Paint();
        paint.setTextSize(70);
        paint.setColor(Color.WHITE);

        initLevel();
    }


    /**
     * Initialize every used objects, variables or lists in the game
     */
    private void initLevel(){
        // Initialization of score and time
        score = 0;

        //moving background initialization
        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX + screenX/2, screenY, getResources());

        //buttons initialization
        joystick = new Joystick(screenX / 12, screenY * 8 / 10, 80, 50);
        shoot = new Shoot(screenX * 11 / 12, screenY * 8 / 10, 70, 60);
        ss = new StartStop(screenX * 11 / 12, screenY / 10, getResources(),0);

        //objects initialization
        spaceShip = new Spaceship(this.getContext(), screenX, screenY);

        //projectiles initialization
        projectiles = new ArrayList<>();

        //enemies initialization
        aliens = new ArrayList<>();

        //portals initialization
        portals = new ArrayList<>();

        //spawn manager initialization
        spawn = new Random();


    }


    @Override
    public void run() {
        while (playing) {
        //score = 10;
            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Update the frame
            if(!paused || !playing){
                update();
            }

            // Draw the frame
            draw();

            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            // This is used to help calculate the fps
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }

        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MediaPlayer shooting;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Case managing when a button is pressed
                if (paused)
                {
                    paused = false;
                }
                //start stop button check condition launches the switch of isStopped if it is pressed
                ss.isPressed(event.getX(), event.getY());
                if (ss.getIsPressed()) {
                    ss.Switch();
                }

                //The fact there is two times the shooting and joystick checks one inside of the other is to allow to use the 2 buttons at the same time
                // (same for the start stop button)
                if (shoot.isPressed(event.getX(), event.getY()) && playing) {
                    if (joystick.isPressed(event.getX(), event.getY())) {
                        joystick.setIsPressed(true);
                    }
                    shoot.setIsPressed((true));
                    //if the shooting button is pressed we start the timer for the bullet
                    shotStartTimer = System.currentTimeMillis();
                }

                if (joystick.isPressed(event.getX(), event.getY())) {
                    if (shoot.isPressed(event.getX(), event.getY()) && playing) {
                        shoot.setIsPressed((true));
                        shotStartTimer = System.currentTimeMillis();
                    }
                    joystick.setIsPressed(true);
                    ss.isPressed(event.getX(), event.getY());
                    if (ss.getIsPressed()) {
                        ss.Switch();
                    }
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                //Case managing the movements in the app
                if (joystick.getIsPressed()) {
                    joystick.setActuator(event.getX(), event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                //Case managing when the button is no longer pressed
                if (ss.getIsPressed()
                ) {
                    //if the boolean inside the button is true then we pause the game otherwise we resume
                    ss.setIsPressed(false);
                    if (ss.getIsStopped()) {
                        pause();
                    }
                    if (!ss.getIsStopped()) {
                        resume();
                    }
                }

                if (joystick.getIsPressed()) {
                    joystick.setIsPressed(false);
                    joystick.resetActuator();

                    if (shoot.getIsPressed()) {
                        if (!prefs.getBoolean("isMute", false)) {
                            shooting = MediaPlayer.create(activity, R.raw.kaboom);
                            shooting.start();
                        }

                        //when we release the shooting button we release the projectile with its timer that will change the size of it
                        shotTimer = System.currentTimeMillis() - shotStartTimer;
                        //Log.e("error ", String.valueOf(shotTimer));
                        if (shotTimer<1500) {
                            Laser laser = new Laser(getResources(), spaceShip);
                            projectiles.add(laser);
                        }
                        if ( (shotTimer >= 1500 && shotTimer <3000) || (shotTimer >= 3000 && spaceShip.getNb_rockets()<=0) ) {
                            Bullet bullet = new Bullet(getResources(), spaceShip);
                            projectiles.add(bullet);
                        }
                        if (shotTimer >= 3000 && spaceShip.getNb_rockets()>0) {
                            Rocket rocket = new Rocket(getResources(), spaceShip);
                            projectiles.add(rocket);
                        }
                        shoot.setIsPressed(false);
                    }
                }

                if (shoot.getIsPressed()) {

                    if (!prefs.getBoolean("isMute", false)) {
                        shooting = MediaPlayer.create(activity, R.raw.kaboom);
                        shooting.start();
                    }

                    //when we release the shooting button we release the bullet with its timer that will change the size of it
                    shotTimer = System.currentTimeMillis() - shotStartTimer;
                    //Log.e("error ", String.valueOf(shotTimer));
                    if (shotTimer<1500) {
                        Laser laser = new Laser(getResources(), spaceShip);
                        projectiles.add(laser);
                    }
                    if ( (shotTimer >= 1500 && shotTimer <3000) || (shotTimer >= 3000 && spaceShip.getNb_rockets()<=0) ) {
                        Bullet bullet = new Bullet(getResources(), spaceShip);
                        projectiles.add(bullet);
                    }
                    if (shotTimer >= 3000 && spaceShip.getNb_rockets()>0) {
                        Rocket rocket = new Rocket(getResources(), spaceShip);
                        projectiles.add(rocket);
                    }
                    shoot.setIsPressed(false);

                    if (joystick.getIsPressed()) {
                        joystick.setIsPressed(false);
                        joystick.resetActuator();
                    }
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void update(){
        joystick.update();
        shoot.update();

        int spawn_random = spawn.nextInt(1000);
        if (spawn_random % 2 ==0){score ++;}

        if(spawn_random >= 200  && spawn_random <= 205 )
        {
            Bonus bonus = new Bonus(screenX, screenY, spaceShip, getResources());
            portals.add(bonus);
        }
        if(spawn_random >= 300  && spawn_random <= 305)
        {
            Malus malus = new Malus(screenX, screenY, spaceShip, getResources());
            portals.add(malus);
        }

        if( ( spawn_random <= 10) ||  spawn_random > 990)
        {
            BasicAlien alien= new BasicAlien(getResources(), screenX, screenY);
            aliens.add(alien);
        }
        if( (spawn_random >= 500  && spawn_random <= 502) || (spawn_random >= 630  && spawn_random <= 632))
        {
            StrongAlien alien= new StrongAlien(getResources(), screenX, screenY);
            aliens.add(alien);
        }
        /*if( (spawn_random >= 700  && spawn_random <= 701) || (spawn_random == 763 ))
        {
            Boss alien= new Boss(getResources(), screenX, screenY);
            aliens.add(alien);
        }*/


        spaceShip.update(fps, joystick, screenX, screenY);

        //Creation of the trash list for projectiles
        List<Projectiles> trashB = new ArrayList<>();

        //If the bullet leaves the screen it is sent to the trash list to be destroyed AKA ScreenCheck
        for (Projectiles projectile: projectiles) {
            projectile.update(fps);
            if (projectile.getX() > screenX) {
                projectile.setDestroyed(true);
                trashB.add(projectile);
                projectile.x += 50 * RatioX;
            }
            if (projectile.getX() < 1) {
                projectile.setDestroyed(true);
                trashB.add(projectile);
                projectile.x -= 50 * RatioX;
            }
            if (projectile.getY() > screenY) {
                projectile.setDestroyed(true);
                trashB.add(projectile);
                projectile.y += 50 * RatioY;

            }
            if (projectile.getY() < 1) {
                projectile.setDestroyed(true);
                trashB.add(projectile);
                projectile.y -= 50 * RatioY;
            }
        }

        for (Alien alien : aliens)
        {
            alien.update(fps, screenY, spaceShip);
        }

        //checkCollisions
        for (Alien alien : aliens)
        {
            if (!alien.getDead()) {
                if (alien.killer(spaceShip)) {
                    lives -= alien.getStrength();
                    alien.setDeath(true);
                }
                for (Projectiles projectile : projectiles)
                {
                    if (alien.killed(projectile)){ score += alien.points;}
                }
            }

        }

        //Creation of the trash list for portals
        List<Portal> trashP = new ArrayList<>();
        for (Portal portal: portals)
        {
            if (portal.gotHit(spaceShip))
            {
                if (portal.getType() == 2){ score += portal.points;}
                if (portal.getType() == 1){ score -= portal.points; lives--;}
                trashP.add(portal);
            }

        }

        //destruction of the projectiles that have been destroyed
        for (Projectiles projectile : trashB) {
            projectiles.remove(projectile);
        }

        //destruction of the portals that have been traversed
        for ( Portal portal: trashP)
        {
            portals.remove(portal);
        }

        //Creation of the trash list for aliens
        List<Alien> trashA = new ArrayList<>();

        for (Alien alien: aliens)
        {
            if (alien.getDead()){trashA.add(alien);}
        }

        //destruction of the aliens that are dead
        for (Alien alien: trashA)
        {
            aliens.remove(alien);
        }
    }

    private void draw(){
        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            // A Canvas and a Paint object
            Canvas canvas = ourHolder.lockCanvas();

            //Changed background
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);
            // Draw the background color
            //canvas.drawColor(Color.argb(255, 26, 128, 182));

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255,  255, 255, 255));
            //score = 20;
            //  draw the defender
            canvas.drawBitmap(spaceShip.getBitmap(),  spaceShip.getX()-spaceShip.getLength()/2, spaceShip.getY() , paint);

            //score = 30;


            //drawing enemies and bullets
            for (Alien alien : aliens) {
               if (!alien.getDead()) {alien.draw(canvas, paint);}
            }
            for (Projectiles projectile: projectiles) {
                if (!projectile.isDestroyed()) {projectile.draw(canvas, paint);}
            }

            for (Portal portal: portals){
                portal.draw(canvas, paint);
            }

            /* drawing the buttons
             * drawn after the game objects to appear in front of them
             */
            joystick.draw(canvas);
            shoot.draw(canvas);

            // Draw the score and remaining lives
            // Change the brush color and size
            paint.setColor(Color.argb(255,  255, 255, 0));
            paint.setTextSize(50);
            canvas.drawText("Score: " + score + "   Lives: " + lives + "   Rockets: " + spaceShip.getNb_rockets(), 10,50, paint);

            if (paused) {
                paint.setColor(Color.argb(255,  255, 255, 255));
                paint.setTextSize(90);
                canvas.drawText("  START  ", (float) (screenX / 2.5), (float) (screenY / 2), paint);
            }else { ss.draw(canvas, paint);}

            if (lives <= 0) {
                if (!prefs.getBoolean("isMute", false)) {
                    player.stop();
                    MediaPlayer dead = MediaPlayer.create(activity, R.raw.life_lost);
                    dead.start();
                }
                playing = false;
                paint.setColor(Color.RED);
                paint.setTextSize(90);
                canvas.drawText(" GAME OVER ", (float) (screenX / 2.5), (float) (screenY / 2), paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHS();
                waitBeforeExiting();
                if (!prefs.getBoolean("isMute", false)) {
                    player.release();
                }
                return;
            }
            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * wait a few seconds before exiting the game
     */
    private void waitBeforeExiting() {
        try {
            Thread.sleep(4000);

            activity.startActivity(new Intent(activity, ScreenActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the score as new HighScore if it is better than the current Highscore
     */
    private void saveIfHS() {
        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }
    }


    // If SpaceGameActivity is paused/stopped
    // shutdown our thread.
    public void pause() {
        playing = false;
        try {
            if (!prefs.getBoolean("isMute", false)) {
                player.pause();
            }
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }



    // If SpaceActivity is started then
    // start our thread.
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
        if (!prefs.getBoolean("isMute", false)) {
            player.start();
        }
    }



}  // end class
