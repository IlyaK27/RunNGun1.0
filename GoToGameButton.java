/**
 * Final Game GoToGameButton Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents a button that will start the game The only difference between this button is it preforms 1 extra action when clicked
 */


import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GoToGameButton extends TextButton {
    private Game game;
    public GoToGameButton(Game game, JFrame window, JPanel cards, String panel, String stringText, Font textFont, Color fontColor, 
                  Color inColor, Color outColor, Color hoverColor, int centerX, int y, int radius) {
        super(window, cards, panel, stringText, textFont, fontColor, inColor, outColor, hoverColor, centerX, y, radius);
        this.game = game;
    }
    
    public class ContinueMouseListener implements MouseListener{
        public void mouseClicked(MouseEvent event) {
            if (mouseInside) {
                if (!(cards == null && panel == null)){
                    game.setDifficultySettings(Const.SETTINGS[RunNGun.gameDifficulty]);
                    game.start();
                    RunNGun.ScreenSwapper swapper = new RunNGun.ScreenSwapper(cards, panel);
                    swapper.swap();
                }
                else if (cards == null && panel == null){
                    stayHeld = !stayHeld;
                }
            }
        }
        public void mousePressed(MouseEvent e) { 
        }
        public void mouseReleased(MouseEvent e) {  
        }
        public void mouseEntered(MouseEvent e) {
        }
        public void mouseExited(MouseEvent e) {
        }
    }
}