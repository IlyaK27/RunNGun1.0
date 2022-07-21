/**
 * Final Game WoodenObstacle Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents a type of obstacle that can be found within a game
 * This specific type of obstacle has low health and a brown color
 */

public class WoodenObstacle extends Obstacle{
    WoodenObstacle(int x, int y, Player player){
        super(x, y, player, Const.WOOD_COLOR, Const.WOOD_HEALTH);
    }
}