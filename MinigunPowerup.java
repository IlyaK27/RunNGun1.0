/**
 * Final Game MinigunPowerup Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents a Minigunpowerup
 * This powerup will grant the player a minigun when picked up
 */

public class MinigunPowerup extends Powerup{    
    MinigunPowerup(int x, Player player){
        super(Const.MINIGUN_POWERUP_IMAGE, x, player);
    } 
//-------------------------------------------------------------------------------------------------    
// Methods
    public boolean checkCollision(){
        boolean collided = super.checkCollision();
        if (collided == true){
            Player player = this.getPlayer();
            player.setGun(false);
            player.setMinigun(true);
            // Changing the players row to show that the player has a minigun
            int newRow = 2;
            if (player.getShielded() == true){newRow = newRow + 3;}
            player.setRow(newRow);
            Game.ammo.setMagazineSize(Const.MINIGUN_MAGAZINE_SIZE);
            return true;
        }
        return false;
    }
    public void resetPowerup(){
        Game.minigunGenerated = false;
    }
}