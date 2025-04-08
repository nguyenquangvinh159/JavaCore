/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import com.sun.jdi.connect.spi.Connection;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import util.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Acer
 */
public class XuatFileForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public XuatFileForm(String maVB) {
        setTitle("Xuất CSVC - Văn bản " + maVB);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(new String[]{"Mã CSVC", "Tên thiết bị"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnXuat = new JButton("Xuất file");
        btnXuat.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Xuất file thành công cho văn bản: " + maVB);
        });

        add(scrollPane, BorderLayout.CENTER);
        add(btnXuat, BorderLayout.SOUTH);

        loadData(maVB);
    }

    private void loadData(String maVB) {
        try (java.sql.Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT xc.MaCSVC, tb.TenCSVC " +
                         "FROM XuatCSVC xc JOIN CoSoVatChat tb ON xc.MaCSVC = tb.MaCSVC " +
                         "WHERE xc.MaVB = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maVB);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("MaCSVC"),
                    rs.getString("TenTB")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu.");
        }
    }
}
