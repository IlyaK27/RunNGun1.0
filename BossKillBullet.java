/**
 * Final Game BossKillBullet Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents one of the bosses attacks which will instantly kill the player if it hits them
 * It extends from bullet and imputs its own constants
 */

public class BossKillBullet extends BossBullet{
    public BossKillBullet (int x){
        super(x, Const.LINE1_TOP, Const.BOSS_KILL_ATTACK_IMAGE.getWidth(), Const.BOSS_KILL_ATTACK_IMAGE.getHeight(), 
              Const.BOSS_BIG_BULLET_SPEED, Const.BOSS_KILL_BULLET_DAMAGE, Const.BOSS_KILL_ATTACK_IMAGE);
    }            
}