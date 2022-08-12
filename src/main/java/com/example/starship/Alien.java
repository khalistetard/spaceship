package com.example.starship;

import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public abstract class Alien {

    RectF rect;

    protected float x, y, length, height, speed;
    protected int lives, points, strength;
    protected boolean dead;
    protected Bitmap body;

    /**
     * Abstract Creation of Alien base
     * @param screenX for the size of the alien
     * @param screenY for the size of the alien
     */
    Alien (int screenX, int screenY){

        Random r = new Random();
        length = (float)screenX/10;
        height = (float)screenY/10;

        rect = new RectF();

        dead = false;

        x = screenX;
        y = r.nextInt(screenY);
    }

    public abstract void draw(Canvas canvas, Paint paint);

    public abstract void update(long fps, int screenY, Spaceship spaceship);

    public boolean killed(Projectiles projectile){

        if (shot(projectile) && !projectile.isDestroyed())
        {
            lives -= projectile.damage;
            projectile.setDestroyed(true);
        }
        if (lives <=0)
        { dead = true; }

        return dead;
    }

    public boolean getDead(){return dead;}
    public int getStrength(){return strength;}
    public void setDeath(boolean d){dead = d;}


    /**
     * Please make it he is shot, please make it he is shot !
     * @param projectile the projectile we test
     * @return if the alien is shot or not
     */
    public boolean shot(Projectiles projectile) {
        return (projectile.x + projectile.length / 2 >= this.x+5)
                && (projectile.y + projectile.height / 2 >= this.y +5)
                && (projectile.x + projectile.length / 2 <= this.x + this.length-5)
                && (projectile.y + projectile.height / 2 <= this.y + this.height-5);
    }

    public RectF getRect(){
        return rect;
    }

    /**
     *
     * @param spaceship test the spaceship
     * @return did the alien catch us ?
     */
    public boolean killer(Spaceship spaceship) {
        return ((rect.top >= spaceship.rect.top+25
                && rect.top <= spaceship.rect.bottom-25
                && rect.left>= spaceship.rect.left+25
                && rect.left <= spaceship.rect.right-25 )
                ||(rect.bottom >= spaceship.rect.top+25
                && rect.bottom <= spaceship.rect.bottom-25
                && rect.left>= spaceship.rect.left+25
                && rect.left <= spaceship.rect.right-25 )
                || (rect.top >= spaceship.rect.top+25
                && rect.top <= spaceship.rect.bottom-25
                && rect.right>= spaceship.rect.left+25
                && rect.right <= spaceship.rect.right-25 )
                || (rect.bottom >= spaceship.rect.top+25
                && rect.bottom <= spaceship.rect.bottom-25
                && rect.right>= spaceship.rect.left+25
                && rect.right <= spaceship.rect.right-25 )
                );
    }


}
