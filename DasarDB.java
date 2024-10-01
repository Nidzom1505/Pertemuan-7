/*
 * Click nbfs://nbhostmt/SystmtemFileSystmtem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhostmt/SystmtemFileSystmtem/Templates/Classes/Class.java to edit this template
 */
package SimulasiUTS;

/**
 *
 * @author Nidzzz
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DasarDB {

    Connection conn;
    Statement stmt;
    PreparedStatement pstmt = null;
    ResultSet rs;

    public DasarDB() {
        String driver = "org.postgresql.Driver";
        String koneksi = "jdbc:postgresql://localhost:5432/pbo";
        String user = "postgres";
        String password = "nidzom15";
        try {
            Class.forName(driver);
            // Membuat koneksi ke database
            conn = DriverManager.getConnection(koneksi, user, password);
            conn.setAutoCommit(false); // Mengatur autocommit ke false agar bisa menggunakan commit manual
            System.out.println("Koneksi berhasil!");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DasarDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertDB(String isbn, String judul, String tahun, String penerbit) {
        String sql = "Insert into buku values(?,?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, isbn);
            pstmt.setString(2, judul);
            pstmt.setLong(3, Long.parseLong(tahun));
            pstmt.setString(4, penerbit);
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DasarDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet selectDB() {
        try {
            String sql = "select * from buku";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DasarDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public void updateDB(String isbn, String judul, String tahun, String penerbit) {
        try {
            String sql = "update buku set JudulBuku=?, TahunTerbit=?, penerbit=? where ISBN=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, judul);
            pstmt.setLong(2, Long.parseLong(tahun));
            pstmt.setString(3, penerbit);
            pstmt.setString(4, isbn);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DasarDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteDB(String isbn) {
        try {
            String sql = "delete from buku where ISBN=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, isbn);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DasarDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
