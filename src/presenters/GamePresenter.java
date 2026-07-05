/***********************************************************************
 * Filename : GamePresenter.java
 * Deskripsi :
 *      Presenter utama untuk gameplay.
 *      Mengatur:
 *      - Logika pergerakan player, alien, dan bullet
 *      - Perhitungan skor & peluru meleset (per sesi)
 *      - Integrasi data lama dari database
 *      - Penyimpanan hasil permainan saat Game Over
 ***********************************************************************/

package presenters;

// import kelas
import models.entities.*;
import models.PlayerBenefit;
import models.database.BenefitRepository;

// import util & library
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.awt.Image;
import javax.swing.ImageIcon;

public class GamePresenter {

    // ======================
    // ENTITY GAME
    // ======================
    public player player;                  // objek player utama
    public List<alien> aliens = new ArrayList<>();   // list alien aktif
    public List<bullet> bullets = new ArrayList<>(); // list peluru aktif
    private List<rock> rocks;               // obstacle batu

    private Image alienImage;               // sprite alien

    // ======================
    // KONFIGURASI LAYAR
    // ======================
    private final int WIDTH;
    private final int HEIGHT;
    private Random rand = new Random();

    // ======================
    // COUNTER UNTUK LOGIKA
    // ======================
    private int alienShootCounter = 0; // timing tembakan alien
    private int autoShootCounter = 0; // timing tembakan player (otomatis)

    // ======================
    // DATA SESI (RESET SETIAP MAIN)
    // ======================
    public int sessionSkor = 0; // skor selama sesi ini
    public int sessionPeluruMeleset = 0; // peluru alien yang meleset (sesi ini)

    // ======================
    // DATA DARI DATABASE (AKUMULASI)
    // ======================
    private int dbSkor = 0; // skor lama dari DB
    private int dbPeluruMeleset = 0; // peluru meleset lama dari DB

    // ======================
    // STATUS GAME
    // ======================
    public boolean isGameOver = false; // flag game over
    private String username; // username player

    // =====================================================
    // CONSTRUCTOR
    // =====================================================
    public GamePresenter(int w, int h, String username) {
        WIDTH = w;
        HEIGHT = h;
        this.username = username;

        // ambil data lama dari database (jika ada)
        BenefitRepository repo = new BenefitRepository();
        PlayerBenefit old = repo.getByUsername(username);

        // inisialisasi player di tengah layar
        player = new player(w, h);

        // jika user sudah pernah main
        if (old != null) {
            dbSkor = old.getSkor();
            dbPeluruMeleset = old.getPeluruMeleset();
            player.peluru = old.getSisaPeluru(); // peluru dilanjutkan
        } else {
            player.peluru = 0; // user baru mulai tanpa peluru
        }

        // load sprite alien
        alienImage = new ImageIcon("src/assets/images/alien.png").getImage();
    }

    // =====================================================
    // SETTER OBSTACLE BATU
    // =====================================================
    public void setRocks(List<rock> rocks) {
        this.rocks = rocks;
    }

    // =====================================================
    // SPAWN ALIEN DI BAGIAN BAWAH LAYAR
    // =====================================================
    public void spawnAlien() {
        int x = rand.nextInt(WIDTH - 40);
        aliens.add(new alien(x, HEIGHT - 40, 40, alienImage));
    }

    // =====================================================
    // UPDATE GAME (DIPANGGIL TIAP FRAME)
    // =====================================================
    public void update() {
        // jika game sudah selesai, stop semua proses
        if (isGameOver) return;

        // update pergerakan player + collision batu
        player.update(WIDTH, HEIGHT, rocks);

        // probabilitas spawn alien
        if (rand.nextInt(100) < 2) {
            spawnAlien();
        }

        // gerak alien & tembak otomatis
        alienShootCounter++;
        for (alien a : aliens) {
            a.move();
            if (alienShootCounter % 80 == 0) {
                bullets.add(a.shoot());
            }
        }

        // tembakan otomatis player (jika punya peluru)
        autoShootCounter++;
        if (autoShootCounter % 30 == 0 && player.peluru > 0) {
            bullets.add(new bullet(
                    player.x + player.width / 2,
                    player.y,
                    false
            ));
            player.peluru--;
        }

        // update semua bullet
        Iterator<bullet> it = bullets.iterator();
        while (it.hasNext()) {
            bullet b = it.next();
            b.move();

            // ===== BULLET KENA BATU =====
            for (rock r : rocks) {
                if (b.getBounds().intersects(r.getBounds())) {
                    it.remove();
                    break;
                }
            }

            // ===== BULLET PLAYER KE ALIEN =====
            if (!b.fromAlien) {
                Iterator<alien> itAlien = aliens.iterator();
                while (itAlien.hasNext()) {
                    alien a = itAlien.next();
                    if (b.getBounds().intersects(a.getBounds())) {
                        itAlien.remove();
                        it.remove();
                        sessionSkor += 100; // skor sesi bertambah
                        break;
                    }
                }
            }

            // ===== BULLET ALIEN =====
            if (b.fromAlien) {

                // kena player -> game over
                if (b.getBounds().intersects(player.getBounds())) {
                    gameOver();
                    return;
                }

                // meleset (keluar layar)
                if (b.y < 0) {
                    sessionPeluruMeleset++;
                    player.peluru++; // peluru bertambah
                    it.remove();
                }
            }
        }
    }

    // =====================================================
    // GAME OVER + SIMPAN KE DATABASE
    // =====================================================
    private void gameOver() {
        isGameOver = true;

        BenefitRepository repo = new BenefitRepository();

        // data akhir = data lama + data sesi
        PlayerBenefit result = new PlayerBenefit(
                username,
                dbSkor + sessionSkor,
                dbPeluruMeleset + sessionPeluruMeleset,
                player.peluru
        );

        repo.saveOrUpdate(result);
    }

    // =====================================================
    // METHOD UNTUK HUD (VIEW)
    // =====================================================
    public int getTotalSkor() {
        return dbSkor + sessionSkor;
    }

    public int getTotalPeluruMeleset() {
        return dbPeluruMeleset + sessionPeluruMeleset;
    }
}
