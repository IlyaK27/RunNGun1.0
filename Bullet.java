/**
 * Final Game BossBullet Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents the general form all bullets (boss and player) 
 */

import java.awt.Graphics;
import java.awt.Color;
import java.awt.*;
import java.awt.Rectangle;

public abstract class Bullet{
    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle hitbox;
    private int bulletVx;
    private int damage;
    
    public Bullet(int x, int y, int bulletWidth, int bulletHeight, int bulletVx, int damage){
        this.x = x;
        this.y = y;
        this.width = bulletWidth;
        this.height = bulletHeight;
        this.hitbox = new Rectangle(x, y, width, height);
        this.bulletVx = bulletVx;
        this.damage = damage;
    }    
//------------------------------------------------------------------------------
// Getters and Setters
    public int getX(){
        return this.x;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getY(){
        return this.y;
    }
    public Rectangle getHitbox(){
        return this.hitbox;
    }
    public int getVx(){
        return this.bulletVx;
    }
    public int getDamage(){
        return this.damage;
    }
//------------------------------------------------------------------------------    
    abstract public void draw(Graphics g);
  
    abstract public void move();
    
    abstract public boolean checkCollision();
}