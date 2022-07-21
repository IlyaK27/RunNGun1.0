/**
 * Final Game Const Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 */

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.awt.FontFormatException;

public final class Const{
    // Screen
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 1000;
    public static final int FRAME_PERIOD = 100;
    public static final int GAME_FRAME_PERIOD = 10;
    
    // Game things
    public static final int STEP = 10; 
    public static final Color INFO_RECT_COLOR = new Color(53, 28, 117);
    public static final Rectangle INFO_RECT = new Rectangle(0, 0, 2000, 100);
    public static final Color LINE_COLOR = new Color(153, 153, 153);
    public static final int LINE_HEIGHT = 10;
    public static final int LANE_HEIGHT = 150;
    public static final int MIN_OPEN_VALUE = 2500;
    public static final int MAX_OPEN_VALUE = 10000;
    public static final int LINE1_TOP = 125;
    public static final Line LINE1 = new Line(0, (int)((MAX_OPEN_VALUE - MIN_OPEN_VALUE) * Math.random() + MIN_OPEN_VALUE), 
                                              LINE1_TOP, MIN_OPEN_VALUE, MAX_OPEN_VALUE, LINE_HEIGHT, Game.player);
    public static final Line LINE2 = new Line(0, (int)((MAX_OPEN_VALUE - MIN_OPEN_VALUE) * Math.random() + MIN_OPEN_VALUE), 
                                              (LINE1_TOP + (LINE_HEIGHT + LANE_HEIGHT)), MIN_OPEN_VALUE, MAX_OPEN_VALUE, 
                                              LINE_HEIGHT, Game.player);
    public static final Line LINE3 = new Line(0, (int)((MAX_OPEN_VALUE - MIN_OPEN_VALUE) * Math.random() + MIN_OPEN_VALUE), 
                                              (LINE1_TOP + (2 * (LINE_HEIGHT + LANE_HEIGHT))), MIN_OPEN_VALUE, MAX_OPEN_VALUE, 
                                              LINE_HEIGHT, Game.player);
    public static final Line LINE4 = new Line(0, (int)((MAX_OPEN_VALUE - MIN_OPEN_VALUE) * Math.random() + MIN_OPEN_VALUE), 
                                              (LINE1_TOP + (3 * (LINE_HEIGHT + LANE_HEIGHT))), MIN_OPEN_VALUE, MAX_OPEN_VALUE, 
                                              LINE_HEIGHT, Game.player);
    public static final Line LINE5 = new Line(0, (int)((MAX_OPEN_VALUE - MIN_OPEN_VALUE) * Math.random() + MIN_OPEN_VALUE), 
                                              (LINE1_TOP + (4 * (LINE_HEIGHT + LANE_HEIGHT))), MIN_OPEN_VALUE, MAX_OPEN_VALUE, 
                                              LINE_HEIGHT, Game.player);
    public static final Line LINE6 = new Line(0, (int)((MAX_OPEN_VALUE - MIN_OPEN_VALUE) * Math.random() + MIN_OPEN_VALUE), 
                                              (LINE1_TOP + (5 * (LINE_HEIGHT + LANE_HEIGHT))), MIN_OPEN_VALUE, MAX_OPEN_VALUE, 
                                              LINE_HEIGHT, Game.player);
    public static final Line[] LINES = {LINE1, LINE2, LINE3, LINE4, LINE5, LINE6};
    public static final UpperLane LANE1 = new UpperLane(LINE1, LINE2);
    public static final Lane LANE2 = new Lane(LINE2, LINE3);
    public static final Lane LANE3 = new Lane(LINE3, LINE4);
    public static final Lane LANE4 = new Lane(LINE4, LINE5);
    public static final LowerLane LANE5 = new LowerLane(LINE5, LINE6); 
    public static final Lane[] LANES = {LANE1, LANE2, LANE3, LANE4, LANE5};
    private static final String SCROLLING_BACKGROUND_IMAGE_NAME = "extra_files/images/gamebackground.png";
    public static final ScrollingBackground SCROLLING_BACKGROUND = new ScrollingBackground
        (SCROLLING_BACKGROUND_IMAGE_NAME, SCROLLING_BACKGROUND_IMAGE_NAME);
    
    // Difficulty settings
    private static final int EASY_PLAYER_LIVES = 3;
    private static final int EASY_BOSS_MIN_HP = 750;
    private static final int EASY_BOSS_HP_MULTIPLIER = 250;
    public static final int[] EASY_SETTINGS = {EASY_PLAYER_LIVES, EASY_BOSS_MIN_HP, EASY_BOSS_HP_MULTIPLIER};
    private static final int NORMAL_PLAYER_LIVES = 3;
    private static final int NORMAL_BOSS_MIN_HP = 750;
    private static final int NORMAL_BOSS_HP_MULTIPLIER = 300;
    public static final int[] NORMAL_SETTINGS = {NORMAL_PLAYER_LIVES, NORMAL_BOSS_MIN_HP, NORMAL_BOSS_HP_MULTIPLIER};
    private static final int HARD_PLAYER_LIVES = 2;
    private static final int HARD_BOSS_MIN_HP = 1250;
    private static final int HARD_BOSS_HP_MULTIPLIER = 350;
    public static final int[] HARD_SETTINGS = {HARD_PLAYER_LIVES, HARD_BOSS_MIN_HP, HARD_BOSS_HP_MULTIPLIER};
    public static final int[][] SETTINGS = {EASY_SETTINGS, NORMAL_SETTINGS, HARD_SETTINGS};
    
    // Player things
    public static final int STARTING_X = 30;
    public static final int STARTING_Y = 495;
    public static final int JUMP_SPEED = -32;
    public static final int DIVE_SPEED = -14;
    public static final int DEATH_SPEED = -18;
    public static final int GRAVITY = 2;
    public static final int PLAYER_HEIGHT = 110;
    public static final int PLAYER_CROUCH_HEIGHT = 65;
    public static final int LIFE_COUNT = 3;
    public static final int PLAYER_MAX_LIVES = 3;
    public static final String PLAYER_PIC_NAME = "extra_files/images/Player_Animations/sprite";
    public static final int PLAYER_ROWS = 7;
    public static final int PLAYER_COLUMNS = 11;
    public static final int PLAYER_NAME_MAX_LENGTH = 12; // 12 Characters
    
    // Boss things
    public static final String BOSS_PIC_NAME = "extra_files/images/Boss_Animations/boss";
    public static final int BOSS_ROWS = 4;
    public static final int BOSS_COLUMNS = 9;
    public static final Image BOSS_ALIVE_ICON = loadImage("extra_files/images/bossAliveFace.png");
    public static final Image BOSS_DEAD_ICON = loadImage("extra_files/images/bossDEADFace.png");
    public static final int BOSS_HEALTHBAR_X = 980;
    public static final int BOSS_HEALTHBAR_Y = 20;
    public static final int BOSS_HEALTHBAR_WIDTH = 100;
    public static final int BOSS_HEALTHBAR_HEIGHT = 60;
    public static final Color BOSS_UNDER_HEALTHBAR_COLOR = new Color(102, 102, 102);
    public static final Color BOSS_HEALTHBAR_COLOR = Color.YELLOW;
    public static final Rectangle BOSS_UNDER_HEALTHBAR = new Rectangle(BOSS_HEALTHBAR_X, BOSS_HEALTHBAR_Y, 
                                                                       BOSS_HEALTHBAR_WIDTH, BOSS_HEALTHBAR_HEIGHT);
    public static final int BOSS_SMALL_BULLET_SPEED = -20;
    public static final int BOSS_BIG_BULLET_SPEED = -5;
    public static final Image BOSS_SMALL_BULLET_IMAGE = loadImage("extra_files/images/Boss_Animations/bossBullet.png");
    public static final Image BOSS_BIG_BULLET_IMAGE = loadImage("extra_files/images/Boss_Animations/bossBigBullet.png");
    public static final Image BOSS_KILL_ATTACK_IMAGE = loadImage("extra_files/images/Boss_Animations/bossKillAttack.png");
    public static final int BOSS_BULLET_DAMAGE = 1; // 1 life
    public static final int BOSS_KILL_BULLET_DAMAGE = 999; // instant death
    public static final int BOSS_GRAVITY = 1;
    public static final int BOSS_JUMPSPEED = -30;
    
    // Obstacle stuff
    public static final int OBSTACLE_WIDTH = 10;
    public static final int OBSTACLE_HEIGHT = 150;
    public static final Color METAL_COLOR = new Color(153, 153, 153);
    public static final Color WOOD_COLOR = new Color(153, 102, 0);
    public static final int METAL_HEALTH = 400;
    public static final int WOOD_HEALTH = 150;
    public static final int MIN_OBSTACLE_DISTANCE = 20; //minimum distance obstacle is from an opening
    public static final int MAX_OBSTACLE_DISTANCE = 150; //max distance obstacle is from an opening
    
    // Powerup stuff
    public static final Image GUN_POWERUP_IMAGE = loadImage("extra_files/images/gunPowerup.png");
    public static final Image MINIGUN_POWERUP_IMAGE = loadImage("extra_files/images/minigunPowerup.png");
    public static final Image SHIELD_POWERUP_IMAGE = loadImage("extra_files/images/shieldPowerup.png");
    public static final Image LIFE_POWERUP_IMAGE = loadImage("extra_files/images/heartPowerup.png");
    
    public static final int MAX_GUN_POWERUP_DISTANCE = 5000;
    public static final int MIN_GUN_POWERUP_DISTANCE = 7500;
    public static final int MAX_MINIGUN_POWERUP_DISTANCE = 7500;
    public static final int MIN_MINIGUN_POWERUP_DISTANCE = 12500;
    public static final int MAX_SHIELD_POWERUP_DISTANCE = 10000;
    public static final int MIN_SHIELD_POWERUP_DISTANCE = 12500;
    public static final int MAX_LIFE_POWERUP_DISTANCE = 17500;
    public static final int MIN_LIFE_POWERUP_DISTANCE = 25000;
    
    // Player Bullet Stuff
    public static final int PISTOL_BULLET_WIDTH = 10;
    public static final int MINIGUN_BULLET_WIDTH = 6;
    public static final int BULLET_HEIGHT = 6;
    public static final int BULLET_SPEED = 20; 
    public static final int PISTOL_MAGAZINE_SIZE = 20;
    public static final int MINIGUN_MAGAZINE_SIZE = 100;
    public static final int PISTOL_FIRE_RATE = 5;
    public static final int MINIGUN_FIRE_RATE = 2;
    public static final int PISTOL_DAMAGE = 50;
    public static final int MINIGUN_DAMAGE = 30;

    // Font stuff
    private static final String TARRGET_FONT_FILE = "extra_files/fonts/TarrgetHalfToneItalic-ozyV.ttf";
    private static final String RAUBFONT_FONT_FILE = "extra_files/fonts/RaubFont.ttf";
    public static final Font MENU_TITLE_FONT = loadFont(TARRGET_FONT_FILE, Font.TRUETYPE_FONT, Font.PLAIN, 64);
    
    public static final Font GAME_TITLE_FONT = loadFont(TARRGET_FONT_FILE, Font.TRUETYPE_FONT, Font.PLAIN, 95);
    public static final Font BULLET_COUNT_FONT = loadFont(RAUBFONT_FONT_FILE, Font.TRUETYPE_FONT, Font.PLAIN, 60);
    public static final Font DISTANCE_FONT = loadFont(RAUBFONT_FONT_FILE, Font.TRUETYPE_FONT, Font.PLAIN, 20);
    
    // Things on game HUD
    public static final Image GUN_HUD_IMAGE = loadImage("extra_files/images/gunHud.png");
    public static final Image MINIGUN_HUD_IMAGE = loadImage("extra_files/images/minigunHud.png");
    public static final Image NORMAL_HEAD = new Image("extra_files/images/normalHead.png");
    public static final Image SHIELDED_HEAD = new Image("extra_files/images/shieldedHead.png");
    public static final Image HEART = new Image("extra_files/images/heart.png");
    public static final Text HUD_TITLE_TEXT = new Text("RUNNGUN", GAME_TITLE_FONT, Color.WHITE, (WIDTH / 2), -25);
    
    // Menu Backgrounds
    public static final Image MENU_BACKGROUND = loadImage("extra_files/images/screen_images/main_background.png");
    private static final Image GENERAL_RULES = loadImage("extra_files/images/screen_images/general_rules.png");
    private static final Image GENERAL_RULES_PICS1 = loadImage("extra_files/images/screen_images/general_rules_pictures1.png");
    private static final Image GENERAL_RULES_PICS2 = loadImage("extra_files/images/screen_images/general_rules_pictures2.png");
    private static final Image CONTROL_RULES = loadImage("extra_files/images/screen_images/control_rules.png");
    private static final Image POWERUP_RULES = loadImage("extra_files/images/screen_images/powerup_rules.png");
    private static final Image POWERUP_RULES_PICS = loadImage("extra_files/images/screen_images/powerup_rules_pictures.png");
    private static final Image BOSS_RULES = loadImage("extra_files/images/screen_images/boss_rules.png");
    private static final Image BOSS_RULES_PICTURES = loadImage("extra_files/images/screen_images/boss_rules_pictures.png");
    public static final Image[] HOW_TO_PLAY_RULES = {GENERAL_RULES, GENERAL_RULES_PICS1, GENERAL_RULES_PICS2, CONTROL_RULES, 
        POWERUP_RULES, POWERUP_RULES_PICS, BOSS_RULES, BOSS_RULES_PICTURES};
    
    // Menu button things and fonts
    public static final int HALF_WIDTH = WIDTH / 2;
    public static final int BUTTON_HORIZONTAL_SPACE = 10;
    public static final int BUTTON_VERTICAL_SPACE = 5;
    public static final int RADIUS = 40;
    public static final Color LARGE_BUTTON_IN_COLOR = new Color(71, 56, 117);
    public static final Color LARGE_BUTTON_BORDER_COLOR = new Color(142, 124, 195);
    public static final Color LARGE_BUTTON_HOVER_COLOR = new Color(32, 18, 77);
    public static final Color SMALL_BUTTON_IN_COLOR = new Color(153, 153, 153); // Grey
    public static final Color SMALL_BUTTON_HOVER_COLOR = new Color(102, 102, 102); // Dark Grey
    public static final int GO_BACK_X = 100;
    public static final int GO_BACK_Y = 900;
    public static final int GO_BACK_RADIUS = 20;
    public static final int CONTINUE_X = 1100;
    public static final Font MENU_BUTTON_FONT = loadFont(RAUBFONT_FONT_FILE, Font.TRUETYPE_FONT, Font.PLAIN, 95);
    public static final Font MEDIUM_BUTTON_FONT = loadFont(RAUBFONT_FONT_FILE, Font.TRUETYPE_FONT, Font.PLAIN, 45);
    public static final Font SMALL_BUTTON_FONT = loadFont(RAUBFONT_FONT_FILE, Font.TRUETYPE_FONT, Font.PLAIN, 20);
    // Arrow Buttons
    public static final int[] LEFT_ARROW_X = {20, 40, 40, 70, 70, 40, 40};
    public static final int[] LEFT_ARROW_Y = {905, 885, 895, 895, 915, 915, 925};
    public static final int LEFT_ARROW_CENTERX = 50;
    public static final int[] RIGHT_ARROW_X = {1112, 1142, 1142, 1162, 1142, 1142, 1112};
    public static final int[] RIGHT_ARROW_Y = {895, 895, 885, 905, 925, 915, 915};
    public static final int RIGHT_ARROW_CENTERX = 1140;
    public static final int ARROW_BUTTON_WIDTH = 50;
    public static final int ARROW_BUTTON_HEIGHT = 40;
    public static final int ARROW_BUTTON_Y = 880;
    public static final int ARROW_POINTS = 7;
    
//---------------------------------------------------------------------------------------------------------------------
// Methods
    private static Font loadFont(String fontFileName, int fontType, int fontStyle, int fontSize){
        Font errorFont = new Font("Bree Serif", fontStyle, fontSize);
        Font font;
        try{
            Font actualFont = Font.createFont(fontType, new File(fontFileName));
            font = actualFont.deriveFont((float)fontSize);
        } catch (IOException ex) {
            System.out.println("Font Files could not be read");
            font = errorFont;
        } catch (FontFormatException ex){
            System.out.println("Font Files are invalid");  
            font = errorFont;
        }
        return font;
    }
    private static Image loadImage(String imageName){
        Image image = new Image(imageName);
        return image;
    }
    private Const(){
    }
}