/**
 * Final Game LifePowerup Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents a Lifepowerup
 * This powerup will grant the player an extra life when picked up
 */

public class LifePowerup extends Powerup{    
    LifePowerup(int x, Player player){
        super(Const.LIFE_POWERUP_IMAGE, x, player);
    }
//-------------------------------------------------------------------------------------------------    
// Methods
    public boolean checkCollision(){
        boolean collided = super.checkCollision();
        if (collided == true){
            Player player = this.getPlayer();
            player.setLives(player.getLives() + 1);
            return true;
        }
        return false;
    }
    public void resetPowerup(){
        Game.lifeGenerated = false;
    }
}