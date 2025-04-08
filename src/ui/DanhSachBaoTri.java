package ui;

import util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class DanhSachBaoTri extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public DanhSachBaoTri() {
        setTitle("Danh Sách Bảo Trì - Sửa Chữa");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(new String[]{
                "Mã BT", "Mã CSVC", "Ngày bảo trì", "Nội dung", "Trạng thái"
        }, 0);

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(e -> xoaBaoTri());

        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.addActionListener(e -> loadData());

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnXoa);
        btnPanel.add(btnLamMoi);

        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        loadData();
    }

    private void loadData() {
        model.setRowCount(0); // Clear bảng
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM BaoTri_SuaChua";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("MaBT"),
                        rs.getString("MaCSVC"),
                        rs.getDate("NgayBaoTri"),
                        rs.getString("NoiDung"),
                        rs.getString("TrangThai")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu");
        }
    }

    private void xoaBaoTri() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để xóa");
            return;
        }

        String maBT = table.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa phiếu bảo trì: " + maBT + "?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "DELETE FROM BaoTri_SuaChua WHERE MaBT = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, maBT);
                int affected = ps.executeUpdate();
                if (affected > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa dữ liệu");
            }
        }
    }
}
