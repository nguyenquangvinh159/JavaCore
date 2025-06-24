package model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.TaiKhoan;
import util.DBConnect;

public class TaiKhoanDAO {
    public TaiKhoan dangNhap(String tenDN, String matKhau) {
        try (Connection con = DBConnect.getConnection()) {
            String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap=? AND MatKhau=? AND TrangThai=1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tenDN);
            ps.setString(2, matKhau);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new TaiKhoan(
                    rs.getString("TenDangNhap"),
                    rs.getString("MatKhau"),
                    rs.getString("VaiTro"),
                    rs.getString("HoTen"),
                    rs.getString("Avatar")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean dangKy(String tenDN, String matKhau, String vaiTro, String hoTen, String avatar, String email, String sdt) {
        try (Connection con = DBConnect.getConnection()) {
            String sql = "INSERT INTO TaiKhoan (TenDangNhap, MatKhau, VaiTro, HoTen, Avatar, Email, SoDienThoai, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, 0)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tenDN);
            ps.setString(2, matKhau);
            ps.setString(3, vaiTro);
            ps.setString(4, hoTen);
            ps.setString(5, avatar);
            ps.setString(6, email);
            ps.setString(7, sdt);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean kiemTraTaiKhoanVaEmail(String tenDN, String email) {
        try (Connection con = DBConnect.getConnection()) {
            String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap=? AND Email=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tenDN);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true nếu tồn tại
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean datLaiMatKhau(String tenDN, String matKhauMoi) {
        try (Connection con = DBConnect.getConnection()) {
            String sql = "UPDATE TaiKhoan SET MatKhau=? WHERE TenDangNhap=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, matKhauMoi);
            ps.setString(2, tenDN);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<TaiKhoan> getAllTaiKhoan() {
        List<TaiKhoan> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection()) {
            String sql = "SELECT * FROM TaiKhoan";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan(
                    rs.getString("TenDangNhap"),
                    rs.getString("MatKhau"),
                    rs.getString("VaiTro"),
                    rs.getString("HoTen"),
                    rs.getString("Avatar")
                );
                tk.setEmail(rs.getString("Email"));
                tk.setSoDienThoai(rs.getString("SoDienThoai"));
                tk.setNgayTao(rs.getTimestamp("NgayTao"));
                tk.setTrangThai(rs.getBoolean("TrangThai") ? 1 : 0);
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean capNhatTaiKhoan(TaiKhoan tk) {
        try (Connection con = DBConnect.getConnection()) {
            String sql = "UPDATE TaiKhoan SET HoTen=?, Email=?, SoDienThoai=?, VaiTro=? WHERE TenDangNhap=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tk.getHoTen());
            ps.setString(2, tk.getEmail());
            ps.setString(3, tk.getSoDienThoai());
            ps.setString(4, tk.getVaiTro());
            ps.setString(5, tk.getTenDangNhap());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean toggleTrangThai(String tenDN) {
        try (Connection con = DBConnect.getConnection()) {
            String sql = "UPDATE TaiKhoan SET TrangThai = IIF(TrangThai=1, 0, 1) WHERE TenDangNhap=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tenDN);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
