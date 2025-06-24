package model.DAO;

import model.ChiTietKiemKe;
import util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietKiemKeDAO {
	    public void themChiTietTuPhong(int maKiemKe, String maPhong) {
	        try (Connection con = DBConnect.getConnection()) {
	            String sql = "INSERT INTO ChiTietKiemKe (MaKiemKe, MaTB, SoLuongSoSach, SoLuongThucTe, TinhTrang, GhiChu) " +
	                         "SELECT ?, c.MaTB, c.SoLuong, c.SoLuong, N'Bình thường', N'' " +
	                         "FROM CSVC_Phong c WHERE c.MaPhong=?";
	            PreparedStatement ps = con.prepareStatement(sql);
	            ps.setInt(1, maKiemKe);
	            ps.setString(2, maPhong);
	            ps.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

    // Lấy danh sách chi tiết kiểm kê theo mã kiểm kê
    public List<ChiTietKiemKe> getChiTiet(int maKiemKe) {
        List<ChiTietKiemKe> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection()) {
            String sql = "SELECT c.MaTB, t.TenTB, c.SoLuongSoSach, c.SoLuongThucTe, c.TinhTrang, c.GhiChu " +
                         "FROM ChiTietKiemKe c JOIN ThietBi t ON c.MaTB = t.MaTB " +
                         "WHERE c.MaKiemKe = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maKiemKe);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietKiemKe ct = new ChiTietKiemKe();
                ct.setMaTB(rs.getString("MaTB"));
                ct.setTenTB(rs.getString("TenTB"));
                ct.setSoSach(rs.getInt("SoLuongSoSach"));
                ct.setThucTe(rs.getInt("SoLuongThucTe"));
                ct.setTinhTrang(rs.getString("TinhTrang"));
                ct.setGhiChu(rs.getString("GhiChu"));
                list.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Cập nhật chi tiết kiểm kê
    public boolean capNhat(int maKiemKe, String maTB, int thucTe, String tinhTrang, String ghiChu) {
        try (Connection con = DBConnect.getConnection()) {
            String sql = "UPDATE ChiTietKiemKe SET SoLuongThucTe=?, TinhTrang=?, GhiChu=? " +
                         "WHERE MaKiemKe=? AND MaTB=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, thucTe);
            ps.setString(2, tinhTrang);
            ps.setString(3, ghiChu);
            ps.setInt(4, maKiemKe);
            ps.setString(5, maTB);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean capNhatChiTiet(int maKiemKe, ChiTietKiemKe ct) {
        String sql = "UPDATE ChiTietKiemKe SET SoLuongThucTe=?, TinhTrang=?, GhiChu=? " +
                     "WHERE MaKiemKe=? AND MaTB=?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ct.getThucTe());
            ps.setString(2, ct.getTinhTrang());
            ps.setString(3, ct.getGhiChu());
            ps.setInt(4, maKiemKe);
            ps.setString(5, ct.getMaTB());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
