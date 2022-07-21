/**
 * Final Game Character Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents a genera frame for a game entity since it is abtstract
 * This class reads from files to create a moving and character with animations
 */

import java.awt.Graphics;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
// possible exceptions
import java.io.IOException;
import java.awt.Color;
import java.awt.*;

public abstract class Character{
    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage[][] frames;
    private int row;
    private int col;
    private Rectangle hitbox;
//------------------------------------------------------------------------------    
    Character(int x, int y, String picName, int rows, int columns){
        this.x = x;
        this.y = y; 
        frames = new BufferedImage[rows][columns];
        try {
            for (int row=0; row<rows; row++){
                for (int col=0; col<columns; col++){
                    frames[row][col] = ImageIO.read(new File(picName+row+col+".png"));
                }
            }
        } catch (IOException ex){}
        row = 0;
        col = 0;
        this.width = 0;
        this.height = 0;
        this.hitbox = new Rectangle(this.x, this.y, this.width, this.height); 
        this.updateHitbox();
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
    public void setY(int y){
        this.y = y;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }  
    public void setHeight(int height){
        this.height = height;
    }
    public int getRow(){
        return this.row;
    }
    public void setRow(int row){
        this.row = row;
    }
    public int getCol(){
        return this.col;
    }
    public void setCol(int col){
        this.col = col;
    }
    public BufferedImage[][] getFrames(){
        return this.frames;
    }
    public Rectangle getHitbox(){
        return this.hitbox;
    }
//------------------------------------------------------------------------------  
    public void draw(Graphics g){
        g.drawImage(this.frames[this.row][this.col], this.x, this.y, null); 
    }
    public void setHitbox(){
        this.hitbox.setLocation(this.x, this.y);
    }
    public void updateHitbox(){
        this.width = this.frames[this.row][this.col].getWidth();
        this.height = this.frames[this.row][this.col].getHeight();
        this.hitbox.setSize(this.width, this.height);
    }
}