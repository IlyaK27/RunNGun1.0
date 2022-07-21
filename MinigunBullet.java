/**
 * Final Game MinigunBullet Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents a type of bullet that the player can fire
 * This bullet will only be fired when the player has a minigun
 * This bullet is shorter and does less damage but it fires faster
 */

public class MinigunBullet extends PlayerBullet{
    public MinigunBullet (int x, int y, Obstacle obstacle){
        super(x, y, Const.MINIGUN_BULLET_WIDTH, Const.BULLET_HEIGHT, obstacle, Const.MINIGUN_DAMAGE);
    }         
}