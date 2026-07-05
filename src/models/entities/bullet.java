/***********************************************************************
 * Filename : bullet.java
 * Deskripsi :
 *
 ***********************************************************************/

package models.entities;

//import statement
import java.awt.*;

public class bullet {

//    posisi peluru
    public int x, y;

//    ukuran peluru
    public int size = 8;

//    kecepatan peluru
    public int speed = 6;

//    penanda apakah peluru asalnya dari alien atau player
    public boolean fromAlien;

//    konstruktor peluru
    public bullet(int x, int y, boolean fromAlien) {
        this.x = x;
        this.y = y;
        this.fromAlien = fromAlien;
    }

//    pergerakan peluru
    public void move() {
        if (fromAlien) {
            y -= speed; // alien nembak KE ATAS
        } else {
            y += speed; // player nembak KE BAWAH
        }
    }

//    area tabrakan peluru
    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}
