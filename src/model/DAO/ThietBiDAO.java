package model.DAO;

import java.sql.*;
import java.util.*;
import model.ThietBi;
import util.DBConnect;

public class ThietBiDAO {
    public List<ThietBi> getAllThietBi() {
        List<ThietBi> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection()) {
            String sql = "SELECT * FROM ThietBi";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThietBi tb = new ThietBi();
                tb.setMaTB(rs.getString("MaTB"));
                tb.setTenTB(rs.getString("TenTB"));
                tb.setLoaiTB(rs.getString("LoaiTB"));
                tb.setSoLuong(rs.getInt("SoLuong"));
                tb.setTinhTrang(rs.getString("TinhTrang"));
                tb.setDonViTinh(rs.getString("DonViTinh"));
                tb.setHinhAnh(rs.getString("HinhAnh"));
                tb.setGiaTriTaiSan(rs.getDouble("GiaTriTaiSan"));
                tb.setNgayMua(rs.getDate("NgayMua"));
                tb.setHanBaoHanh(rs.getDate("HanBaoHanh"));
                tb.setNhaCungCap(rs.getString("NhaCungCap"));
                tb.setGhiChu(rs.getString("GhiChu"));
                list.add(tb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean delete(String maTB) {
        try (Connection con = DBConnect.getConnection()) {
            String sql = "DELETE FROM ThietBi WHERE MaTB=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maTB);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
