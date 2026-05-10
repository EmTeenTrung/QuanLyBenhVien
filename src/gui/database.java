/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author chitrung
 */
public class database {

    public static Connection getConnection() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QLBV;encrypt=false;trustServerCertificate=true;";
            String user = "sa";
            String pass = "13032006";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.err.println("Lỗi kết nối: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        String sql = "SELECT * FROM tai_khoan";

        try (Connection conn = getConnection(); java.sql.Statement st = conn.createStatement(); java.sql.ResultSet rs = st.executeQuery(sql)) {

            if (conn != null) {
                System.out.println("Ket noi toi database thanh cong!");
                System.out.println("------------------------------------------");
                System.out.printf("%-5s | %-20s | %-20s%n", "ID", "ten_dang_nhap", "mat_khau");
                System.out.println("------------------------------------------");

                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    int id = rs.getInt("id");
                    String user = rs.getString("ten_dang_nhap");
                    String pass = rs.getString("mat_khau");

                    System.out.printf("%-5d | %-20s | %-20s%n", id, user, pass);
                }

                if (!hasData) {
                    System.out.println("chua co tai khoan nao ton tai");
                }
            }
        } catch (Exception e) {
            System.err.println("Loi lay du lieu " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static boolean registerAccount(String username, String password) {
    String sql = "INSERT INTO tai_khoan (ten_dang_nhap, mat_khau) VALUES (?, ?)";
    try (Connection conn = getConnection(); 
         java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, username);
        ps.setString(2, password);
        return ps.executeUpdate() > 0; 
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    public static boolean checkLogin(String username, String password) {
    String sql = "SELECT * FROM tai_khoan WHERE ten_dang_nhap = ? AND mat_khau = ?";
    
    try (Connection conn = getConnection(); 
         java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, username);
        ps.setString(2, password);
        
        java.sql.ResultSet rs = ps.executeQuery();
        return rs.next(); 
        
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}
