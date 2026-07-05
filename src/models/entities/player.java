/***********************************************************************
 * Filename : player.java
 * Deskripsi :
 *
 ***********************************************************************/

package models.entities;

//import statement
import java.awt.Rectangle;
import java.util.List;

public class player {

//    posisi player
    public int x, y;

//    ukuran player
    public int width = 40;
    public int height = 40;

//    kecepatan gerak player
    public int speed = 5;

//    jumlah peluru yg dimiliki player
    public int peluru = 0;

//    status tombol gerak (dipake buat movement yg smooth)
    public boolean left, right, up, down;

//    konstruktor player
    public player(int w, int h) {

//        player muncul ditengah layar
        x = w / 2 - width / 2;
        y = h / 2 - height / 2;
    }

//    update posisi player setiap frame
    public void update(int maxW, int maxH, List<rock> rocks) {

//        simpan posisi lama (buat rollback klo nabrak batu)
        int oldX = x;
        int oldY = y;

//        gerakan berdasarkan input
        if (left)  x -= speed;
        if (right) x += speed;
        if (up)    y -= speed;
        if (down)  y += speed;

        // batas layar
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x + width > maxW) x = maxW - width;
        if (y + height > maxH) y = maxH - height;

        // ===== CEK NABRAK BATU =====
        Rectangle playerRect = getBounds();
        for (rock r : rocks) {
//            klo nabrak batu, balik ke posisi sebelumnya
            if (playerRect.intersects(r.getBounds())) {
                x = oldX;
                y = oldY;
                break;
            }
        }
    }

//    area tabrakan player
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}