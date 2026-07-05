/***********************************************************************
 * Filename : BenefitRepository.java
 * Deskripsi :
 *      Kelas ini tugasnya sebagai Data Access Object
 *      untuk tabel tbenefit pada database.
 *      semua operasi CRUD yang berhubungan sama
 *      data benefit player dilakukan lewat class ini
 ***********************************************************************/

package models.database;

//import kelas
import models.PlayerBenefit;

//import statement
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BenefitRepository {

    /*
     * ngambil seluruh data dari tabel tbenefit.
     * digunakan untuk nampilin leaderboard / tabel
     * pada Main Menu View.
     */
    public List<PlayerBenefit> getAllBenefits() {
        List<PlayerBenefit> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM tbenefit";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Membaca setiap baris hasil query
            while (rs.next()) {
                PlayerBenefit pb = new PlayerBenefit(
                        rs.getString("username"),
                        rs.getInt("skor"),
                        rs.getInt("peluru_meleset"),
                        rs.getInt("sisa_peluru")
                );
                list.add(pb);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /*
     * Mengambil data benefit berdasarkan username.
     * Dipakai saat game dimulai untuk:
     * - melanjutkan sisa peluru
     * - membaca skor & peluru meleset sebelumnya
     */
    public PlayerBenefit getByUsername(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM tbenefit WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            // Jika username ditemukan
            if (rs.next()) {
                return new PlayerBenefit(
                        rs.getString("username"),
                        rs.getInt("skor"),
                        rs.getInt("peluru_meleset"),
                        rs.getInt("sisa_peluru")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Jika username tidak ditemukan
        return null;
    }

    /*
     * Menyimpan data benefit dengan konsep SAVE OR UPDATE.
     *
     * - Jika username sudah ada → data di-UPDATE
     * - Jika username belum ada → data di-INSERT
     *
     * Method ini dipanggil saat GAME OVER.
     */
    public void saveOrUpdate(PlayerBenefit b) {
        try (Connection conn = DBConnection.getConnection()) {

            // Cek apakah username sudah ada
            String check = "SELECT username FROM tbenefit WHERE username = ?";
            PreparedStatement psCheck = conn.prepareStatement(check);
            psCheck.setString(1, b.getUsername());
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                // ===== UPDATE DATA LAMA =====
                String update = """
                UPDATE tbenefit
                SET skor = ?, peluru_meleset = ?, sisa_peluru = ?
                WHERE username = ?
            """;
                PreparedStatement ps = conn.prepareStatement(update);
                ps.setInt(1, b.getSkor());
                ps.setInt(2, b.getPeluruMeleset());
                ps.setInt(3, b.getSisaPeluru());
                ps.setString(4, b.getUsername());
                ps.executeUpdate();

            } else {
                // ===== INSERT DATA BARU =====
                String insert = """
                INSERT INTO tbenefit (username, skor, peluru_meleset, sisa_peluru)
                VALUES (?, ?, ?, ?)
            """;
                PreparedStatement ps = conn.prepareStatement(insert);
                ps.setString(1, b.getUsername());
                ps.setInt(2, b.getSkor());
                ps.setInt(3, b.getPeluruMeleset());
                ps.setInt(4, b.getSisaPeluru());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}