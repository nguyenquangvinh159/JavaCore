package model.DAO;

import java.sql.*;
import java.util.*;
import model.CSVCPhong;
import util.DBConnect;

public class CSVCPhongDAO {
    public List<CSVCPhong> getThietBiTheoPhong(String maPhong) {
        List<CSVCPhong> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection()) {
            String sql = "SELECT cs.MaPhong, cs.MaTB, tb.TenTB, cs.SoLuong, cs.NgayLapDat, tb.TinhTrang " +
                         "FROM CSVC_Phong cs JOIN ThietBi tb ON cs.MaTB = tb.MaTB WHERE cs.MaPhong = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maPhong);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CSVCPhong(
                    rs.getString("MaPhong"),
                    rs.getString("MaTB"),
                    rs.getString("TenTB"),
                    rs.getInt("SoLuong"),
                    rs.getDate("NgayLapDat"),
                    rs.getString("TinhTrang")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean themHoacCapNhat(String maPhong, String maTB, int soLuong) {
        try (Connection con = DBConnect.getConnection()) {
            String check = "SELECT COUNT(*) FROM CSVC_Phong WHERE MaPhong=? AND MaTB=?";
            PreparedStatement ps1 = con.prepareStatement(check);
            ps1.setString(1, maPhong);
            ps1.setString(2, maTB);
            ResultSet rs = ps1.executeQuery();
            rs.next();
            boolean exists = rs.getInt(1) > 0;

            if (exists) {
                String update = "UPDATE CSVC_Phong SET SoLuong = SoLuong + ? WHERE MaPhong=? AND MaTB=?";
                PreparedStatement ps2 = con.prepareStatement(update);
                ps2.setInt(1, soLuong);
                ps2.setString(2, maPhong);
                ps2.setString(3, maTB);
                return ps2.executeUpdate() > 0;
            } else {
                String insert = "INSERT INTO CSVC_Phong(MaPhong, MaTB, SoLuong) VALUES (?, ?, ?)";
                PreparedStatement ps3 = con.prepareStatement(insert);
                ps3.setString(1, maPhong);
                ps3.setString(2, maTB);
                ps3.setInt(3, soLuong);
                return ps3.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}