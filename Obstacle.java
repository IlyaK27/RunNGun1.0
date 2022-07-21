/**
 * Final Game Obstacle Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents the obstacles that can be found within the game
 * This class is abstract because there are 2 types of obstacles while this is the general framework for each
 */

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.*;

public abstract class Obstacle extends GameObject{
    private Color color;
    private int health;
    
    Obstacle(int x, int y, Player player, Color color, int health){
        super(x, y, Const.OBSTACLE_WIDTH, Const.OBSTACLE_HEIGHT, player);
        this.color = color;
        this.health = health;
    } 
//-------------------------------------------------------------------------------------------------    
// Methods
    public void draw(Graphics g){
        g.setColor(this.color);
        ((Graphics2D)g).fill(this.getHitbox());
    }
    public boolean checkCollision(){
        Player player = this.getPlayer();
        if (this.getHitbox().intersects(player.getHitbox())){
            player.damage(1); // Dealing 1 damage to the player when collided with
            this.setDelete(true);
            return true;
        }
        return false;
    } 
    public void scroll(int step){
        this.setX(this.getX() - step);
        this.getHitbox().setLocation(this.getX(), this.getY());
        if (this.getX() + this.getHitbox().getWidth() <= 0){
            this.setDelete(true);
        }
    }
    public void damage(int damage){
        this.health = this.health - damage;
        if (this.health <= 0){
            this.setDelete(true);
        }
    }
}