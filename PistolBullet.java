/**
 * Final Game PistolBullet Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents a type of bullet that the player can fire
 * This bullet will only be fired when the player has a gun (pistol)
 * This bullet is longer and does more damage but it fires slower
 */

public class PistolBullet extends PlayerBullet{
    public PistolBullet (int x, int y, Obstacle obstacle){
        super(x, y, Const.PISTOL_BULLET_WIDTH, Const.BULLET_HEIGHT, obstacle, Const.PISTOL_DAMAGE);
    }            
}