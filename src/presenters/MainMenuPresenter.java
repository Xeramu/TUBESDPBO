/***********************************************************************
 * Filename : MainMenuPresenter.java
 * Deskripsi :
 *      Presenter untuk Main Menu.
 *      Mengatur:
 *      - Validasi username
 *      - Navigasi ke GameView
 *      - Menampilkan data tbenefit di tabel
 ***********************************************************************/

package presenters;

//import kelas
import models.PlayerBenefit;
import models.database.BenefitRepository;
import views.MainMenuView;
import views.game.GameView;

//import util
import java.util.List;

public class MainMenuPresenter {

    private MainMenuView view; // tampilan menu
    private BenefitRepository repo; // akses database

    public MainMenuPresenter(MainMenuView v) {
        this.view = v;
        repo = new BenefitRepository();
    }

    // =====================================================
    // TOMBOL PLAY DITEKAN
    // =====================================================
    public void onPlayPressed(String username) {

        // validasi username
        if (username == null || username.trim().isEmpty()) {
            return;
        }

        // buka game dengan username
        new GameView(username);

        // tutup menu
        view.dispose();
    }

    // =====================================================
    // TOMBOL QUIT
    // =====================================================
    public void onQuitPressed() {
        System.exit(0);
    }

    // =====================================================
    // LOAD DATA DARI DATABASE KE TABEL
    // =====================================================
    public void loadBenefits() {
        List<PlayerBenefit> data = repo.getAllBenefits();
        view.showBenefits(data);
    }
}