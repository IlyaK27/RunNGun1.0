/**
 * Final Game ShieldPowerup Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents a Shieldpowerup
 * This powerup will grant the player a shield when picked up
 */

public class ShieldPowerup extends Powerup{    
    ShieldPowerup(int x, Player player){
        super(Const.SHIELD_POWERUP_IMAGE, x, player);
    }
//-------------------------------------------------------------------------------------------------    
// Methods
    public boolean checkCollision(){
        boolean collided = super.checkCollision();
        Player player = this.getPlayer();
        // Changing the row of the player to show that the player has a shield
        if (collided == true && player.getShielded() == false){
            player.setRow(player.getRow() + 3);
            player.setShielded(true);
            return true;
        }
        return false;
    }
    public void resetPowerup(){
        Game.shieldGenerated = false;
    }
}