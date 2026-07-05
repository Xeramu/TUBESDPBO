/***********************************************************************
 * Filename : MainMenuView.java
 * Deskripsi :
 *      View utama (menu awal) aplikasi.
 *      Berfungsi untuk:
 *      - Input username pemain
 *      - Menampilkan leaderboard (data dari database)
 *      - Navigasi ke gameplay
 ***********************************************************************/

package views;

//import kelas
import presenters.MainMenuPresenter;
import models.PlayerBenefit;

// import library GUI
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainMenuView extends JFrame {

    // Komponen UI
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton playButton, quitButton;
    private JTextField usernameField;

    // Presenter (MVP)
    private MainMenuPresenter presenter;

    public MainMenuView() {

        // Inisialisasi presenter
        presenter = new MainMenuPresenter(this);

        // Konfigurasi window
        setTitle("Hide and Seek - Main Menu");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama (layout vertikal)
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // =============================
        // JUDUL GAME
        // =============================
        JLabel titleLabel = new JLabel("HIDE AND SEEK - THE CHALLENGE");
        titleLabel.setFont(new Font("Press Start 2P", Font.BOLD, 15));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titleLabel);

        // =============================
        // INPUT USERNAME
        // =============================
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Press Start 2P", Font.BOLD, 10));
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(usernameLabel);

        usernameField = new JTextField(15);
        usernameField.setMaximumSize(new Dimension(200, 25));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(usernameField);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // =============================
        // TABEL LEADERBOARD
        // =============================
        tableModel = new DefaultTableModel(new String[]{
                "Username", "Skor", "Peluru Meleset", "Sisa Peluru"
        }, 0);

        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(550, 250));
        panel.add(scroll);

        // =============================
        // TOMBOL PLAY & QUIT
        // =============================
        JPanel btnPanel = new JPanel();

        playButton = new JButton("Play");
        quitButton = new JButton("Quit");

        // Play → kirim username ke presenter
        playButton.addActionListener(e ->
                presenter.onPlayPressed(usernameField.getText())
        );

        // Quit -> keluar aplikasi
        quitButton.addActionListener(e -> presenter.onQuitPressed());

        btnPanel.add(playButton);
        btnPanel.add(quitButton);

        panel.add(btnPanel);
        add(panel);
        setVisible(true);

        // =============================
        // LOAD DATA DARI DATABASE
        // =============================
        presenter.loadBenefits();

        // Tombol Play nonaktif jika username kosong
        playButton.setEnabled(false);

        // Listener agar Play aktif hanya jika username diisi
        usernameField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { check(); }
            public void removeUpdate(DocumentEvent e) { check(); }
            public void changedUpdate(DocumentEvent e) { check(); }

            private void check() {
                playButton.setEnabled(!usernameField.getText().trim().isEmpty());
            }
        });
    }

    // Dipanggil presenter untuk menampilkan leaderboard
    public void showBenefits(List<PlayerBenefit> list) {
        for (PlayerBenefit p : list) {
            tableModel.addRow(new Object[]{
                    p.getUsername(),
                    p.getSkor(),
                    p.getPeluruMeleset(),
                    p.getSisaPeluru()
            });
        }
    }

    // Getter username (dipakai presenter)
    public String getUsername() {
        return usernameField.getText().trim();
    }
}
