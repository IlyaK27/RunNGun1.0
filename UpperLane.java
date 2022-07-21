/**
 * Final Game UpperLane Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents the one of lanes that the player can run in. In this case its the highest one
 * This class is seperate because the highest lane needs to have different conditions to generate obstacles to make the game fair
 */
public class UpperLane extends Lane{   
    UpperLane(Line roof, Line floor){
        super(roof, floor);
    }
    @Override
    public void generateObstacle(){
        Line lineWithOpening;
        int openingX;
        if(this.getFloor().getOpeningClose() == true){
            lineWithOpening = this.getFloor();
            openingX = lineWithOpening.getOpeningX();
            this.makeObstacle(openingX);
        }
    }
}