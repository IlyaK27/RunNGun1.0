/**
 * Final Game BossSmallBullet Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents one of the bosses attacks
 * It extends from bullet and imputs its own constants
 */

public class BossSmallBullet extends BossBullet{
    public BossSmallBullet (int x, int y){
        super(x, y, Const.BOSS_SMALL_BULLET_IMAGE.getWidth(), Const.BOSS_SMALL_BULLET_IMAGE.getHeight(),  
              Const.BOSS_SMALL_BULLET_SPEED, Const.BOSS_BULLET_DAMAGE, Const.BOSS_SMALL_BULLET_IMAGE);
    }            
}