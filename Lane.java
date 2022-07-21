/**
 * Final Game Lane Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents the lanes that the player can run in
 * The lane has different attributes and things inside it such as powerups and obstacles that make it into a lane
 */

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.ArrayList;

public class Lane{
    private Line roof;
    private Line floor;
    private Player player;
    private Obstacle obstacle;
    private boolean obstacleGenerated;
    private ArrayList<Powerup> powerups = new ArrayList<Powerup>();
//------------------------------------------------------------------------------    
    Lane(Line roof, Line floor){
        this.roof = roof;
        this.floor = floor;
        this.player = Game.player;
        this.obstacleGenerated = false;
    }
//------------------------------------------------------------------------------  
// Getters and Setters
    public Line getRoof(){
        return this.roof;
    }
    public Line getFloor(){
        return this.floor;
    }
    public boolean getObstacleGenerated(){
        return this.obstacleGenerated;
    }
    public Obstacle getObstacle(){
        return this.obstacle;
    }
//------------------------------------------------------------------------------  
    // Collison checkers
    public void checkCollisions(){
        checkRoofCollision();
        checkFloorCollision();
        player.updateHitboxes();
        if (this.obstacleGenerated == true){
            boolean collided = obstacle.checkCollision();
            deleteObstacle();
        }
        if (this.powerups.size() > 0){
            for (int i = 0; i < powerups.size(); i++){
                boolean collided = powerups.get(i).checkCollision();
                deletePowerup(i);
            }
        }
    }
    private void checkRoofCollision(){
        if (this.roof.checkCollision(player.getTop())){
            player.setY(roof.getY() + roof.getHeight());
            player.setVy(0);
        }
    }
    private void checkFloorCollision(){
        if (this.floor.checkCollision(player.getBottom())){
            player.setVy(0);
            int bottomOfPlayer = (int)(player.getBottom().getY() + player.getBottom().getHeight() - 1);
            if (bottomOfPlayer > floor.getY()){ //If the player is in the ground push them up so they are on it 
                player.setY(player.getY() - (bottomOfPlayer - floor.getY()));
            }
            player.setOnFloor(true);
        }
        else{
            player.setOnFloor(false);
        }
    }
    // Checks lane if it can start generating an obstacke
    public void generateObstacle(){
        Line lineWithOpening = null;
        int openingX;
        if(this.roof.getOpeningClose() == true){
            lineWithOpening = roof;
        }if(this.floor.getOpeningClose() == true){
            lineWithOpening = floor;
        }
        if(lineWithOpening != null){
            openingX = lineWithOpening.getOpeningX();
            if (this.roof.getOpeningClose() == true && this.floor.getOpeningClose() == true){ // Unreachable for some reason
                openingX = Math.max(roof.getOpeningX(), floor.getOpeningX());
            }
            makeObstacle(openingX);   
        }
        // Obstacle will be generated only if an opening is found
    }
    // If an obstacle can be made in this lane this method will make an obstacle randomly
    protected void makeObstacle(int openingX){
        // Obstacle isn't garunteed to generate, 1 means generated, 0 means not generated
        int generateChance = (int)Math.round(1 * Math.random());
        boolean generateObstacle = generateChance == 1;
        if (generateObstacle == true && this.obstacleGenerated == false && openingX > Const.WIDTH){
            int obstacleType = (int)(4 * Math.random() + 1); // 1,2,3 = metal, 4 = wooden
            int distanceFromOpening = (int)((Const.MAX_OBSTACLE_DISTANCE - Const.MIN_OBSTACLE_DISTANCE) 
                                                 * Math.random() + Const.MIN_OBSTACLE_DISTANCE) + openingX;
            if (obstacleType == 4){
                this.obstacle = new WoodenObstacle(distanceFromOpening, (this.roof.getY() + Const.LINE_HEIGHT), this.player);
            }else{
                this.obstacle = new MetalObstacle(distanceFromOpening, (this.roof.getY() + Const.LINE_HEIGHT), this.player);
            }
            this.obstacleGenerated = true;
        }
    }
    // Creating powerups and adding them to the lane
    public void addPowerup(Powerup powerup){
        int centerY = this.floor.getY() - (Const.LANE_HEIGHT / 2);
        int y = centerY - powerup.getImage().getHeight() / 2;
        powerup.setY(y);
        boolean notColliding = true; //Checking if the powerup is inside another powerup that is already present in the lane
        while(notColliding == true){
            notColliding = false;
            powerup.getHitbox().setLocation(powerup.getX(), powerup.getY());
            for (int i = 0; i < powerups.size(); i++){
                // Making the powerup generate in another location if it does happen be inside something
                if (powerups.get(i).getHitbox().intersects(powerup.getHitbox())){ 
                    powerup.setX(powerup.getX() - 200);
                    notColliding = true;
                }
            }
        }
        powerups.add(powerup);
    }
    // Method makes all things apart of the lane scroll (besides the roof and floor)
    public void scroll(int step){
        if (obstacleGenerated == true){
            obstacle.scroll(step);
            deleteObstacle();
        }
        if (this.powerups.size() > 0){
            for (int i = 0; i < powerups.size(); i++){
                powerups.get(i).scroll(step);
                if(this.obstacleGenerated == true && this.obstacle.getHitbox().intersects(this.powerups.get(i).getHitbox())){ // If powerup inside obstacle move it left
                    this.powerups.get(i).setX(this.powerups.get(i).getX() - 200);
                }
                deletePowerup(i);
            }
        }
    }
    public void draw(Graphics g){
        if (obstacleGenerated == true){
            this.obstacle.draw(g);
        }
        if (this.powerups.size() > 0){
            for(int i = 0; i < powerups.size(); i++){
                powerups.get(i).draw(g);
            }
        }
    }
    public void deleteObstacle(){
        if (this.obstacle.getDelete() == true){
            this.obstacle = null;
            this.obstacleGenerated = false;
        }
    }
    private void deletePowerup(int index){
        if (powerups.get(index).getDelete() == true){
                this.powerups.get(index).resetPowerup();
                this.powerups.remove(index);       
        }
    }
}