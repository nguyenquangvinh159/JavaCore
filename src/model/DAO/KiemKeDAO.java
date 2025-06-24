package model.DAO;

import model.KiemKe;
import util.DBConnect;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KiemKeDAO {

    public boolean themKiemKe(KiemKe kk) {
        try (Connection con = DBConnect.getConnection()) {
            String sql = "INSERT INTO KiemKe (TenDangNhap, MaPhong, NgayKiemKe, LoaiKiemKe, TrangThai, GhiChu) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, kk.getTenDangNhap());
            ps.setString(2, kk.getMaPhong());
            ps.setDate(3, Date.valueOf(kk.getNgayKiemKe()));
            ps.setString(4, kk.getLoaiKiemKe());
            ps.setString(5, kk.getTrangThai());
            ps.setString(6, kk.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<KiemKe> getAllKiemKe() {
        List<KiemKe> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection()) {
            String sql = "SELECT * FROM KiemKe ORDER BY MaKiemKe DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new KiemKe(
                        rs.getInt("MaKiemKe"),
                        rs.getString("TenDangNhap"),
                        rs.getString("MaPhong"),
                        rs.getDate("NgayKiemKe").toLocalDate(),
                        rs.getString("LoaiKiemKe"),
                        rs.getString("TrangThai"),
                        rs.getString("GhiChu")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean capNhatTrangThai(int maKiemKe, String trangThaiMoi) {
        try (Connection con = DBConnect.getConnection()) {
            String sql = "UPDATE KiemKe SET TrangThai=? WHERE MaKiemKe=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, trangThaiMoi);
            ps.setInt(2, maKiemKe);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public int themKiemKeVaLayMa(KiemKe kk) {
        try (Connection con = DBConnect.getConnection()) {
            String sql = "INSERT INTO KiemKe (TenDangNhap, MaPhong, NgayKiemKe, LoaiKiemKe, TrangThai, GhiChu) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, kk.getTenDangNhap());
            ps.setString(2, kk.getMaPhong());
            ps.setDate(3, Date.valueOf(kk.getNgayKiemKe()));
            ps.setString(4, kk.getLoaiKiemKe());
            ps.setString(5, kk.getTrangThai());
            ps.setString(6, kk.getGhiChu());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Trả về mã kiểm kê
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
