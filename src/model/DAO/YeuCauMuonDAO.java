package model.DAO;

import model.YeuCauMuon;
import util.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class YeuCauMuonDAO {
    public boolean themYeuCau(YeuCauMuon yc) {
        String sql = "INSERT INTO YeuCauMuon (TenDangNhap, MaTB, SoLuongMuon, NgayMuon, NgayTraDuKien, MucDichSuDung, TrangThai) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, yc.getTenDangNhap());
            ps.setString(2, yc.getMaTB());
            ps.setInt(3, yc.getSoLuongMuon());
            ps.setDate(4, Date.valueOf(yc.getNgayMuon()));
            ps.setDate(5, Date.valueOf(yc.getNgayTraDuKien()));
            ps.setString(6, yc.getMucDichSuDung());
            ps.setString(7, yc.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<YeuCauMuon> layLichSuMuonTheoNguoiDung(String tenDangNhap) {
        List<YeuCauMuon> list = new ArrayList<>();
        String sql = "SELECT yc.*, tb.TenTB FROM YeuCauMuon yc " +
                     "JOIN ThietBi tb ON yc.MaTB = tb.MaTB " +
                     "WHERE yc.TenDangNhap = ? ORDER BY yc.NgayMuon DESC";

        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenDangNhap);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                YeuCauMuon yc = new YeuCauMuon(
                    rs.getString("TenDangNhap"),
                    rs.getString("MaTB"),
                    rs.getInt("SoLuongMuon"),
                    rs.getDate("NgayMuon").toLocalDate(),
                    rs.getDate("NgayTraDuKien") != null ? rs.getDate("NgayTraDuKien").toLocalDate() : null,
                    rs.getString("MucDichSuDung")
                );

                yc.setMaYeuCau(rs.getInt("MaYeuCau"));
                yc.setTenTB(rs.getString("TenTB"));

                if (rs.getDate("NgayTraThucTe") != null)
                    yc.setNgayTraThucTe(rs.getDate("NgayTraThucTe").toLocalDate());

                yc.setTrangThai(rs.getString("TrangThai"));
                yc.setNguoiDuyet(rs.getString("NguoiDuyet"));
                yc.setLyDoTuChoi(rs.getString("LyDoTuChoi"));

                list.add(yc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<YeuCauMuon> getYeuCauChoDuyet() {
        List<YeuCauMuon> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection()) {
            String sql = "SELECT * FROM YeuCauMuon WHERE TrangThai = N'Chờ duyệt'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                YeuCauMuon yc = new YeuCauMuon(
                    rs.getString("TenDangNhap"),
                    rs.getString("MaTB"),
                    rs.getInt("SoLuongMuon"),
                    rs.getDate("NgayMuon").toLocalDate(),
                    rs.getDate("NgayTraDuKien").toLocalDate(),
                    rs.getString("MucDichSuDung")
                );
                yc.setMaYeuCau(rs.getInt("MaYeuCau"));
                yc.setTrangThai(rs.getString("TrangThai"));
                list.add(yc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean duyetYeuCau(int maYeuCau, String nguoiDuyet, boolean chapNhan, String lyDo) {
        try (Connection con = DBConnect.getConnection()) {
            String sql = "UPDATE YeuCauMuon SET TrangThai = ?, NguoiDuyet = ?, NgayDuyet = GETDATE(), LyDoTuChoi = ? WHERE MaYeuCau = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, chapNhan ? "Đã duyệt" : "Từ chối");
            ps.setString(2, nguoiDuyet);
            ps.setString(3, chapNhan ? null : lyDo);
            ps.setInt(4, maYeuCau);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
