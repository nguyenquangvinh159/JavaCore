package model.DAO;

import java.sql.*;
import java.util.*;
import model.PhongHoc;
import util.DBConnect;

public class PhongHocDAO {
    public List<PhongHoc> getAllPhongHoc() {
        List<PhongHoc> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection()) {
            String sql = "SELECT * FROM PhongHoc";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new PhongHoc(
                    rs.getString("MaPhong"),
                    rs.getString("TenPhong"),
                    rs.getString("ToaNha"),
                    rs.getInt("Tang"),
                    rs.getInt("SucChua"),
                    rs.getString("LoaiPhong"),
                    rs.getString("TrangThai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean capNhatTrangThai(String maPhong, String trangThai) {
        try (Connection con = DBConnect.getConnection()) {
            String sql = "UPDATE PhongHoc SET TrangThai=? WHERE MaPhong=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, trangThai);
            ps.setString(2, maPhong);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}