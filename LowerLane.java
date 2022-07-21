/**
 * Final Game LowerLane Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents the one of lanes that the player can run in. In this case its the lowest one
 * This class is seperate because the lowerlane needs to have different conditions to generate obstacles to make the game fair
 */
public class LowerLane extends Lane{    
    LowerLane(Line roof, Line floor){
        super(roof, floor);
    } 
    @Override
    public void generateObstacle(){
        Line lineWithOpening;
        int openingX;
        if(this.getRoof().getOpeningClose() == true){
            lineWithOpening = this.getRoof();
            openingX = lineWithOpening.getOpeningX();
            this.makeObstacle(openingX);
        }
    }
}