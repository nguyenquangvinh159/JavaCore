package model.DAO;

import java.sql.*;
import java.util.*;

import model.ChucNang;
import util.DBConnect;

public class PhanQuyenDAO {
	public ArrayList<ChucNang> layChucNangTheoRole(String vaitro) {
	    ArrayList<ChucNang> list = new ArrayList<>();
	    try (Connection con = DBConnect.getConnection()) {
	        String sql = "SELECT MaChucNang, ChucNang FROM PhanQuyen WHERE VaiTro=? AND CoQuyen=1";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, vaitro);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            list.add(new ChucNang(rs.getString("MaChucNang"), rs.getString("ChucNang")));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
}
