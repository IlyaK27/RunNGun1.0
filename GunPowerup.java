/**
 * Final Game GunPowerup Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents a Gunpowerup
 * This powerup will grant the player a gun when picked up
 */

public class GunPowerup extends Powerup{    
    GunPowerup(int x, Player player){
        super(Const.GUN_POWERUP_IMAGE, x, player);
    }
//-------------------------------------------------------------------------------------------------    
// Methods
    public boolean checkCollision(){
        boolean collided = super.checkCollision();
        if (collided == true){
            Player player = this.getPlayer();
            player.setMinigun(false);
            player.setGun(true);
            // Changing the players row to show that the player has a gun
            int newRow = 1;
            if (player.getShielded() == true){newRow = newRow + 3;}
            player.setRow(newRow);
            Game.ammo.setMagazineSize(Const.PISTOL_MAGAZINE_SIZE);
            return true;
        }
        return false;
    }
    public void resetPowerup(){
        Game.gunGenerated = false;
    }
}