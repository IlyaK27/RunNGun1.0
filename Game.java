/**
 * Final Game Game Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class is where the main gameplay takes place
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.Timer;

public class Game{
    private JFrame gameWindow;
    private JPanel cards;
    
    //game objects
    public static Player player;
    public static Boss boss;
    private int topInLane;
    private int bottomInLane;
    private int ground;
    private int deadCount;
    public static int step = Const.STEP;
    private int meters = 0;
    private static int bossCount = 0;
    private static int bossMinHealth;
    private static int bossHPMultiplier;
    
    int fireRateCount  = 0;
    public static Ammo ammo = new Ammo();
    public static boolean gunGenerated = false;
    public static boolean minigunGenerated = false;
    public static boolean shieldGenerated = false;
    public static boolean lifeGenerated = false;
    private Timer runLoop;
    ScrollingBackground background;
//------------------------------------------------------------------------------
    //instantiate game objects
    Game(JFrame gameWindow, JPanel gamePanel){
        this.gameWindow = gameWindow; 
        this.cards = gamePanel;
        this.runLoop = new Timer(Const.GAME_FRAME_PERIOD, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                run();
            }
        });
        this.player = new Player(Const.STARTING_X, Const.STARTING_Y, Const.PLAYER_MAX_LIVES);
        setDifficultySettings(Const.NORMAL_SETTINGS);
        background = Const.SCROLLING_BACKGROUND;
    }
//------------------------------------------------------------------------------  
    //main game loop
    public void run(){
            topInLane = (int)((player.getY() - 125) / 160) + 1;
            bottomInLane = topInLane + 1;
            ground = topInLane * 160 + 125;
            background.scroll(step);
            Const.LINE1.scroll(step);
            for (int i = 0; i < Const.LANES.length; i++){
                Const.LINES[i + 1].scroll(step);
                Const.LANES[i].scroll(step);
            }
            meters = (int)(meters + (step / 10.0));
            if (player.getLives() > 0){
                player.updateHitboxes();
                if ((topInLane > 0 && topInLane < 6) && (bottomInLane > 0 && bottomInLane < 7)){
                    if (bottomInLane == 6 && topInLane == 5){bottomInLane = 4;}
                    Const.LANES[bottomInLane - 1].checkCollisions();
                    Const.LANES[topInLane - 1].checkCollisions();
                }
                else{
                    if(player.getY() <= 125 || player.getY() >= 935){ // if player jumps out of map
                        player.setY(1800);
                    }
                    player.setLives(0);
                }
                // Generating the Obstacles
                if (meters >= 200){
                    generateObstacles();
                    generatePowerups();
                }
                if (player.getShooting() == true){ 
                    playerShoot();
                }
                if (boss != null){
                    boss.updateHitboxes();
                    if (boss.getHealth() <= 0 && (boss.getX() + boss.getWidth() <= 0)){
                        boss = null;
                    }
                }
                if(meters % 2000 == 0){ // Try to create a boss every 2000m 
                    boss = createBoss();
                }
                fireRateCount = fireRateCount + 1;
            }
            else{
                deadCount = deadCount + 1;
                if (deadCount >= 8 && step > 0){
                    deadCount = 0;
                    step = step - 1;
                }else if(deadCount >= 8 && step == 0){
                    RunNGun.updateLeaderboard(player.getName(), meters);
                    RunNGun.ScreenSwapper swapper = new RunNGun.ScreenSwapper(cards, RunNGun.GAME_OVER_PANEL);
                    swapper.swap();
                    stop();
                }
                if ((topInLane > 0 && topInLane < 6) && (bottomInLane > 0 && bottomInLane < 6)){
                    Const.LANES[bottomInLane - 1].checkCollisions();
                    Const.LANES[topInLane - 1].checkCollisions();
                }
            }
            player.move(ground);
            player.accellerate();
            if (bottomInLane > 5){bottomInLane = 5;}
            player.moveY(Const.LINES[bottomInLane]);
            player.setHitbox();
            if (boss != null){boss.move(ground);}
            ammo.moveBullets();
    }  
    
    public void start() {
        this.runLoop.start();
    }

    public void stop() {
        this.runLoop.stop();
    }
//------------------------------------------------------------------------------  
    //act upon key events
    public class GameKeyListener implements KeyListener{   
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if (player.getLives() > 0){
                if (key == KeyEvent.VK_SPACE && player.getSliding() == false && player.getJumping() == false 
                        && player.getDiving() == false && player.getOnFloor() == true){
                    player.setY(player.getY() - 30);
                    player.setVy(Const.JUMP_SPEED);
                    player.setJumping(true);
                    player.setOnFloor(false);
                } 
                if (key == KeyEvent.VK_DOWN && player.getDiving() == false && player.getJumping() == false 
                        && player.getOnFloor() == true){
                    player.setSliding(true);
                }
                if (key == KeyEvent.VK_UP && player.getSliding() == false && player.getJumping() == false 
                        && player.getDiving() == false && player.getOnFloor() == true){
                    player.setY(player.getY() - 15);
                    player.setVy(Const.DIVE_SPEED);
                    player.setDiving(true);
                    player.setOnFloor(false);
                } 
                else if (key == KeyEvent.VK_Q){
                    player.setShooting(true);
                }
                else if (key == KeyEvent.VK_ESCAPE){
                    RunNGun.ScreenSwapper swapper = new RunNGun.ScreenSwapper(cards, RunNGun.PAUSE_PANEL);
                    swapper.swap();
                    stop();
                }
                // These can be used for debugging or checking gameplay out but shouldnt be a part of an actual playthrough
                /*if (key == KeyEvent.VK_0){
                    player.setRow(0);
                    player.setGun(false);
                    player.setMinigun(false);
                    player.setShielded(false);
                    ammo.setMagazineSize(0);
                    System.out.println("Normal");
                } 
                if (key == KeyEvent.VK_1){
                    player.setRow(1);
                    player.setGun(true);
                    player.setMinigun(false);
                    player.setShielded(false);
                    ammo.setMagazineSize(Const.PISTOL_MAGAZINE_SIZE);
                    System.out.println("Normal-Pistol");
                } 
                if (key == KeyEvent.VK_2){
                    player.setRow(2);
                    player.setGun(false);
                    player.setMinigun(true);
                    player.setShielded(false);
                    ammo.setMagazineSize(Const.MINIGUN_MAGAZINE_SIZE);
                    System.out.println("Normal-Minigun");
                } 
                if (key == KeyEvent.VK_3){
                    player.setRow(3);
                    player.setGun(false);
                    player.setMinigun(false);
                    player.setShielded(true);
                    ammo.setMagazineSize(0);
                    System.out.println("Shield");
                } 
                if (key == KeyEvent.VK_4){
                    player.setRow(4);
                    player.setGun(true);
                    player.setMinigun(false);
                    player.setShielded(true);
                    ammo.setMagazineSize(Const.PISTOL_MAGAZINE_SIZE);
                    System.out.println("Shield-Pistol");
                } 
                if (key == KeyEvent.VK_5){
                    player.setRow(5);
                    player.setGun(false);
                    player.setMinigun(true);
                    player.setShielded(true);
                    ammo.setMagazineSize(Const.MINIGUN_MAGAZINE_SIZE);
                    System.out.println("Shield-Minigun");
                } 
                if (key == KeyEvent.VK_6){
                    player.setLives(0);
                    player.die();
                } 
                if (key == KeyEvent.VK_7){
                    boss = createBoss();
                } */
            }
        }
        public void keyReleased(KeyEvent e){ 
            int key = e.getKeyCode();
            if (player.getLives() > 0){
                if (key == KeyEvent.VK_DOWN){
                    player.setCol(8);
                    player.setSliding(false);
                    player.setY(player.getY() - (Const.PLAYER_HEIGHT - player.getHeight()));
                    player.setYChanged(false);
                }
                if (key == KeyEvent.VK_Q){
                    player.setShooting(false);
                }
            }
        }   
        public void keyTyped(KeyEvent e){
        }           
    }     
//------------------------------------------------------------------------------  
// Game methods
    private void playerShoot(){
        if (player.getGun() == true){
            if (fireRateCount >= Const.PISTOL_FIRE_RATE){
                ammo.addPlayerBullet(Const.LANES[topInLane - 1].getObstacle());
                fireRateCount = 0;
            }
        }
        else if (player.getMinigun() == true){
            if (fireRateCount >= Const.MINIGUN_FIRE_RATE){
                ammo.addPlayerBullet(Const.LANES[topInLane - 1].getObstacle());
                fireRateCount = 0;
            }
        }
        if (ammo.getMagazineSize() <= 0){
            player.setGun(false);
            player.setMinigun(false);
            player.setRow(0);
            if (player.getShielded() == true){player.setRow(3);}
        }
    }
    private void generateObstacles(){
        if (Const.LANE1.getObstacleGenerated() == false && Const.LANE2.getObstacleGenerated() == false){
            Const.LANE1.generateObstacle();
        }
        // Lanes Inbetween
        for (int i = 1; i < Const.LANES.length - 1; i++){
            if(Const.LANES[i-1].getObstacleGenerated() == false && Const.LANES[i].getObstacleGenerated() == false && 
               Const.LANES[i + 1].getObstacleGenerated() == false){ // So that 1 opening doesnt generate 2 obstacles
                Const.LANES[i].generateObstacle();
            }
        }
        // Last Lane
        if (Const.LANE5.getObstacleGenerated() == false && Const.LANE4.getObstacleGenerated() == false){
            Const.LANE5.generateObstacle();
        }
    }
    private void generatePowerups(){
        // Gun powerup
        if(gunGenerated == false){
            int powerupX = generateRandomX(Const.MAX_GUN_POWERUP_DISTANCE, Const.MIN_GUN_POWERUP_DISTANCE);
            int laneNum = generateRandomLane();
            GunPowerup gun = new GunPowerup(powerupX, player);
            Const.LANES[laneNum - 1].addPowerup(gun);
            gunGenerated = true;
        }
        // Minigun powerup
        if(minigunGenerated == false){
            int powerupX = generateRandomX(Const.MAX_MINIGUN_POWERUP_DISTANCE, Const.MIN_MINIGUN_POWERUP_DISTANCE);
            int laneNum = generateRandomLane();
            MinigunPowerup minigun = new MinigunPowerup(powerupX, player);
            Const.LANES[laneNum - 1].addPowerup(minigun);
            minigunGenerated = true;
        }
        // Shield powerup
        if(shieldGenerated == false && player.getShielded() == false){
            int powerupX = generateRandomX(Const.MAX_SHIELD_POWERUP_DISTANCE, Const.MIN_SHIELD_POWERUP_DISTANCE);
            int laneNum = generateRandomLane();
            ShieldPowerup shield = new ShieldPowerup(powerupX, player);
            Const.LANES[laneNum - 1].addPowerup(shield);
            shieldGenerated = true;
        }
        // Extra Life powerup
        if(lifeGenerated == false && player.getLives() < Const.PLAYER_MAX_LIVES){
            int powerupX = generateRandomX(Const.MAX_LIFE_POWERUP_DISTANCE, Const.MIN_LIFE_POWERUP_DISTANCE);
            int laneNum = generateRandomLane();
            LifePowerup extraLife = new LifePowerup(powerupX, player);
            Const.LANES[laneNum - 1].addPowerup(extraLife);
            lifeGenerated = true;
        }
    }
    private int generateRandomX(int max, int min){
        return (int)((max - min) * Math.random() + min);
    }
    private int generateRandomLane(){
        return (int)((Const.LANES.length - 1) * Math.random() + 1);
    }
    private static Boss createBoss(){
        if (boss == null){
            int playerInLane = (int)((player.getY() - 125) / 160);
            int bossSpawnY = (playerInLane * 160) - 5;
            Boss newBoss = new Boss(760, bossSpawnY, (bossMinHealth + (bossCount * bossHPMultiplier)), player);
            bossCount = bossCount + 1;
            return newBoss;
        }
        else if (boss != null && boss.getHealth() > 0){
            BossKillBullet deathBullet = new BossKillBullet(800);
            ammo.addBossBullet(deathBullet);
        }
        return boss;
    }
    
    public void draw(Graphics g){
        drawHud(g);
        drawGameArea(g);
        gameWindow.repaint();
    }
    
    // Draw parts of the game
    private void drawHud(Graphics g){
        g.setColor(Const.INFO_RECT_COLOR);
        ((Graphics2D)g).fill(Const.INFO_RECT); 
        if (player.getShielded() == false){
            Const.NORMAL_HEAD.draw(g, 5, 19); 
        }else{
            Const.SHIELDED_HEAD.draw(g, 5, 19);
        }
        for (int i = 0; i < player.getLives(); i++){
            Const.HEART.draw(g, (6 + (i * 21)), 81);
        }
        if (player.getGun() == true){
            Const.GUN_HUD_IMAGE.draw(g, 66, 15);
            Text bulletCount = new Text(Integer.toString(ammo.getMagazineSize()), Const.BULLET_COUNT_FONT, 
                                        Color.WHITE, 190, 10);
            bulletCount.draw(g);
        }else if (player.getMinigun() == true){
            Const.MINIGUN_HUD_IMAGE.draw(g, 66, 20);
            Text bulletCount = new Text(Integer.toString(ammo.getMagazineSize()), Const.BULLET_COUNT_FONT, 
                                        Color.WHITE, 240, 10);
            bulletCount.draw(g);
        }
        Const.HUD_TITLE_TEXT.draw(g);
        g.setFont(Const.DISTANCE_FONT);
        g.drawString((Integer.toString(meters) + "m"), 10, 19);
        
        // Boss part
        if (boss != null){
            g.setColor(Const.BOSS_UNDER_HEALTHBAR_COLOR);
            ((Graphics2D)g).fill(Const.BOSS_UNDER_HEALTHBAR); 
            if (boss.getHealth() > 0){
                int bossPrecentHealth = 100 - (int)(((double)boss.getHealth() / boss.getMaxHealth()) * 100);
                g.setColor(Const.BOSS_HEALTHBAR_COLOR);
                g.fillRect((Const.BOSS_HEALTHBAR_X + bossPrecentHealth), Const.BOSS_HEALTHBAR_Y, 
                           (Const.BOSS_HEALTHBAR_WIDTH - bossPrecentHealth), Const.BOSS_HEALTHBAR_HEIGHT);
                Const.BOSS_ALIVE_ICON.draw(g, Const.WIDTH - 115, 5);
            }
            else{
                Const.BOSS_DEAD_ICON.draw(g, 1085, 5);
            }
        }
    }
    private void drawGameArea(Graphics g){
        background.draw(g);
            player.draw(g);
            Const.LINE1.draw(g);
            Const.LINE2.draw(g);
            Const.LINE3.draw(g);
            Const.LINE4.draw(g);
            Const.LINE5.draw(g);
            Const.LINE6.draw(g);
            for (int i = 0; i < Const.LANES.length; i++){
                Const.LANES[i].draw(g);
            }
            if (boss != null){
                boss.draw(g);
            }
            ammo.drawBullets(g);
    }
    public void setDifficultySettings(int[] settings){
        this.player.setLives(settings[0]);
        this.bossMinHealth = settings[1];
        this.bossHPMultiplier = settings[2];
    }
//------------------------------------------------------------------------------------------
// Window and frame methods
    public void setGameWindow(JFrame gameWindow){
        this.gameWindow = gameWindow;
    }
    public void setCards(JPanel cards){
        this.cards = cards;
    }
}