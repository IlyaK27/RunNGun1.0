/**
 * Final Game TextButton Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class is a button that has text written ontop of it 
 * This type of button is generally used to swtich screens
 */

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class TextButton extends Button {
    protected JPanel cards;
    protected String panel;

    private Text text;
    private Font font;
    private int fontSize;

    public TextButton(JFrame window, JPanel cards, String panel, String stringText, Font textFont, Color fontColor, 
                  Color inColor, Color outColor, Color hoverColor, int centerX, int y, int radius) {
        super(window, inColor, outColor, hoverColor, centerX, y, radius);
        this.cards = cards;
        this.panel = panel;
        this.text = new Text(stringText, textFont, fontColor, centerX, y);
        this.font = font;
        this.fontSize = fontSize;
        int width = text.getWidth() + (2 * Const.BUTTON_HORIZONTAL_SPACE);
        int height = text.getHeight() + (2 * Const.BUTTON_VERTICAL_SPACE); 
        this.setDimensions(width, height);
    }
    
    /**
     * This method draws the button onto a component/panel/window.
     * @param graphics The graphics of the component to be drawn onto.
     */
    public void draw(Graphics graphics) {
        // Draw the button body.
        if (!mouseInside) {
            graphics.setColor(this.inColor);
        } else {
            graphics.setColor(this.hoverColor);
        }
        if (stayHeld == true){
            graphics.setColor(this.hoverColor);
        }
        graphics.fillRoundRect(this.getXVal(), this.getYVal(), this.getWidthVal(), this.getHeightVal(), 
                               this.getRadiusVal(), this.getRadiusVal());
        
        // Draw the button border.
        graphics.setColor(this.outColor);
        graphics.drawRoundRect(this.getXVal(), this.getYVal(), this.getWidthVal(), this.getHeightVal(), 
                               this.getRadiusVal(), this.getRadiusVal());
        
        // Draw the button text.
        this.text.draw(graphics);
    }
    
    // Mouse clicking actions
    public class BasicMouseListener implements MouseListener{
        public void mouseClicked(MouseEvent event) {
            // If mouse clicks button switch to correct screen
            if (mouseInside) {
                if (!(cards == null && panel == null)){
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