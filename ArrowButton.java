/**
 * Final Game Arrow Button Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents a an arrow button
 * This button is generally used to change certian values 
 */

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JFrame;

public class ArrowButton extends Button {    
    int arrowDirection; // -1 = left, 1 = right    
    public ArrowButton(JFrame window, Color inColor, Color outColor, Color hoverColor, int centerX, int y, int radius,
                      int arrowDirection){
        super(window, inColor, outColor, hoverColor, centerX, y, radius);
        this.arrowDirection = arrowDirection;
        int width = Const.ARROW_BUTTON_WIDTH + (2 * Const.BUTTON_HORIZONTAL_SPACE);
        int height = Const.ARROW_BUTTON_HEIGHT + (2 * Const.BUTTON_VERTICAL_SPACE); 
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
        graphics.setColor(Const.INFO_RECT_COLOR);
        if (arrowDirection == -1){
            graphics.fillPolygon(Const.LEFT_ARROW_X, Const.LEFT_ARROW_Y, Const.ARROW_POINTS);
        } else if (arrowDirection == 1){
            graphics.fillPolygon(Const.RIGHT_ARROW_X, Const.RIGHT_ARROW_Y, Const.ARROW_POINTS);
        }
    }
}