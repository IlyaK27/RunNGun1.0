/**
 * Final Game Ammo Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 */

import java.awt.Graphics;

public class Ammo{
    final int CARTRIDGE_SIZE = 100;  // Maximum amount of bullets on the screen  
    int magazineSize = 0;
    private Bullet[] bullets;
    
    Ammo(){
        this.bullets = new Bullet[CARTRIDGE_SIZE];
    }
//------------------------------------------------------------------------------   
//  Getters and Setters
    public int getMagazineSize(){
        return magazineSize;
    }
    public void setMagazineSize(int newSize){
        this.magazineSize = newSize;
    }
//------------------------------------------------------------------------------    
// Bullet actions
    public void drawBullets(Graphics g){
        for (int i=0; i<this.bullets.length; i++){
            if (this.bullets[i] != null){
                this.bullets[i].draw(g);
            }
        }
    }
    public void moveBullets(){
        for (int i=0; i<this.bullets.length; i++){
            if (this.bullets[i] != null){
                this.bullets[i].move();
                boolean collided = this.bullets[i].checkCollision();
                if (this.bullets[i].getX() > Const.WIDTH || collided == true){
                    this.removeBullet(i);
                }
            }
        }
    }
//------------------------------------------------------------------------------  
// Adding and removing bullets
    public void addPlayerBullet(Obstacle obstacle){
        boolean added = false;
        Player player = Game.player;
        for (int i=0; i < this.bullets.length; i++){
            if (this.bullets[i] == null && !added){
                int bulletX = player.getX() + player.getWidth();
                int bulletY = player.getY() + (player.getHeight() / 2) - (Const.BULLET_HEIGHT / 2);
                if (player.getGun() == true){
                    this.bullets[i] = new PistolBullet(bulletX, bulletY, obstacle);
                }else if (player.getMinigun() == true){
                    this.bullets[i] = new MinigunBullet(bulletX, bulletY, obstacle);
                }
                added = true;
                magazineSize = magazineSize - 1;
            }
        }
    }
    public void addBossBullet(Bullet bullet){
        boolean added = false;
        for (int i=0; i < this.bullets.length; i++){
            if (this.bullets[i] == null && !added){
                this.bullets[i] = bullet;
                added = true;
            }
        }
    }
    public void removeBullet(int index){
        this.bullets[index] = null;
    }    
}