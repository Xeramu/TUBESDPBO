/***********************************************************************
 * Filename : GamePanel.java
 * Deskripsi :
 *      Kelas GamePanel merupakan komponen utama permainan yang bertugas
 *      menampilkan gameplay (rendering), menerima input keyboard,
 *      serta menghubungkan tampilan dengan logika game (GamePresenter).
 ***********************************************************************/

package views.game;

//mport kelas
import models.entities.rock;
import models.entities.bullet;
import models.entities.alien;
import presenters.GamePresenter;
import views.MainMenuView;

//import statement
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel {

    // ======================
    // ATRIBUT GAME
    // ======================
    private List<rock> rock = new ArrayList<>();
    private final int WIDTH = 900;
    private final int HEIGHT = 600;

    private GamePresenter presenter;
    private Timer timer;

    // asset gambar
    private Image rockImage;
    private Image bgImage;
    private Image playerImage;
    private Image alienImage;

    private String username;

    // status game
    private boolean isPaused = false;

    public GamePanel(String username) {
        this.username = username;

        // konfigurasi panel
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        // load asset gambar
        rockImage   = new ImageIcon("src/assets/images/rock.png").getImage();
        bgImage     = new ImageIcon("src/assets/images/bg.jpg").getImage();
        playerImage = new ImageIcon("src/assets/images/player.png").getImage();
        alienImage  = new ImageIcon("src/assets/images/alien.png").getImage();

        // generate rintangan batu
        generateRocks(8);

        // inisialisasi presenter (logika game)
        presenter = new GamePresenter(WIDTH, HEIGHT, username);
        presenter.setRocks(rock);

        // setup kontrol keyboard
        setupKeyBindings();

        // game loop (60 FPS)
        timer = new Timer(16, e -> {
            presenter.update();
            repaint();
        });
        timer.start();
    }

    // ======================
    // KEY BINDINGS (INPUT)
    // ======================
    private void setupKeyBindings() {

        InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        // ===== GERAK KIRI (A) =====
        im.put(KeyStroke.getKeyStroke("pressed A"), "leftPressed");
        im.put(KeyStroke.getKeyStroke("released A"), "leftReleased");

        am.put("leftPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                presenter.player.left = true;
            }
        });

        am.put("leftReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                presenter.player.left = false;
            }
        });

        // ===== GERAK KANAN (D) =====
        im.put(KeyStroke.getKeyStroke("pressed D"), "rightPressed");
        im.put(KeyStroke.getKeyStroke("released D"), "rightReleased");

        am.put("rightPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                presenter.player.right = true;
            }
        });

        am.put("rightReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                presenter.player.right = false;
            }
        });

        // ===== GERAK ATAS (W) =====
        im.put(KeyStroke.getKeyStroke("pressed W"), "upPressed");
        im.put(KeyStroke.getKeyStroke("released W"), "upReleased");

        am.put("upPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                presenter.player.up = true;
            }
        });

        am.put("upReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                presenter.player.up = false;
            }
        });

        // ===== GERAK BAWAH (S) =====
        im.put(KeyStroke.getKeyStroke("pressed S"), "downPressed");
        im.put(KeyStroke.getKeyStroke("released S"), "downReleased");

        am.put("downPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                presenter.player.down = true;
            }
        });

        am.put("downReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                presenter.player.down = false;
            }
        });

        // ===== PAUSE / RESUME (SPACE) =====
        im.put(KeyStroke.getKeyStroke("pressed SPACE"), "pauseToggle");

        am.put("pauseToggle", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) {
                    timer.stop();
                    isPaused = true;
                } else {
                    timer.start();
                    isPaused = false;
                }
                repaint();
            }
        });

        // ===== KEMBALI KE MENU (ENTER) =====
        im.put(KeyStroke.getKeyStroke("pressed ENTER"), "backMenu");

        am.put("backMenu", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (presenter.isGameOver) {
                    new MainMenuView();
                    SwingUtilities.getWindowAncestor(GamePanel.this).dispose();
                }
            }
        });
    }

    // ======================
    // RENDERING
    // ======================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // background
        drawBackgroundCover(g);

        // ===== HUD =====
        g.setColor(Color.WHITE);
        g.setFont(new Font("Press Start 2P", Font.BOLD, 16));
        g.drawString("Skor : " + presenter.getTotalSkor(), 20, 30);
        g.drawString("Peluru : " + presenter.player.peluru, 20, 55);
        g.drawString("Meleset : " + presenter.getTotalPeluruMeleset(), 20, 80);

        // batu
        for (rock o : rock) {
            g.drawImage(rockImage, o.x, o.y, o.width, o.height, null);
        }

        // player
        g.drawImage(
                playerImage,
                presenter.player.x,
                presenter.player.y,
                presenter.player.width,
                presenter.player.height,
                null
        );

        // alien
        for (alien a : presenter.aliens) {
            g.drawImage(alienImage, a.x, a.y, a.size, a.size, null);
        }

        // bullet
        g.setColor(Color.BLACK);
        for (bullet b : presenter.bullets) {
            g.fillOval(b.x, b.y, b.size, b.size);
        }

        // overlay pause
        if (isPaused) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(0, 0, WIDTH, HEIGHT);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Press Start 2P", Font.BOLD, 24));
            g2.drawString("PAUSED", WIDTH / 2 - 80, HEIGHT / 2);
        }

        // overlay game over
        if (presenter.isGameOver) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 0, 180));
            g2.fillRect(0, 0, WIDTH, HEIGHT);

            g2.setColor(Color.RED);
            g2.setFont(new Font("Press Start 2P", Font.BOLD, 28));
            g2.drawString("GAME OVER", WIDTH / 2 - 150, HEIGHT / 2 - 40);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Press Start 2P", Font.PLAIN, 14));
            g2.drawString("Skor : +" + presenter.sessionSkor, WIDTH / 2 - 90, HEIGHT / 2);
            g2.drawString("Press ENTER to Menu", WIDTH / 2 - 140, HEIGHT / 2 + 40);
        }
    }

    // ======================
    // UTIL
    // ======================
    private void generateRocks(int count) {
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            int baseW = rockImage.getWidth(null);
            int baseH = rockImage.getHeight(null);

            int scale = rand.nextInt(50) + 40;
            double ratio = (double) baseW / baseH;

            int w = (int) (scale * ratio);
            int h = scale;

            int x = rand.nextInt(WIDTH - w);
            int y = rand.nextInt(HEIGHT - h);

            rock.add(new rock(x, y, w, h));
        }
    }

    private void drawBackgroundCover(Graphics g) {
        int panelW = getWidth();
        int panelH = getHeight();

        int imgW = bgImage.getWidth(null);
        int imgH = bgImage.getHeight(null);

        double scale = Math.max(
                (double) panelW / imgW,
                (double) panelH / imgH
        );

        int newW = (int) (imgW * scale);
        int newH = (int) (imgH * scale);

        int x = (panelW - newW) / 2;
        int y = (panelH - newH) / 2;

        g.drawImage(bgImage, x, y, newW, newH, null);
    }
}