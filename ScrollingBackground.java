/**
 * Final Game ScrollingBackground Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class represents the background of the actual game
 * this class will read an image file and from the image file create a background that lookes to be moving by changing x values
 */

import java.awt.Graphics;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ScrollingBackground{
    private int x1, x2;
    private int y1, y2;
    private int width;
    private BufferedImage bckgPic1;
    private BufferedImage bckgPic2;
//------------------------------------------------------------------------------    
    ScrollingBackground(String picName1, String picName2){
        this.x1 = 0;
        this.y1 = 100;        
        try {                
            this.bckgPic1 = ImageIO.read(new File(picName1));
            this.bckgPic2 = ImageIO.read(new File(picName2));
        } catch (IOException ex){}
        this.width = bckgPic1.getWidth();
        this.x2 = (this.width - 20);
        this.y2 = 100;
    }
//------------------------------------------------------------------------------  
// Methods
    public void draw(Graphics g){
        g.drawImage(this.bckgPic1, this.x1, this.y1, null);
        g.drawImage(this.bckgPic2, this.x2, this.y2, null);    
    }
    public void scroll(int step){
        this.x1 -= step;
        this.x2 -= step;
        if (this.x1 <= -this.width){
            this.x1 = (this.width - 20);
            this.x2 = 0;
        } else if (this.x2 <= -this.width){
            this.x2 = (this.width - 20);
            this.x1 = 0;
        }    
    }
}