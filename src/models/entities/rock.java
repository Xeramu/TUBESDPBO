/***********************************************************************
 * Filename : rock.java
 * Deskripsi :
 *
 ***********************************************************************/

package models.entities;

//import statement
import java.awt.*;

public class rock {
//    posisi batu
    public int x, y;
//    ukuran batu
    public int width, height;

//    konstruktor batu
    public rock(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

//    area tabrakan batu
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
