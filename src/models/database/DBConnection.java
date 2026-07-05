/***********************************************************************
 * Filename : DBConnection.java
 * Deskripsi :
 *      Kelas ini tugasnya ngatur koneksi ke databse MySQL
 ***********************************************************************/

//package model yang ngekses basis data
package models.database;

//import konektor
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//kelas DBConnection
public class DBConnection {
    //URL koneksi database ===> jdbc:mysql://localhost:3306/nama_basis_data
    private static final String URL = "jdbc:mysql://localhost:3306/hide_and_seek";

    //username databse
    private static final String USER = "root";

    //password database (default XAMPP kosong)
    private static final String PASSWORD = "";

    /* ngembaliin objek Connection ke database.
     * dipanggil setiap kali Repository butuh koneksi.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // 🔥 PAKSA LOAD DRIVER MYSQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver tidak ditemukan", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}