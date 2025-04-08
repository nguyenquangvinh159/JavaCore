/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import controller.ThemKiemKeForm;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import util.DBConnection;
import view.XuatVanBanKKForm;

public class KiemKeCSVC  extends JFrame{
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnThem, btnTaiLai,  btnXoa;
    
    public KiemKeCSVC() {
        setTitle("Quản lý phiếu kiểm kê");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã KK", "Ngày kiểm kê", "Người thực hiện", "Ghi chú"});

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        btnThem = new JButton("Thêm phiếu kiểm kê");
        btnTaiLai = new JButton("Tải lại dữ liệu");
        btnXoa = new JButton("Xóa phiếu kiểm kê");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnTaiLai);
        buttonPanel.add(btnXoa);
        add(buttonPanel, BorderLayout.SOUTH);

        btnThem.addActionListener(e -> {
            new ThemKiemKeForm().setVisible(true);
        });
        btnXoa.addActionListener(e -> xoaPhieuKiemKe());
        btnTaiLai.addActionListener(e -> loadData());
        JButton btnXuatFile = new JButton("Xuất file");
        
        btnXuatFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn phiếu kiểm kê để xuất file.");
                    return;
                }
                String maKK = table.getValueAt(selectedRow, 0).toString();
                new XuatVanBanKKForm(maKK).setVisible(true);
            }
        });
        
        loadData();
    }
    private void loadData() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT MaKK, NgayKK, NguoiThucHien, GhiChu FROM KiemKeCSVC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            tableModel.setRowCount(0); // clear existing data
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("MaKK"));
                row.add(rs.getDate("NgayKK"));
                row.add(rs.getString("NguoiThucHien"));
                row.add(rs.getString("GhiChu"));
                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }
    private void xoaPhieuKiemKe() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu kiểm kê để xóa.");
            return;
        }

        String maKK = tableModel.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa phiếu kiểm kê này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM KiemKeCSVC WHERE MaKK = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maKK);
            int result = stmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu để xóa.");
            }

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + e.getMessage());
        }
    }
}
