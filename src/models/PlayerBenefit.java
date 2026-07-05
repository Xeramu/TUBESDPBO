/***********************************************************************
 * Filename : PlayerBenefit.java
 * Deskripsi :
 *      Model / Entity yang merepresentasikan satu baris
 *      data pada tabel tbenefit.
 *       Digunakan sebagai penghubung antara:
 *       - Database
 *       - Presenter
 *       - View
 ***********************************************************************/

package models;

public class PlayerBenefit {

    // Username player
    private String username;

    // Total skor yang telah dikumpulkan
    private int skor;

    // Total peluru alien yang meleset
    private int peluruMeleset;

    // Jumlah peluru yang tersisa di akhir permainan
    private int sisaPeluru;

    //constructor PlayerBenefit
    public PlayerBenefit(String username, int skor, int peluruMeleset, int sisaPeluru) {
        this.username = username;
        this.skor = skor;
        this.peluruMeleset = peluruMeleset;
        this.sisaPeluru = sisaPeluru;
    }

    //==== GETTER ====
    public String getUsername() { return username; }
    public int getSkor() { return skor; }
    public int getPeluruMeleset() { return peluruMeleset; }
    public int getSisaPeluru() { return sisaPeluru; }
}