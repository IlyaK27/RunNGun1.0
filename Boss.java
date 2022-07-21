/**
 * Final Game Boss Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents a boss entity.
 * It extends from character because it uses sprites and animation
 */

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.*;

public class Boss extends Character{
    private int count = 0;
    private int Vy;
    private boolean isOnFloor;
    private int health;
    private int maxHealth;
    private boolean dead;
    private Rectangle top;
    private Rectangle middle;
    private Rectangle bottom;
    private boolean yChanged;
    private Player player;
    private boolean chainAttack;
    private boolean playerInSameLane;
    private int shootTimer; // Time between attacks
    private boolean smallAttack;
    private boolean bigAttack;
    private Bullet currentBullet;
//------------------------------------------------------------------------------    
    Boss(int x, int y, int health, Player player){
        super(x, y, Const.BOSS_PIC_NAME, Const.BOSS_ROWS, Const.BOSS_COLUMNS);
        this.Vy = 0; 
        this.isOnFloor = true;
        this.health = health;
        this.maxHealth = health;
        this.dead = false;
        this.top = new Rectangle(x + 180, y, (this.getWidth() - 100), (this.getHeight() / 3));
        this.middle = new Rectangle(x, (y + (this.getHeight() / 3)), (this.getWidth() - 100), (this.getHeight()/ 3));
        this.bottom = new Rectangle(x + 180, (y + (2 *  (this.getHeight() / 3))), (this.getWidth() - 100), (this.getHeight()/ 3));
        this.player = player;
        this.chainAttack = false;
        this.playerInSameLane = true;
        this.shootTimer = 0;
        this.smallAttack = false;
        this.bigAttack = false;
        this.currentBullet = null;
    }
//------------------------------------------------------------------------------ 
    public void setVy(int Vy){
        this.Vy = Vy;
    }
    public int getVy(){
        return this.Vy;
    }   
    public int getHealth(){
        return this.health;
    }
    public void setHealth(int health){
        this.health = health;
    }
    public int getMaxHealth(){
        return this.maxHealth;
    }
    public boolean getOnFloor(){
        return this.isOnFloor;
    }
    public void setOnFloor(boolean isOnFloor){
        this.isOnFloor = isOnFloor;
    }
//------------------------------------------------------------------------------
    public void move(int playerGround){
        if (this.health <= 0 || this.getY() > 1000 || (this.getY() + this.getHeight()) < 0){
            this.die();
        }
        if (this.player.getLives() > 0){
            if (this.dead == false){
                this.count = this.count + 1;
                if (this.smallAttack == false && this.bigAttack == false){
                    this.run();
                }else if (this.smallAttack == true){
                    smallAttack();
                }else if (this.bigAttack == true){
                    bigAttack();
                }
                if (this.playerInSameLane == false){
                    moveTowardsPlayer(playerGround);
                }
            }
        }
        else{
            //if (this.health < 0){
              //  this.setRow(0);
                //this.setCol(8);
            //}
        }
    }
    
    private void run(){
        if (this.isOnFloor == true){
            this.shootTimer = this.shootTimer + 1;
            if (this.count >= 6){
                    this.setCol((this.getCol() + 1)%(this.getFrames())[this.getRow()].length); 
                    this.count = 0;
            }
            if (this.shootTimer >= 50){
                makeAttack();
                this.shootTimer = 0;
            }
        }
        else{
            this.setRow(0);
            this.setCol(0);
        }
    }
    private void moveTowardsPlayer(int playerGround){
        int ground = playerGround + (Const.LANE_HEIGHT + Const.LINE_HEIGHT);
        int playerInLane = (int)((player.getY() - 125) / 160) + 1;
        int gunInLane = (int)((this.middle.getY() - 125) / 160) + 1;
        if (gunInLane > playerInLane && this.isOnFloor == true){
            this.Vy = Const.BOSS_JUMPSPEED;
        }
        this.isOnFloor = false;
        moveY(ground);
        accellerate();
    }
    private void accellerate(){
        if(this.isOnFloor == false){
            this.Vy += Const.BOSS_GRAVITY;
        }
    }
    private void moveY(int ground){
        Rectangle feet = new Rectangle((int)bottom.getX(), (int)(bottom.getY() + 130), (int)bottom.getWidth(), (int)bottom.getHeight() - 130);
        if(feet.contains((this.getX() + this.getHeight()), ground)){
            this.setY(ground - this.getHeight());
            this.Vy = 0;
            this.playerInSameLane = true;
            this.isOnFloor = true;
        }
        else{
            this.setY(this.getY() + this.Vy);   
        }
    }
    private void makeAttack(){
        int generateChance = (int)Math.round(1 * Math.random()); // 0 = don't generate, 1 = generate
        if ((chainAttack == true || generateChance == 1) && (smallAttack == false || bigAttack == false)){
            int playerInLane = (int)((player.getY() - 125) / 160) + 1;
            int gunInLane = (int)((this.middle.getY() - 125) / 160) + 1;
            if (playerInLane == gunInLane){
                if (playerInSameLane == false){playerInSameLane = true;}  
                Lane currentLane = Const.LANES[gunInLane - 1];
                if((currentLane.getRoof().getOpeningX() <= 900 ||currentLane.getFloor().getOpeningX() <= 900)
                  && currentLane.getObstacleGenerated() == false){ // If there is an opening and no obstacle make a big bullet
                    BossBigBullet bullet = new BossBigBullet((this.getX() + Const.BOSS_BIG_BULLET_IMAGE.getWidth()), 
                                                             (Const.LINE1_TOP + 22) + 
                                                             ((gunInLane - 1) * (Const.LANE_HEIGHT + Const.LINE_HEIGHT)));
                    this.setRow(2);
                    this.setCol(0);
                    this.bigAttack = true;
                    this.currentBullet = bullet;
                    this.chainAttack = false;
                }
                else{
                    // 1 - 5 = bullet in top of lane, 6 - 10 = bullet in bottom of lane, 5 or 10 = activate chain attack along with make respective bullet
                    int attackChoice = (int)Math.round(9 * Math.random() + 1); 
                    if (attackChoice == 5 || attackChoice == 10){
                        this.chainAttack = true;
                    }
                    int bulletY = 0;
                    if (attackChoice >= 1 && attackChoice <= 5){
                        bulletY = (Const.LINE1_TOP + 45) + ((gunInLane - 1) * (Const.LANE_HEIGHT + Const.LINE_HEIGHT));
                    }else if (attackChoice >= 6 && attackChoice <= 10){
                        bulletY = (Const.LINE1_TOP + 120) + ((gunInLane - 1) * (Const.LANE_HEIGHT + Const.LINE_HEIGHT));
                    }
                    BossSmallBullet bullet = new BossSmallBullet((this.getX() + Const.BOSS_SMALL_BULLET_IMAGE.getWidth()), 
                                                             bulletY);
                    this.setRow(1);
                    this.setCol(0);
                    this.smallAttack = true;
                    this.currentBullet = bullet;
                }
            }
            else{
                if (chainAttack == true){chainAttack = false;}   
                playerInSameLane = false;
            }
        }
    }
    private void smallAttack(){
        if (this.getCol() == 0 && this.currentBullet != null){
            Game.ammo.addBossBullet(this.currentBullet);
            this.currentBullet = null;
        }
        else if (this.getCol() == 4){
            this.setRow(0);
            this.setCol(0);
            this.smallAttack = false;
        }
        if (this.count >= 6){
            this.setCol((this.getCol() + 1)%(this.getFrames())[this.getRow()].length); 
            this.count = 0;
        }
    }
    private void bigAttack(){
        if (this.getCol() == 4 && this.currentBullet != null){
            Game.ammo.addBossBullet(this.currentBullet);
            this.currentBullet = null;
        }
        else if (this.getCol() == 8){
            this.setRow(0);
            this.setCol(0);
            this.bigAttack = false;
        }
        if (this.count >= 6){
            this.setCol((this.getCol() + 1)%(this.getFrames())[this.getRow()].length); 
            this.count = 0;
        }
    }
    public void damage(int damage){
        this.health = this.health - damage;
        if(this.health <= 0){
            this.die();
        }
    }
    private void updateSecondaryHitboxes(){
        this.top.setLocation(this.getX() + 180, this.getY());
        this.middle.setLocation(this.getX(), (this.getY() + this.getHeight() / 3));
        this.bottom.setLocation(this.getX() + 180, (this.getY() + (2 * (this.getHeight() / 3))));
    }
    public void updateHitboxes(){
        this.setHitbox();
        this.updateHitbox();
        this.updateSecondaryHitboxes();
    }
    public boolean checkCollision(Rectangle box){
        if (this.top.intersects(box) || this.middle.intersects(box) || this.bottom.intersects(box)){
            return true;
        }
        return false;
    }
    public void die(){
        if (this.dead == false){
            this.health = 0;
            this.setRow(3); 
            this.setCol(0);
            this.isOnFloor = false;
            this.dead = true;
        }
        if (this.dead == true){
            this.setX(this.getX() - Game.step);
            this.count = this.count + 1;
            if (this.count >= 7){
                if((this.getCol() + 1) < 6){
                    this.setCol(this.getCol() + 1); 
                    this.count = 0;   
                }
            }
        }
    }
}