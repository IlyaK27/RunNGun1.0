/**
 * Final Game PlayerBullet Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents the framework for the types of bullets the player can fire
 */

import java.awt.Graphics;
import java.awt.Color;
import java.awt.*;
import java.awt.Rectangle;

public abstract class PlayerBullet extends Bullet{
    private Obstacle obstacle;
    private Boss boss;
    
    public PlayerBullet(int x, int y, int bulletWidth, int bulletHeight, Obstacle obstacle, int damage){
        super(x, y, bulletWidth, bulletHeight, Const.BULLET_SPEED, damage);
        this.obstacle = obstacle;
        this.boss = Game.boss;
    }    
//------------------------------------------------------------------------------    
// Methods
    public void draw(Graphics g){
        g.setColor(Color.YELLOW);
        ((Graphics2D)g).fill(this.getHitbox());    
    }       
    public void move(){
        this.setX(this.getX() + this.getVx());
        this.getHitbox().setLocation(this.getX(), this.getY());
    }    
    public boolean checkCollision(){
        if (this.obstacle != null && this.getHitbox().intersects(this.obstacle.getHitbox())){
            this.obstacle.damage(this.getDamage());
            return true;
        }
        if (this.boss != null && this.boss.checkCollision(this.getHitbox())){
            this.boss.damage(this.getDamage());
            return true;
        }
        return false;
    }
}