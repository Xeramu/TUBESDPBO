/***********************************************************************
 * Filename : alien.java
 * Deskripsi :
 *
 ***********************************************************************/

package models.entities;

//import statement
import java.awt.*;

public class alien {

//    posisi alien discreen
    public int x, y;

//    ukuran alien (digambar persegi/ sprite)
    public int size;

//    kecepaten gerakan alien
    public int speed = 1;

//    gambar alien
    public Image image;

//    konstruktor alien
    public alien(int x, int y, int size, Image image) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.image = image;
    }

//    pergerakan alien
    public void move() {
        y -= speed; //geraknya keatas
    }

//    alien nembak peluru
//    peluru ditandain asalnya dari alien (fromAlien = true)
    public bullet shoot() {
        return new bullet(
                x + size / 2,
                y,
                true
        );
    }

//    area tabrakan alien buat collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}