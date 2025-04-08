package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import ui.PhongHocCSVCForm;
import util.DBConnection;

public class EditPhongHocForm extends JFrame {
    private JTextField tfMaPhong, tfTenPhong, tfLoaiPhong, tfSucChua;
    private JButton btnCapNhat, btnHuy;

    public EditPhongHocForm(String maPhong, String tenPhong, String loaiPhong, int sucChua) {
        setTitle("Cập nhật Phòng học");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("Mã phòng:"));
        tfMaPhong = new JTextField(maPhong);
        tfMaPhong.setEditable(false);
        panel.add(tfMaPhong);

        panel.add(new JLabel("Tên phòng:"));
        tfTenPhong = new JTextField(tenPhong);
        panel.add(tfTenPhong);

        panel.add(new JLabel("Loại phòng:"));
        tfLoaiPhong = new JTextField(loaiPhong);
        panel.add(tfLoaiPhong);

        panel.add(new JLabel("Sức chứa:"));
        tfSucChua = new JTextField(String.valueOf(sucChua));
        panel.add(tfSucChua);

        btnCapNhat = new JButton("Cập nhật");
        btnHuy = new JButton("Hủy");
        panel.add(btnCapNhat);
        panel.add(btnHuy);

        add(panel);
        setVisible(true);

        btnCapNhat.addActionListener(e -> capNhatPhong());
        btnHuy.addActionListener(e -> {
            dispose();
            try {
                new PhongHocCSVCForm();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Không thể mở lại PhongHocCSVCForm: " + ex.getMessage());
            }
        });
    }

    private void capNhatPhong() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE PhongHoc SET TenPhong = ?, LoaiPhong = ?, SucChua = ? WHERE MaPhong = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tfTenPhong.getText());
            stmt.setString(2, tfLoaiPhong.getText());
            stmt.setInt(3, Integer.parseInt(tfSucChua.getText()));
            stmt.setString(4, tfMaPhong.getText());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }
}