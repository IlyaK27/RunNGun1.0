/**
 * Final Game BossBigBullet Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents one of the bosses attacks
 * It extends from bullet and imputs its own constants
 */

public class BossBigBullet extends BossBullet{
    public BossBigBullet (int x, int y){
        super(x, y, Const.BOSS_BIG_BULLET_IMAGE.getWidth(), Const.BOSS_BIG_BULLET_IMAGE.getHeight(), 
              Const.BOSS_BIG_BULLET_SPEED, Const.BOSS_BULLET_DAMAGE, Const.BOSS_BIG_BULLET_IMAGE);
    }            
}