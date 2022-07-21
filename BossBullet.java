/**
 * Final Game BossBullet Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents a general form that all the boss attacks take
 * It extends from bullet to have similar methods
 */

import java.awt.Graphics;
import java.awt.Color;
import java.awt.*;
import java.awt.Rectangle;

public abstract class BossBullet extends Bullet{
    private Player player;
    private Image bulletImage;
   
    public BossBullet(int x, int y, int bulletWidth, int bulletHeight, int bulletSpeed, int damage, Image bulletImage){
        super(x, y, bulletWidth, bulletHeight, bulletSpeed, damage);
        this.player = Game.player;
        this.bulletImage = bulletImage;
    }    
//------------------------------------------------------------------------------    
    public void draw(Graphics g){
        this.bulletImage.draw(g, this.getX(), this.getY());    
    }    
    public void move(){
        this.setX(this.getX() + this.getVx());
        this.getHitbox().setLocation(this.getX(), this.getY());
    }    
    public boolean checkCollision(){
        if (this.getHitbox().intersects(this.player.getHitbox())){
            this.player.damage(this.getDamage());
            return true;
        }
        return false;
    }
}