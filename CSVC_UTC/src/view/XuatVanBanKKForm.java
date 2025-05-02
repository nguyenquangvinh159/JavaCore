package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import util.DBConnection;

public class XuatVanBanKKForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnXuatFile;

    public XuatVanBanKKForm(String maKK) {
        setTitle("Chi tiết xuất văn bản - Mã kiểm kê: " + maKK);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"MaVB", "MaKK", "TenPhong"});

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        btnXuatFile = new JButton("Xuất file");
        btnXuatFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(XuatVanBanKKForm.this, "Vui lòng chọn một văn bản để xuất.");
                    return;
                }
                String maVB = table.getValueAt(row, 0).toString();
                // Mô phỏng xuất file
                JOptionPane.showMessageDialog(XuatVanBanKKForm.this, "Xuất file cho MaVB: " + maVB);
            }
        });

        loadData(maKK);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnXuatFile);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadData(String maKK) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT xvbkk.MaVB, xvbkk.MaKK, ph.TenPhong " +
                         "FROM XuatVanBan_KiemKe xvbkk " +
                         "JOIN XuatVanBan xvb ON xvbkk.MaVB = xvb.MaVB " +
                         "JOIN PhongHoc ph ON xvb.MaPhong = ph.MaPhong " +
                         "WHERE xvbkk.MaKK = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maKK);
            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(rs.getString("MaVB"));
                row.add(rs.getString("MaKK"));
                row.add(rs.getString("TenPhong"));
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }
}
