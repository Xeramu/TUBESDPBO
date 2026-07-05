//Saya Umarex Shauma Andromeda mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
// Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
// tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

/***********************************************************************
 * Filename : Main.java
 * Deskripsi :
 *      Class utama (entry point) dari aplikasi Hide and Seek.
 *      Program akan mulai dieksekusi dari method main().
 *      Tugas utama file ini:
 *      - Menjalankan aplikasi
 *      - Menampilkan tampilan awal (Main Menu)
 *      Pola arsitektur:
 *      - MVP (Model–View–Presenter)
 *      - Main.java hanya bertugas memanggil View pertama
 ***********************************************************************/

import views.MainMenuView;

public class Main {

    /*
     * Method main adalah titik awal eksekusi program Java.
     * Saat program dijalankan, method ini akan dipanggil pertama kali.
     */
    public static void main(String[] args) {

        // Membuat dan menampilkan Main Menu
        // Dari sini alur program dilanjutkan ke MainMenuView
        // (input username, lihat leaderboard, lalu masuk ke game)
        new MainMenuView();
    }
}

/*
* ================================
*  INGET BISI LUPA CARA RUN DR CMD
* ================================
*/

//compile dlu
//dir /s /b src\*.java > sources.txt
//type sources.txt
//javac -cp "lib\mysql-connector-j-xx.xx.xx.jar" -d out @sources.txt

//cara run
//java -cp "out;lib/mysql-connector-j-xx.xx.xx.jar" Main

//versi mysql connectornya sesuaiin sama lib yg udh ada dibagian xx.xx.xxnya