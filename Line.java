/**
 * Final Game Line Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents a a Line inside the game
 * The player will be able to either run on a line or bump into one to create the main mechanic of the game
 */

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.*;

public class Line{
    private int x1;
    private int x2;
    private int y;
    private int minWidth;
    private int maxWidth;
    private int height; 
    private Rectangle rect1;
    private Rectangle rect2;
    private boolean resetRect1;
    private boolean resetRect2;
    private boolean openingClose;
//------------------------------------------------------------------------------    
    Line(int x, int width2, int y, int minWidth, int maxWidth, int height, Player player){
        this.x1 = x;
        this.x2 = x + 10;
        this.y = y;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.height = height;
        this.rect1 = new Rectangle(x1, y, 10, this.height);
        this.rect2 = new Rectangle(x2, y, width2, this.height);
        this.resetRect1 = false;
        this.resetRect2 = false;
        this.openingClose = false;
    }
//------------------------------------------------------------------------------  
// Getters and Setters
    public int getY(){
        return this.y;
    }  
    public int getHeight(){
        return this.height;
    } 
    public boolean getOpeningClose(){
        return this.openingClose;
    }
//------------------------------------------------------------------------------  
    public void draw(Graphics g){
        g.setColor(Const.LINE_COLOR);
        ((Graphics2D)g).fill(this.rect1);
        ((Graphics2D)g).fill(this.rect2);
    }
    public boolean checkCollision(Rectangle box){
        if (this.rect1.intersects(box) || this.rect2.intersects(box)){
            return true;
        }
        return false;
    }
    public void scroll(int step){
        this.x1 = this.x1 - step;
        this.x2 = this.x2 - step;
        this.rect1.setLocation(x1, y);
        this.rect2.setLocation(x2, y);
        if (this.x1 + this.rect1.getWidth() <= 0){
            this.resetRect1 = true;
            this.createNewRect();
        }
        if (this.x2 + this.rect2.getWidth() <= 0){
            this.resetRect2 = true;
            this.createNewRect();
        }
        if (this.x1 + this.rect1.getWidth() < (Const.WIDTH + 300) 
                || this.x2 + this.rect2.getWidth() < (Const.WIDTH + 300)){
            this.openingClose = true;
        }else{
            this.openingClose = false;
        }
    }
    // Once 1 rectangle is out of bounds to the left its coordinates get regenerated 
    // This process will also create the opening in a line inside the game
    private void createNewRect(){
        if (this.resetRect1 == true){
            this.x1 = (int)(rect2.getX() + rect2.getWidth()) + 175;
            this.rect1.setLocation(x1, this.y);
            this.rect1.setSize(this.generateWidth(), this.height);
            this.resetRect1 = false;
        }
        if (this.resetRect2 == true){
            this.x2 = (int)(rect1.getX() + rect1.getWidth()) + 175;
            this.rect2.setLocation(this.x2, this.y);
            this.rect2.setSize(this.generateWidth(), this.height);
            this.resetRect2 = false;
        }
    }
    public int getOpeningX(){
        return Math.max(x1, x2);
    }
    // Generating a random with for the newly generated rectangle
    private int generateWidth(){
        return (int)((this.maxWidth - this.minWidth) * Math.random() + this.minWidth);
    }
}