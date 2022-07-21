/**
 * Final Game Player Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents the actual player of the game
 * This class has all the behaviours of the player and inherits from character in order to use animation
 */

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.*;

public class Player extends Character{
    private int count = 0; // Variable that will limit how fast the player animates 
    private int Vy;
    private boolean jumping;
    private boolean diving;
    private boolean sliding;
    private boolean gun;
    private boolean minigun;
    private boolean shielded;
    private boolean shooting;
    private boolean isOnFloor;
    private int lives;
    private boolean dead;
    private Rectangle top;
    private Rectangle bottom;
    private boolean yChanged;
    private String name;
//------------------------------------------------------------------------------    
    Player(int x, int y, int lives){
        super(x, y, Const.PLAYER_PIC_NAME, Const.PLAYER_ROWS, Const.PLAYER_COLUMNS);
        this.Vy = 0; 
        this.jumping = false;
        this.diving = false;
        this.sliding = false;
        this.gun = false;
        this.minigun = false;
        this.shielded = false;
        this.shooting = false;
        this.isOnFloor = true;
        this.lives = lives;
        this.dead = false;
        this.top = new Rectangle(x, y, this.getWidth(), (this.getHeight() / 2) - 10);
        this.bottom = new Rectangle(x, y + ((this.getHeight() / 2) + 10), this.getWidth(), (this.getHeight() / 2) - 15);
        this.yChanged = false;
        this.name = "";
    }
//------------------------------------------------------------------------------ 
//  Getters and Setters
    public boolean getJumping(){
        return this.jumping;
    }
    public void setJumping(boolean jumping){
        this.jumping = jumping;
    }
    public boolean getSliding(){
        return this.sliding;
    }
    public void setSliding(boolean sliding){
        this.sliding = sliding;
    }
    public boolean getDiving(){
        return this.diving;
    }
    public void setDiving(boolean diving){
        this.diving = diving;
    }
    public boolean getGun(){
        return this.gun;
    }
    public void setGun(boolean gun){
        this.gun = gun;
    }
    public boolean getMinigun(){
        return this.minigun;
    }
    public void setMinigun(boolean minigun){
        this.minigun = minigun;
    }
    public boolean getShielded(){
        return this.shielded;
    }
    public void setShielded(boolean shielded){
        this.shielded = shielded;
    }
    public boolean getShooting(){
        return this.shooting;
    }
    public void setShooting(boolean shooting){
        this.shooting = shooting;
    }
    public void setVy(int Vy){
        this.Vy = Vy;
    }
    public int getVy(){
        return this.Vy;
    }   
    public int getLives(){
        return this.lives;
    }
    public void setLives(int lives){
        this.lives = lives;
    }
    public boolean getOnFloor(){
        return this.isOnFloor;
    }
    public void setOnFloor(boolean isOnFloor){
        this.isOnFloor = isOnFloor;
    }
    public Rectangle getTop(){
        return this.top;
    }
    public Rectangle getBottom(){
        return this.bottom;
    }
    public boolean getYChanged(){
        return this.yChanged;
    }
    public void setYChanged(boolean yChanged){
        this.yChanged = yChanged;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
//------------------------------------------------------------------------------
// Movement and animation
    public void move(int ground){
        if (this.lives <= 0){
            this.die();
        }
        if (this.dead == false){
            if (this.jumping == false && this.diving == false && this.sliding == false){
                run(ground);
            }
            if (this.jumping == true){
                jump(ground);
            }
            if (this.sliding == true){
                slide();
            }
            if (this.diving == true){
                dive(ground);
            }
        }
    }
    private void run(int ground){
        if (this.isOnFloor == true){
            this.count = this.count + 1;
            if (this.count >= 6){
                if ((this.getCol() + 1) < 6){
                    this.setCol((this.getCol() + 1)%(this.getFrames())[this.getRow()].length); 
                    if (this.minigun == true && this.getCol() == 5){ 
                        // Despite there being 6 images for the machine gun run only 5 are actually used and the 6th is a duplicate filler
                        this.setCol(0);
                    }
                    this.count = 0;
                }else{
                    this.setCol(0);
                }
            }
        }
        else{
            if (!((this.getY() + this.getHeight()) + 1 == ground || (this.getY() + this.getHeight()) == ground)){
                this.setCol(6);
            }
        }
    }
    private void jump(int ground){
        if ((this.getY() + this.getHeight()) < (ground - 40)){
            this.setCol(7); 
        } else if ((this.getY() + this.getHeight()) <= ground){
            this.setCol(6); 
        }
        if (this.Vy == 0 && this.isOnFloor == true){
            this.jumping = false;
        }
    }
    private void slide(){
        if (this.yChanged == false){
            this.setY(this.getY() + this.getHeight() - Const.PLAYER_CROUCH_HEIGHT);
            this.yChanged = true;
        }
        if (this.sliding == true){
            this.setCol(9);
        }
    }
    private void dive(int ground){
        if ((this.getY() + this.getHeight()) < (ground - 20)){
            this.setCol(10); 
        } else if ((this.getY() + this.getHeight()) <= ground){
            if (this.getCol() == 10){this.setY(this.getY() - 45);}
            this.setCol(8); 
        }
        if (this.Vy == 0 && this.isOnFloor == true){ // Add a way for it to check if player is on the ground later
            this.diving = false;
            this.setY(this.getY() - 65);
        }
    }
    // Methods to help create gravity and have the player jump up
    public void accellerate(){
        if(this.isOnFloor == false){
            this.Vy += Const.GRAVITY;
        }
    }
    public void moveY(Line ground){
        if(ground.checkCollision(this.getHitbox())){
            this.setY(ground.getY() - this.getHeight());
            this.Vy = 0;
        }
        else{
            this.setY(this.getY() + this.Vy);   
        }
    }
//------------------------------------------------------------------------------ 
// Damage methods
    public void damage(int damage){
        if (this.shielded == true){
            this.shielded = false;
            this.setRow(this.getRow() - 3);
            damage = damage - 1;
        }
        this.lives = this.lives - damage;
        if(this.lives <= 0){
            this.die();
        }
    }
    public void die(){
        if (this.dead == false){
            this.setRow(6); 
            this.setCol(0);
            this.isOnFloor = false;
            this.Vy = Const.DEATH_SPEED;
            this.shielded = false;
            this.dead = true;
            this.count = 0;
        }
        if (this.dead == true){
            this.count = this.count + 1;
            if (this.count >= 7){
                if((this.getCol() + 1) < 5){
                    this.setCol(this.getCol() + 1); 
                    this.count = 0;   
                }
            }
        }
    }
//------------------------------------------------------------------------------ 
// Methods that update player hitboxes
    public void updateHitboxes(){
        this.setHitbox();
        this.updateHitbox();
        this.setTopBottom();
        this.updateTopBottom();
    }
    private void setTopBottom(){
        this.top.setLocation(this.getX(), (int)this.getHitbox().getY()); 
        this.bottom.setLocation(this.getX(), ((int)this.getHitbox().getY() +((this.getHeight() / 2) + 10))); 
    }
    private void updateTopBottom(){
        this.top.setSize(this.getWidth(), (this.getHeight() / 2) - 10);
        this.bottom.setSize(this.getWidth(), (this.getHeight() / 2) - 10);
    }
}