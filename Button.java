/**
 * Final Game Button Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents a buttons frame since it is abtstract. 
 * Its custom appearance gets shared with all other buttons in the game.
 * This class provides a template for all other Buttons
 */

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class Button extends Rectangle {
    private JFrame window;
    
    protected Color inColor;
    protected Color outColor;
    protected Color hoverColor;

    private int centerX;
    private int x;
    private int y;
    private int width; 
    private int height; 
    private int radius;
    protected boolean mouseInside = false;
    protected boolean stayHeld = false;

    public Button(JFrame window, Color inColor, Color outColor, Color hoverColor, int centerX, int y, int radius) {
        this.window = window;
        this.inColor = inColor;
        this.outColor = outColor;
        this.hoverColor = hoverColor;
        this.centerX = centerX;
        this.y = y;
        this.radius = radius;
    }
//---------------------------------------------------------------------------------------------------------
// Getters and Setters
    public int getXVal(){
        return this.x;
    }
    public int getYVal(){
        return this.y;
    }
    public int getWidthVal(){
        return this.width;
    }
    public int getHeightVal(){
        return this.height;
    }
    public int getRadiusVal(){
        return this.radius;
    }
    protected void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
        this.x = (this.centerX - (this.width / 2) - (Const.BUTTON_HORIZONTAL_SPACE / 2));
    }
    public boolean getStayHeld(){
        return this.stayHeld;
    }
    public void resetStayHeld(){
        this.stayHeld = false;
    }
//---------------------------------------------------------------------------------------------------------
    public abstract void draw(Graphics graphics); 
    
    public class BasicMouseListener implements MouseListener{
        public void mouseClicked(MouseEvent event) {
        }
        public void mousePressed(MouseEvent e) {   // changes box color and places the box at the mouse location
        }
        public void mouseReleased(MouseEvent e) {  // restores box color
        }
        public void mouseEntered(MouseEvent e) {
        }
        public void mouseExited(MouseEvent e) {
        }
    }
    // Interface is used to change colour of button and to find out weather mouse coordinates are inside the button
    public class InsideButtonMotionListener implements MouseMotionListener {
        public void mouseMoved(MouseEvent event) {
            boolean mouseRegisteredInside = (event.getX() >= x) && (event.getX() <= (x + width)) && (event.getY() >= y) && (event.getY() <= (y + height));
            
            if (mouseRegisteredInside && !mouseInside) {
                // Redraw the button if mouse just moved onto button.
                mouseInside = true;
                window.repaint();
            } else if (!mouseRegisteredInside && mouseInside) {
                // Redraw the button if mouse just moved off button.
                mouseInside = false;
                window.repaint();
            }
        }
        public void mouseDragged(MouseEvent e) { }
    }
}