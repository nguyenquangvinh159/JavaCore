package ui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class XuatFileForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public XuatFileForm(String maVB) {
        setTitle("Xuất CSVC - Văn bản " + maVB);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(new String[]{
            "Mã CSVC", "Tên thiết bị", "Loại", "Số lượng", "Đơn vị tính", "Tình trạng"
        }, 0);

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnXuat = new JButton("Xuất file");
        btnXuat.addActionListener(e -> exportToFile());

        add(scrollPane, BorderLayout.CENTER);
        add(btnXuat, BorderLayout.SOUTH);

        loadData(maVB);
    }

    private void loadData(String maVB) {
        String sql = "SELECT xc.MaCSVC, tb.TenCSVC, tb.LoaiCSVC, tb.SoLuong, tb.DonViTinh, tb.TinhTrang " +
                     "FROM XuatVanBan_CSVC xc JOIN CoSoVatChat tb ON xc.MaCSVC = tb.MaCSVC " +
                     "WHERE xc.MaVB = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maVB);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getString("MaCSVC"),
                        rs.getString("TenCSVC"),
                        rs.getString("LoaiCSVC"),
                        rs.getInt("SoLuong"),
                        rs.getString("DonViTinh"),
                        rs.getString("TinhTrang")
                    });
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu.");
        }
    }

    private void exportToFile() {
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file CSV");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            try (java.io.FileWriter fw = new java.io.FileWriter(fileToSave)) {
                // Ghi tiêu đề cột
                for (int i = 0; i < model.getColumnCount(); i++) {
                    fw.write(model.getColumnName(i));
                    if (i < model.getColumnCount() - 1) fw.write(",");
                }
                fw.write("\n");

                // Ghi từng dòng dữ liệu
                for (int r = 0; r < model.getRowCount(); r++) {
                    for (int c = 0; c < model.getColumnCount(); c++) {
                        Object val = model.getValueAt(r, c);
                        fw.write(val == null ? "" : val.toString());
                        if (c < model.getColumnCount() - 1) fw.write(",");
                    }
                    fw.write("\n");
                }

                JOptionPane.showMessageDialog(this, "Xuất file thành công: " + fileToSave.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất file: " + e.getMessage());
            }
        }
    }
}
