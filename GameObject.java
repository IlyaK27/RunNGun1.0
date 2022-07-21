/**
 * Final Game GameObject Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class is a general frame of what gameobjects such as powerups and obstacles should be like
 */

import java.awt.Rectangle;
import java.awt.Graphics;

public abstract class GameObject{
    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle hitbox;
    private Player player;
    private boolean delete;
    
    GameObject(int x, int y, int width, int height, Player player){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(this.x, this.y, this.width, this.height); 
        this.player = player;
        this.delete = false;
    }
//--------------------------------------------------------------------------------------
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
    public void setY(int y){
        this.y = y;
    }
    public Rectangle getHitbox(){
        return this.hitbox;
    }
    public Player getPlayer(){
        return this.player;
    }
    public boolean getDelete(){
        return this.delete;
    }
    public void setDelete(boolean delete){
        this.delete = delete;
    }
//-------------------------------------------------------------------------------------------------    
// Methods
    abstract public void draw(Graphics g);
    
    abstract public boolean checkCollision();
    
    abstract public void scroll(int step);
}