/**
 * Final Game MetalObstacle Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents a type of obstacle that can be found within a game
 * This specific type of obstacle has high health and a grey color
 */

public class MetalObstacle extends Obstacle{
    MetalObstacle(int x, int y, Player player){
        super(x, y, player, Const.METAL_COLOR, Const.METAL_HEALTH);
    }
}