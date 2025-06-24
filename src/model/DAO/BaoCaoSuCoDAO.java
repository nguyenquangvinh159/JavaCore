package model.DAO;

import model.BaoCaoSuCo;
import util.DBConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaoCaoSuCoDAO {
    public boolean guiBaoCao(BaoCaoSuCo bc) {
        try (Connection con = DBConnect.getConnection()) {
            String sql = "INSERT INTO BaoCaoSuCo (TenDangNhap, MaTB, MaPhong, MoTa, MucDoNghiemTrong) " +
                         "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, bc.getTenDangNhap());
            ps.setString(2, bc.getMaTB());
            ps.setString(3, bc.getMaPhong());
            ps.setString(4, bc.getMoTa());
            ps.setString(5, bc.getMucDoNghiemTrong());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<BaoCaoSuCo> layLichSuBaoCao(String tenDangNhap) {
        List<BaoCaoSuCo> list = new ArrayList<>();

        String sql = """
            SELECT bc.*, tb.TenTB
            FROM BaoCaoSuCo bc
            JOIN ThietBi tb ON bc.MaTB = tb.MaTB
            WHERE bc.TenDangNhap = ?
            ORDER BY bc.NgayBaoCao DESC
        """;

        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tenDangNhap);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BaoCaoSuCo bc = new BaoCaoSuCo();
                bc.setMaBC(rs.getInt("MaBC"));
                bc.setTenDangNhap(rs.getString("TenDangNhap"));
                bc.setMaTB(rs.getString("MaTB"));
                bc.setTenTB(rs.getString("TenTB"));
                bc.setMaPhong(rs.getString("MaPhong"));
                bc.setMoTa(rs.getString("MoTa"));
                bc.setMucDoNghiemTrong(rs.getString("MucDoNghiemTrong"));
                bc.setNgayBaoCao(rs.getDate("NgayBaoCao").toLocalDate());
                bc.setTrangThai(rs.getString("TrangThai"));
                bc.setNguoiXuLy(rs.getString("NguoiXuLy"));
                if (rs.getDate("NgayXuLy") != null)
                    bc.setNgayXuLy(rs.getDate("NgayXuLy").toLocalDate());
                bc.setKetQuaXuLy(rs.getString("KetQuaXuLy"));

                list.add(bc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<BaoCaoSuCo> layBaoCaoChuaXuLy() {
        List<BaoCaoSuCo> list = new ArrayList<>();
        String sql = "SELECT * FROM BaoCaoSuCo WHERE TrangThai = N'Chưa xử lý'";
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BaoCaoSuCo bc = new BaoCaoSuCo(
                    rs.getString("TenDangNhap"),
                    rs.getString("MaTB"),
                    rs.getString("MaPhong"),
                    rs.getString("MoTa"),
                    rs.getString("MucDoNghiemTrong")
                );
                bc.setMaBC(rs.getInt("MaBC"));
                bc.setNgayBaoCao(rs.getDate("NgayBaoCao").toLocalDate());
                bc.setTrangThai(rs.getString("TrangThai"));
                list.add(bc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean xuLyBaoCao(int maBC, String nguoiXuLy, String ketQua) {
        String sql = "UPDATE BaoCaoSuCo SET TrangThai=N'Đã xử lý', NguoiXuLy=?, NgayXuLy=GETDATE(), KetQuaXuLy=? WHERE MaBC=?";
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nguoiXuLy);
            ps.setString(2, ketQua);
            ps.setInt(3, maBC);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
