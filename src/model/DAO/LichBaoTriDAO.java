package model.DAO;

import model.LichBaoTri;
import util.DBConnect;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class LichBaoTriDAO {

    public ArrayList<LichBaoTri> getAllLichBaoTri() {
        ArrayList<LichBaoTri> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection()) {
            String sql = "SELECT * FROM LichBaoTri";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                LichBaoTri lbt = new LichBaoTri();
                lbt.setMaBaoTri(rs.getInt("MaBaoTri"));
                lbt.setMaTB(rs.getString("MaTB"));
                lbt.setLoaiBaoTri(rs.getString("LoaiBaoTri"));
                lbt.setNgayDuKien(rs.getDate("NgayDuKien").toLocalDate());
                Date ngayTT = rs.getDate("NgayThucTe");
                if (ngayTT != null) lbt.setNgayThucTe(ngayTT.toLocalDate());
                lbt.setNoiDung(rs.getString("NoiDung"));
                lbt.setNguoiThucHien(rs.getString("NguoiThucHien"));
                lbt.setChiPhi(rs.getDouble("ChiPhi"));
                lbt.setTrangThai(rs.getString("TrangThai"));
                list.add(lbt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean themLichBaoTri(LichBaoTri lbt) {
        try (Connection con = DBConnect.getConnection()) {
            String sql = "INSERT INTO LichBaoTri (MaTB, LoaiBaoTri, NgayDuKien, NoiDung, NguoiThucHien, ChiPhi, TrangThai) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, lbt.getMaTB());
            ps.setString(2, lbt.getLoaiBaoTri());
            ps.setDate(3, Date.valueOf(lbt.getNgayDuKien()));
            ps.setString(4, lbt.getNoiDung());
            ps.setString(5, lbt.getNguoiThucHien());
            ps.setDouble(6, lbt.getChiPhi());
            ps.setString(7, lbt.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
