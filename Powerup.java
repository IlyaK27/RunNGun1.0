/**
 * Final Game Powerup Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents the framework for the different powerups found within the game
 * This class inherits from Gameobject because it is a game entity while not being a character/living entity
 */

import java.awt.Rectangle;
import java.awt.Graphics;

public abstract class Powerup extends GameObject{
    private Image image;
    
    Powerup(Image image, int x, Player player){
        super(x, 0, image.getWidth(), image.getHeight(), player);
        this.image = image;
    }
//-------------------------------------------------------------------------------------------------  
// Getters and Setters
    public Image getImage(){
        return this.image;
    }
//-------------------------------------------------------------------------------------------------    
// Methods
    public void draw(Graphics g){
        this.image.draw(g, this.getX(), this.getY());
    }    
    public boolean checkCollision(){
        if(this.getHitbox().intersects(this.getPlayer().getHitbox())){
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
    public abstract void resetPowerup();
}