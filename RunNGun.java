/**
 * Final Game RunNGun Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class is the game itself (not the playable area but basically the entire application as a whole)
 * It has many different screens that are all inner classes because those inner screenpanel classes should not be able 
 * to exist without this class
 */

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.CardLayout;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.lang.Character;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Timer;

/**
 * The RunNGun class runs a game through a graphical interface.
 */
public class RunNGun{
    private JPanel cards;
    private JFrame gameWindow;

    // Labels for the different screens.
    public final static String MENU_PANEL = "main menu screen";
    public final static String SELECT_PANEL = "select screen";
    public final static String HOW_TO_PLAY_PANEL = "how to play screen";
    public final static String LEADERBOARD_PANEL = "leaderboard screen";
    public final static String CREDITS_PANEL = "credits screen";
    public final static String GAME_PANEL = "game screen";
    public final static String GAME_OVER_PANEL = "game over screen";
    public final static String PAUSE_PANEL = "pause screen";

    // The different screens.
    private JPanel menuScreen;
    private JPanel selectScreen;
    private JPanel howToPlayScreen;
    private JPanel leaderboardScreen;
    private JPanel creditsScreen;
    private JPanel gameScreen;
    private JPanel gameOverScreen;
    private JPanel pauseScreen;
    
    Image menuBackgroundImage;

    private static Leaderboard easyLeaderboard = new Leaderboard("extra_files/leaderboards/easyLeaderboard.txt");
    private static Leaderboard normalLeaderboard = new Leaderboard("extra_files/leaderboards/normalLeaderboard.txt");
    private static Leaderboard hardLeaderboard = new Leaderboard("extra_files/leaderboards/hardLeaderboard.txt");
    private static Leaderboard[] leaderboards = {easyLeaderboard, normalLeaderboard, hardLeaderboard};
    private String[] difficultyNames = {"Easy", "Normal", "Hard"};
    
    private Game game;
    private String playerName = "";
    public static int gameDifficulty = 1; // 0 = easy, 1 = normal, 2 = hard
    private Timer drawLoop;

    public RunNGun() {
        this.game = new Game(gameWindow, cards);
        this.initializeWindow();
        this.game.setGameWindow(gameWindow);
        this.game.setCards(cards);
        
        // Initialize the draw loop.
        this.drawLoop = new Timer(Const.FRAME_PERIOD, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                runDrawLoop();
            }
        });
    }

    /**
     * This method initializes the windows and screens for the RunNgun Game.
     */
    private void initializeWindow() {
        // Initialize the window.
        this.gameWindow = new JFrame("RunNGun");
        this.gameWindow.setPreferredSize(new Dimension(Const.WIDTH, Const.HEIGHT));// adding because of window problems   
        this.gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.cards = new JPanel(new CardLayout());
        
        menuBackgroundImage = Const.MENU_BACKGROUND;

        //Initialize the screens.
        this.menuScreen = new MenuScreenPanel(Const.MENU_BACKGROUND);
        this.selectScreen = new SelectScreenPanel(Const.MENU_BACKGROUND);
        this.howToPlayScreen = new HowToPlayScreenPanel(Const.MENU_BACKGROUND);
        this.leaderboardScreen = new LeaderBoardScreenPanel(Const.MENU_BACKGROUND);
        this.creditsScreen = new CreditsScreenPanel(Const.MENU_BACKGROUND);
        this.gameScreen = new GameScreenPanel(null);
        this.gameOverScreen = new GameOverScreenPanel(Const.MENU_BACKGROUND);
        this.pauseScreen = new PauseScreenPanel(Const.MENU_BACKGROUND);

        // Add the screens to the window manager.
        cards.add(menuScreen, MENU_PANEL);
        cards.add(selectScreen, SELECT_PANEL);
        cards.add(howToPlayScreen, HOW_TO_PLAY_PANEL);
        cards.add(leaderboardScreen, LEADERBOARD_PANEL);
        cards.add(creditsScreen, CREDITS_PANEL);
        cards.add(gameScreen, GAME_PANEL);
        cards.add(gameOverScreen, GAME_OVER_PANEL);
        cards.add(pauseScreen, PAUSE_PANEL);

        gameWindow.add(cards);
        gameWindow.setVisible(true);
        gameWindow.setResizable(false);
        gameWindow.pack();
    }

    /**
     * This method draws one frame of the main game once. It does this at the specified framerate.
     */
    private void runDrawLoop() {
        gameWindow.repaint();
    }   

    /**
     * This class implements an ActionListener that swaps to the specified panel when 
     * an action is performed.
     */
    public static class ScreenSwapper implements ActionListener {
        private String nextPanel;
        private JPanel cards;
        /*
         * Constructs a ScreenSwapper object with the screen to switch to.
         * @param cards The JPanel that stores the different screens.
         * @param nextPanel The screen to switch to.
         */
        public ScreenSwapper(JPanel cards, String nextPanel) {
            this.nextPanel = nextPanel;
            this.cards = cards;
        }
        @Override
        public void actionPerformed(ActionEvent event) {
            this.swap();
        }
        /** 
         * This method swaps the current screen to the set screen.
        */
        public void swap() {
            System.out.println("Switching to " + this.nextPanel);

            // Switch to the specified panel.
            CardLayout layout = (CardLayout) this.cards.getLayout();
            layout.show(this.cards, this.nextPanel);
        }
    }
    
    /**
     * This method draws one frame of the main game once. It does this at the specified framerate.
     */
    public static void updateLeaderboard(String playerName, int score) {
        if (!(playerName.equals(""))){ //No Name will not save
            leaderboards[gameDifficulty].addEntry(playerName, score);
            leaderboards[gameDifficulty].saveToFile();
        }
    }
    /**
     * This class represents the main menu/title screen in the RunNGun Game.
     */
    public class MenuScreenPanel extends ScreenPanel {
        private TextButton playButton;
        private TextButton howToPlayButton;
        private TextButton leaderboardButton; 
        private TextButton creditsButton;

        public MenuScreenPanel(Image backgroundSprite) {
            super(backgroundSprite);
            Font buttonFont = Const.MENU_BUTTON_FONT;
            Color fontColor = Color.WHITE;
            // Initialize the buttons.
            this.playButton = new TextButton(gameWindow, cards, SELECT_PANEL, "Play", buttonFont, fontColor, 
                                         Const.LARGE_BUTTON_IN_COLOR, Const.LARGE_BUTTON_BORDER_COLOR, 
                                         Const.LARGE_BUTTON_HOVER_COLOR, Const.HALF_WIDTH, 260, Const.RADIUS);
            
            this.howToPlayButton = new TextButton(gameWindow, cards, HOW_TO_PLAY_PANEL, "How To Play", buttonFont, fontColor, 
                                         Const.LARGE_BUTTON_IN_COLOR, Const.LARGE_BUTTON_BORDER_COLOR, 
                                         Const.LARGE_BUTTON_HOVER_COLOR, Const.HALF_WIDTH, 430, Const.RADIUS);
            
            this.leaderboardButton = new TextButton(gameWindow, cards, LEADERBOARD_PANEL, "Leaderboard", buttonFont, fontColor, 
                                         Const.LARGE_BUTTON_IN_COLOR, Const.LARGE_BUTTON_BORDER_COLOR, 
                                         Const.LARGE_BUTTON_HOVER_COLOR, Const.HALF_WIDTH, 600, Const.RADIUS);
            
            this.creditsButton = new TextButton(gameWindow, cards, CREDITS_PANEL, "Credits", buttonFont, fontColor, 
                                         Const.LARGE_BUTTON_IN_COLOR, Const.LARGE_BUTTON_BORDER_COLOR, 
                                         Const.LARGE_BUTTON_HOVER_COLOR, Const.HALF_WIDTH, 770, Const.RADIUS);
            
            // Add the listeners for the screens.
            this.addMouseListener(playButton.new BasicMouseListener());
            this.addMouseMotionListener(playButton.new InsideButtonMotionListener());
            this.addMouseListener(howToPlayButton.new BasicMouseListener());
            this.addMouseMotionListener(howToPlayButton.new InsideButtonMotionListener());
            this.addMouseListener(leaderboardButton.new BasicMouseListener());
            this.addMouseMotionListener(leaderboardButton.new InsideButtonMotionListener());
            this.addMouseListener(creditsButton.new BasicMouseListener());
            this.addMouseMotionListener(creditsButton.new InsideButtonMotionListener());
            this.setFocusable(true);
            this.addComponentListener(this.FOCUS_WHEN_SHOWN);
        }
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            this.playButton.draw(graphics);
            this.howToPlayButton.draw(graphics);
            this.leaderboardButton.draw(graphics);
            this.creditsButton.draw(graphics);
        }
        public final ComponentAdapter FOCUS_WHEN_SHOWN = new ComponentAdapter(){
            public void componentShown(ComponentEvent event){
                requestFocusInWindow();
            }
        };
    }

    /**
     * This class represents the select screen in the RunNGun Game. It appears before the main game starts.
     */
    public class SelectScreenPanel extends ScreenPanel {
        private TextButton easyButton;
        private TextButton normalButton;
        private TextButton hardButton;
        private TextButton playerNameButton;
        private TextButton goBackButton;
        private GoToGameButton continueButton;
        private final int DIFFICULTY_BUTTON_Y = 395;
        Font titleFont = Const.MENU_BUTTON_FONT;
        Font mediumFont = Const.MEDIUM_BUTTON_FONT;
        Font smallButtonFonts = Const.SMALL_BUTTON_FONT;
        Color fontColor = Color.WHITE;
        Text name = new Text("", mediumFont, Color.WHITE, Const.HALF_WIDTH, (DIFFICULTY_BUTTON_Y + 350));
        SelectMouseListener mouseListener = new SelectMouseListener();  
        
        public SelectScreenPanel(Image backgroundSprite) {
            super(backgroundSprite);   
            // Initialize the buttons.
            this.easyButton = new TextButton(gameWindow, null, null, "Easy", mediumFont, fontColor, Const.LARGE_BUTTON_IN_COLOR, 
                                        Const.LARGE_BUTTON_BORDER_COLOR, Const.LARGE_BUTTON_HOVER_COLOR, 
                                        (Const.HALF_WIDTH / 2) + 10, DIFFICULTY_BUTTON_Y, Const.RADIUS);
                
            this.normalButton = new TextButton(gameWindow, null, null, "Normal", mediumFont, fontColor, Const.LARGE_BUTTON_IN_COLOR, 
                                        Const.LARGE_BUTTON_BORDER_COLOR, Const.LARGE_BUTTON_HOVER_COLOR, 
                                        Const.HALF_WIDTH, DIFFICULTY_BUTTON_Y, Const.RADIUS);
                
                
            this.hardButton = new TextButton(gameWindow, null, null, "Hard", mediumFont, fontColor, Const.LARGE_BUTTON_IN_COLOR, 
                                        Const.LARGE_BUTTON_BORDER_COLOR, Const.LARGE_BUTTON_HOVER_COLOR, 
                                        (Const.HALF_WIDTH + (Const.HALF_WIDTH / 2)) - 10, DIFFICULTY_BUTTON_Y, Const.RADIUS);
            // Empty text to make the button large
            this.playerNameButton = new TextButton(gameWindow, null, null, "                                ", mediumFont, 
                                               fontColor, Const.SMALL_BUTTON_IN_COLOR, Color.BLACK, Const.SMALL_BUTTON_HOVER_COLOR, 
                                               Const.HALF_WIDTH, DIFFICULTY_BUTTON_Y + 350, 0);

            this.goBackButton = new TextButton(gameWindow, cards, MENU_PANEL, "Go Back", smallButtonFonts, fontColor, 
                                           Const.SMALL_BUTTON_IN_COLOR, Color.BLACK, Const.SMALL_BUTTON_HOVER_COLOR, 
                                           Const.GO_BACK_X, Const.GO_BACK_Y, Const.GO_BACK_RADIUS);
            
            this.continueButton = new GoToGameButton(game, gameWindow, cards, GAME_PANEL, "Continue", smallButtonFonts, fontColor, 
                                           Const.SMALL_BUTTON_IN_COLOR, Color.BLACK, Const.SMALL_BUTTON_HOVER_COLOR, 
                                           Const.CONTINUE_X, Const.GO_BACK_Y, Const.GO_BACK_RADIUS);

            this.addMouseListener(mouseListener);

            // Add the listeners for the screens.
            this.addMouseListener(easyButton.new BasicMouseListener());
            this.addMouseMotionListener(easyButton.new InsideButtonMotionListener());
            this.addMouseListener(normalButton.new BasicMouseListener());
            this.addMouseMotionListener(normalButton.new InsideButtonMotionListener());
            this.addMouseListener(hardButton.new BasicMouseListener());
            this.addMouseMotionListener(hardButton.new InsideButtonMotionListener());
            this.addMouseListener(playerNameButton.new BasicMouseListener());
            this.addMouseMotionListener(playerNameButton.new InsideButtonMotionListener());
            
            this.addMouseListener(goBackButton.new BasicMouseListener());
            this.addMouseMotionListener(goBackButton.new InsideButtonMotionListener());
            this.addMouseListener(continueButton.new ContinueMouseListener());
            this.addMouseMotionListener(continueButton.new InsideButtonMotionListener());

            this.addKeyListener(new NameKeyListener());
            
            this.setFocusable(true);
            this.addComponentListener(this.FOCUS_WHEN_SHOWN);
        }

        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.setColor(Const.INFO_RECT_COLOR);
            graphics.fillRoundRect(210, (DIFFICULTY_BUTTON_Y - 95), 780, 220, Const.RADIUS, Const.RADIUS);
            graphics.fillRoundRect(210, (DIFFICULTY_BUTTON_Y + 175), 780, 250, Const.RADIUS, Const.RADIUS);
            Text difficulty = new Text("Difficulty", titleFont, Color.WHITE, Const.HALF_WIDTH, (DIFFICULTY_BUTTON_Y - 115));
            Text chosenDifficulty = new Text("Current Diffuculty: ", mediumFont, Color.WHITE, Const.HALF_WIDTH, 
                                             (DIFFICULTY_BUTTON_Y + 60));
            
            if (gameDifficulty == 0){
                chosenDifficulty.setText("Current Diffuculty: Easy");
            }else if (gameDifficulty == 1){
                chosenDifficulty.setText("Current Diffuculty: Normal");
            }else if (gameDifficulty == 2){
                chosenDifficulty.setText("Current Diffuculty: Hard");
            }
            
            difficulty.draw(graphics);
            chosenDifficulty.draw(graphics);
            
            Text nameTitle = new Text("Name Select:", titleFont, Color.WHITE, Const.HALF_WIDTH, (DIFFICULTY_BUTTON_Y + 155));
            Text nameInfo1 = new Text("(If nothing entered your", 
                                     mediumFont, Color.WHITE, Const.HALF_WIDTH, (DIFFICULTY_BUTTON_Y + 250));
            Text nameInfo2 = new Text("highscore will not be saved)", 
                                     mediumFont, Color.WHITE, Const.HALF_WIDTH, (DIFFICULTY_BUTTON_Y + 290));
            nameTitle.draw(graphics);
            nameInfo1.draw(graphics);
            nameInfo2.draw(graphics);
            this.playerNameButton.draw(graphics);
            name.draw(graphics);
            
            this.easyButton.draw(graphics);
            this.normalButton.draw(graphics);
            this.hardButton.draw(graphics);
            this.goBackButton.draw(graphics);
            this.continueButton.draw(graphics);
        }
        public final ComponentAdapter FOCUS_WHEN_SHOWN = new ComponentAdapter(){
            public void componentShown(ComponentEvent event){
                requestFocusInWindow();
            }
        };
        
        // The SelectMouseListener class implements a mouse listener for toggling the difficulty of the game.
        public class SelectMouseListener implements MouseListener {
            public void mouseClicked(MouseEvent event) {
                int mouseX = event.getX();
                int mouseY = event.getY();
                if((mouseY >= DIFFICULTY_BUTTON_Y) && (mouseY <= (DIFFICULTY_BUTTON_Y + easyButton.getHeightVal()))) { // all buttons have same height
                    if ((mouseX >= easyButton.getXVal()) && (mouseX <= (easyButton.getXVal() + easyButton.getWidthVal()))) {
                        // Select easy diffuculty.
                        System.out.println("Changing diffuculty to easy");
                        normalButton.resetStayHeld();
                        hardButton.resetStayHeld();
                        gameDifficulty = 0;
                    } else if ((mouseX >= normalButton.getXVal()) && (mouseX <= (normalButton.getXVal() + normalButton.getWidthVal()))) {
                        // Select medium speed.
                        System.out.println("Changing diffuculty to normal");
                        easyButton.resetStayHeld();
                        hardButton.resetStayHeld();
                        gameDifficulty = 1;
                    } else if ((mouseX >= hardButton.getXVal()) && (mouseX <= (hardButton.getXVal() + hardButton.getWidthVal()))) {
                        // Select fast speed.
                        System.out.println("Changing diffuculty to hard");
                        easyButton.resetStayHeld();
                        normalButton.resetStayHeld();
                        gameDifficulty = 2;
                    }
                }
            }
            public void mousePressed(MouseEvent event) { }
            public void mouseReleased(MouseEvent event) { }
            public void mouseEntered(MouseEvent event) { }
            public void mouseExited(MouseEvent event) { }
        }
        public class NameKeyListener implements KeyListener {
            public void keyPressed(KeyEvent event) {
                int key = event.getKeyCode();
                String newChar = Character.toString((char)key);
                if (playerNameButton.getStayHeld() == true){
                    if (playerName.length() <= Const.PLAYER_NAME_MAX_LENGTH && key != 8){
                        playerName = playerName + newChar;
                    }
                    else if (key == 8 && playerName.length() > 0){
                        playerName = playerName.substring(0, (playerName.length() - 1));
                    }
                    name.setText(playerName);
                    game.player.setName(name.getText());
                }
                gameWindow.repaint();
            }
            public void keyReleased(KeyEvent event) { }
            public void keyTyped(KeyEvent event) { }
        }
    }
    /**
     * This class represents the how to play screen in the RunNGun Game. It teaches the controls and how to play the game.
     */
    public class HowToPlayScreenPanel extends ScreenPanel {
        private TextButton backToMenuButton;
        private ArrowButton leftArrowButton;
        private ArrowButton rightArrowButton;
        private final Font TITLE_FONT = Const.MENU_BUTTON_FONT;
        private final Font ENTRIES_FONT = Const.MEDIUM_BUTTON_FONT;
        private final Text LEADERBOARD_TITLE = new Text("Game Tips", TITLE_FONT, Color.WHITE, Const.HALF_WIDTH, 235);
        private Text subTitleText = new Text("General", ENTRIES_FONT, Color.WHITE, Const.HALF_WIDTH, 335);
        private int currentImage = 0;
        private Image[] images = Const.HOW_TO_PLAY_RULES;
        HowToPlayMouseListener mouseListener = new HowToPlayMouseListener();  
        public HowToPlayScreenPanel(Image backgroundSprite) {
            super(backgroundSprite);
            // Initialize the button.
            this.backToMenuButton = new TextButton(gameWindow, cards, MENU_PANEL, "Main Menu", ENTRIES_FONT, Color.WHITE, 
                                                   Const.LARGE_BUTTON_IN_COLOR, Const.LARGE_BUTTON_BORDER_COLOR, 
                                                   Const.LARGE_BUTTON_HOVER_COLOR, Const.HALF_WIDTH, 850, Const.RADIUS);
            
            this.leftArrowButton = new ArrowButton(gameWindow, Const.SMALL_BUTTON_IN_COLOR, Color.BLACK, 
                                                   Const.SMALL_BUTTON_HOVER_COLOR, Const.LEFT_ARROW_CENTERX, 
                                                   Const.ARROW_BUTTON_Y, 0, -1);
            this.rightArrowButton = new ArrowButton(gameWindow, Const.SMALL_BUTTON_IN_COLOR, Color.BLACK, 
                                                   Const.SMALL_BUTTON_HOVER_COLOR, Const.RIGHT_ARROW_CENTERX, 
                                                    Const.ARROW_BUTTON_Y, 0, 1);
            
            // Add the listeners for the screen.
            this.addMouseListener(mouseListener);
            this.addMouseListener(backToMenuButton.new BasicMouseListener());
            this.addMouseMotionListener(backToMenuButton.new InsideButtonMotionListener());
            this.addMouseMotionListener(leftArrowButton.new InsideButtonMotionListener());
            this.addMouseMotionListener(rightArrowButton.new InsideButtonMotionListener());
            
            this.setFocusable(true);
            this.addComponentListener(this.FOCUS_WHEN_SHOWN);
        }
        
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            images[currentImage].draw(graphics, 0, 0);
            this.LEADERBOARD_TITLE.draw(graphics);
            subTitleText.draw(graphics);
            this.backToMenuButton.draw(graphics);   
            this.leftArrowButton.draw(graphics);
            this.rightArrowButton.draw(graphics);
        }
        public final ComponentAdapter FOCUS_WHEN_SHOWN = new ComponentAdapter(){
            public void componentShown(ComponentEvent event){
                requestFocusInWindow();
            }
        };
        public class HowToPlayMouseListener implements MouseListener {
            public void mouseClicked(MouseEvent event) {
                int mouseX = event.getX();
                int mouseY = event.getY();
                if((mouseY >= Const.ARROW_BUTTON_Y) && (mouseY <= (Const.ARROW_BUTTON_Y + leftArrowButton.getHeightVal()))) { // all buttons have same height
                    if ((mouseX >= leftArrowButton.getXVal()) && (mouseX <= (leftArrowButton.getXVal() + leftArrowButton.getWidthVal()))) {
                        currentImage = currentImage - 1;
                        if (currentImage < 0){
                            currentImage = images.length - 1;
                        }
                        gameWindow.repaint();
                    } else if ((mouseX >= rightArrowButton.getXVal()) && (mouseX <= (rightArrowButton.getXVal() + rightArrowButton.getWidthVal()))) {
                        currentImage = currentImage + 1;
                        if (currentImage > (images.length - 1)){
                            currentImage = 0;
                        }
                        gameWindow.repaint();
                    }
                    if (currentImage >= 0 && currentImage <= 2){
                        subTitleText.setText("General");
                    }else if (currentImage == 3){
                        subTitleText.setText("Controls");
                    }else if (currentImage == 4 || currentImage == 5){
                        subTitleText.setText("Powerups");
                    }else if (currentImage == 6 || currentImage == 7){
                        subTitleText.setText("Boss");
                    }
                }
            }
            public void mousePressed(MouseEvent event) { }
            public void mouseReleased(MouseEvent event) { }
            public void mouseEntered(MouseEvent event) { }
            public void mouseExited(MouseEvent event) { }
        }
    }
    /**
     * This class represents the leaderboard.
     * Here the player can see past players names and scores
     * The leaderboard is taken from three text files (1 per difficulty)
     */
    public class LeaderBoardScreenPanel extends ScreenPanel {
        private TextButton backToMenuButton;
        private ArrowButton leftArrowButton;
        private ArrowButton rightArrowButton;
        private final Font TITLE_FONT = Const.MENU_BUTTON_FONT;
        private final Font ENTRIES_FONT = Const.MEDIUM_BUTTON_FONT;
        private final Text LEADERBOARD_TITLE = new Text("Leaderboard", TITLE_FONT, Color.WHITE, Const.HALF_WIDTH, 250);
        private final int MAX_LEADERBOARD_CHOICE = 2;
        private int leaderboardChoice = 0; // 0 = display easy leaderbaord, 1 = display normal leaderbaord, 2 = display hard leaderboard
        LeaderboardMouseListener mouseListener = new LeaderboardMouseListener();  
        public LeaderBoardScreenPanel(Image backgroundSprite) {
            super(backgroundSprite);
            easyLeaderboard.sort();
            normalLeaderboard.sort();
            hardLeaderboard.sort();
            // Initialize the button.
            this.backToMenuButton = new TextButton(gameWindow, cards, MENU_PANEL, "Main Menu", ENTRIES_FONT, Color.WHITE, 
                                                   Const.LARGE_BUTTON_IN_COLOR, Const.LARGE_BUTTON_BORDER_COLOR, 
                                                   Const.LARGE_BUTTON_HOVER_COLOR, Const.HALF_WIDTH, 850, Const.RADIUS);
            
            this.leftArrowButton = new ArrowButton(gameWindow, Const.SMALL_BUTTON_IN_COLOR, Color.BLACK, 
                                                   Const.SMALL_BUTTON_HOVER_COLOR, Const.LEFT_ARROW_CENTERX, 
                                                   Const.ARROW_BUTTON_Y, 0, -1);
            this.rightArrowButton = new ArrowButton(gameWindow, Const.SMALL_BUTTON_IN_COLOR, Color.BLACK, 
                                                   Const.SMALL_BUTTON_HOVER_COLOR, Const.RIGHT_ARROW_CENTERX, 
                                                    Const.ARROW_BUTTON_Y, 0, 1);
            
            // Add the listeners for the screen.
            this.addMouseListener(mouseListener);
            this.addMouseListener(backToMenuButton.new BasicMouseListener());
            this.addMouseMotionListener(backToMenuButton.new InsideButtonMotionListener());
            this.addMouseMotionListener(leftArrowButton.new InsideButtonMotionListener());
            this.addMouseMotionListener(rightArrowButton.new InsideButtonMotionListener());
            
            this.setFocusable(true);
            this.addComponentListener(this.FOCUS_WHEN_SHOWN);
        }
        
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.setColor(Const.INFO_RECT_COLOR);
            graphics.fillRoundRect(200, 260, 800, 500, Const.RADIUS, Const.RADIUS);
            Text leaderboardType = new Text(difficultyNames[leaderboardChoice], ENTRIES_FONT, Color.WHITE, Const.HALF_WIDTH, 350);
            this.LEADERBOARD_TITLE.draw(graphics);
            leaderboardType.draw(graphics);
            Leaderboard.Entry[] entries = leaderboards[leaderboardChoice].getEntries();
            Leaderboard.Entry entry;
            for(int i = 0; i < leaderboards[leaderboardChoice].getNumEntries() && i < 10; i++){
               entry = entries[i];
               Text entryText = new Text(((i + 1) + ". " + entry.getName() + " - " + entry.getScore() + "m"), ENTRIES_FONT, Color.WHITE, 
                                         Const.HALF_WIDTH, (400 + (i * 40)));
               entryText.draw(graphics);
            }
            this.backToMenuButton.draw(graphics);   
            this.leftArrowButton.draw(graphics);
            this.rightArrowButton.draw(graphics);
        }
        public final ComponentAdapter FOCUS_WHEN_SHOWN = new ComponentAdapter(){
            public void componentShown(ComponentEvent event){
                requestFocusInWindow();
            }
        };
        public class LeaderboardMouseListener implements MouseListener {
            public void mouseClicked(MouseEvent event) {
                int mouseX = event.getX();
                int mouseY = event.getY();
                if((mouseY >= Const.ARROW_BUTTON_Y) && (mouseY <= (Const.ARROW_BUTTON_Y + leftArrowButton.getHeightVal()))) { // all buttons have same height
                    if ((mouseX >= leftArrowButton.getXVal()) && (mouseX <= (leftArrowButton.getXVal() + leftArrowButton.getWidthVal()))) {
                        leaderboardChoice = leaderboardChoice - 1;
                        if (leaderboardChoice < 0){
                            leaderboardChoice = MAX_LEADERBOARD_CHOICE;
                        }
                        gameWindow.repaint();
                    } else if ((mouseX >= rightArrowButton.getXVal()) && (mouseX <= (rightArrowButton.getXVal() + rightArrowButton.getWidthVal()))) {
                        leaderboardChoice = leaderboardChoice + 1;
                        if (leaderboardChoice > MAX_LEADERBOARD_CHOICE){
                            leaderboardChoice = 0;
                        }
                        gameWindow.repaint();
                    }
                }
            }
            public void mousePressed(MouseEvent event) { }
            public void mouseReleased(MouseEvent event) { }
            public void mouseEntered(MouseEvent event) { }
            public void mouseExited(MouseEvent event) { }
        }
    }
    
    /**
     * This class represents the credits screen in the RunNGun Game.
     */
    public class CreditsScreenPanel extends ScreenPanel {
        TextButton goBackButton;
        Font nameFont = Const.MENU_BUTTON_FONT;
        Font goBackFont = Const.SMALL_BUTTON_FONT;
        Color fontColor = Color.WHITE;
        Text nameOfTheGOAT = new Text("Ilya Kononov", nameFont, Color.WHITE, Const.HALF_WIDTH, 400); // I am very funny I know
        public CreditsScreenPanel(Image backgroundSprite) {
            super(backgroundSprite);
            // Initialize the button.
            this.goBackButton = new TextButton(gameWindow, cards, MENU_PANEL, "Go Back", goBackFont, fontColor, 
                                           Const.SMALL_BUTTON_IN_COLOR, Color.BLACK, Const.SMALL_BUTTON_HOVER_COLOR, 
                                           Const.GO_BACK_X, Const.GO_BACK_Y, Const.GO_BACK_RADIUS);
            // Add the listeners for the screen.
            this.addMouseListener(goBackButton.new BasicMouseListener());
            this.addMouseMotionListener(goBackButton.new InsideButtonMotionListener());
            this.setFocusable(true);
            this.addComponentListener(this.FOCUS_WHEN_SHOWN);
        }
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            this.goBackButton.draw(graphics);
            graphics.setColor(Const.INFO_RECT_COLOR);
            graphics.fillRoundRect(210, 350, 780, 220, Const.RADIUS, Const.RADIUS);
            this.nameOfTheGOAT.draw(graphics);
        }
        public final ComponentAdapter FOCUS_WHEN_SHOWN = new ComponentAdapter(){
            public void componentShown(ComponentEvent event){
                requestFocusInWindow();
            }
        };
    }  
    /**
     * This class represents the actual game screen in the RunNGun Game. 
     */
    public class GameScreenPanel extends ScreenPanel {
        public GameScreenPanel(Image backgroundSprite) {
            super(backgroundSprite);
            this.setFocusable(true);
            this.addComponentListener(this.FOCUS_WHEN_SHOWN);
            this.addKeyListener(game.new GameKeyListener());
        }
        @Override
        public void paintComponent(Graphics graphics) {
            game.draw(graphics);
        }
        public final ComponentAdapter FOCUS_WHEN_SHOWN = new ComponentAdapter(){
            public void componentShown(ComponentEvent event){
                requestFocusInWindow();
            }
        };
    }

    /**
     * This class represents the game over screen in the game
     */
    public class GameOverScreenPanel extends ScreenPanel {
        private final Text GAME_OVER_TITLE= new Text("GAME OVER", Const.MENU_BUTTON_FONT, Color.WHITE, 
                                                     Const.HALF_WIDTH, 270);
        private final Text GAME_OVER_TEXT1 = new Text("Did you do well?", 
                                                      Const.MEDIUM_BUTTON_FONT, Color.WHITE, Const.HALF_WIDTH, 400);
        private final Text GAME_OVER_TEXT2 = new Text("Or did something go wrong?", 
                                                      Const.MEDIUM_BUTTON_FONT, Color.WHITE, Const.HALF_WIDTH, 460);
        private final Text GAME_OVER_TEXT3 = new Text("In any case. Good Job!", 
                                                      Const.MEDIUM_BUTTON_FONT, Color.WHITE, Const.HALF_WIDTH, 520);
        private final Text GAME_OVER_TEXT4 = new Text("You can now close the game", 
                                                      Const.MEDIUM_BUTTON_FONT, Color.WHITE, Const.HALF_WIDTH, 580);
        public GameOverScreenPanel(Image backgroundSprite) {
            super(backgroundSprite);      
            this.setFocusable(true);
            this.addComponentListener(this.FOCUS_WHEN_SHOWN);
        }
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.setColor(Const.INFO_RECT_COLOR);
            graphics.fillRoundRect(200, 250, 800, 450, Const.RADIUS, Const.RADIUS);
            GAME_OVER_TITLE.draw(graphics);
            GAME_OVER_TEXT1.draw(graphics);
            GAME_OVER_TEXT2.draw(graphics);
            GAME_OVER_TEXT3.draw(graphics);
            GAME_OVER_TEXT4.draw(graphics);
        }
        public final ComponentAdapter FOCUS_WHEN_SHOWN = new ComponentAdapter(){
            public void componentShown(ComponentEvent event){
                requestFocusInWindow();
            }
        };
    }

    /**
     * This class represents the pause screen in the RunNGun Game. The game can be paused while the game is running.
     */
    public class PauseScreenPanel extends ScreenPanel {
        private final Text PAUSE_TITLE = new Text("Game Paused", Const.MENU_BUTTON_FONT, Color.WHITE, Const.HALF_WIDTH, 270);
        private final Text PAUSE_TEXT = new Text("Press Escape to unpause", Const.MEDIUM_BUTTON_FONT, Color.WHITE, Const.HALF_WIDTH, 400);
        public PauseScreenPanel(Image backgroundSprite) {
            super(backgroundSprite);
            this.setFocusable(true);
            this.addComponentListener(this.FOCUS_WHEN_SHOWN);
            this.addKeyListener(new PauseKeyListener());
        }
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.setColor(Const.INFO_RECT_COLOR);
            graphics.fillRoundRect(200, 250, 800, 250, Const.RADIUS, Const.RADIUS);
            PAUSE_TITLE.draw(graphics);
            PAUSE_TEXT.draw(graphics);
        }
        public final ComponentAdapter FOCUS_WHEN_SHOWN = new ComponentAdapter(){
            public void componentShown(ComponentEvent event){
                requestFocusInWindow();
            }
        };
        public class PauseKeyListener implements KeyListener {
            public void keyPressed(KeyEvent event) {
                int key = event.getKeyCode();
                if (key == KeyEvent.VK_ESCAPE){
                    RunNGun.ScreenSwapper swapper = new RunNGun.ScreenSwapper(cards, RunNGun.GAME_PANEL);
                    swapper.swap();
                    game.start();
                }
            }
            public void keyReleased(KeyEvent event) { }
            public void keyTyped(KeyEvent event) { }
        }
    }
}