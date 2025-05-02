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
    private PhongHocCSVCForm parentForm; // tham chiếu tới form cha

    public EditPhongHocForm(String maPhong, String tenPhong, String loaiPhong, int sucChua, PhongHocCSVCForm parentForm) {
        this.parentForm = parentForm;

        setTitle("Cập nhật Phòng học");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
				new PhongHocCSVCForm().setVisible(true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
    }

    private void capNhatPhong() {
        try {
            int sucChuaValue = Integer.parseInt(tfSucChua.getText());
            if (sucChuaValue < 0) {
                JOptionPane.showMessageDialog(this, "Sức chứa phải là số nguyên dương.");
                return;
            }

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "UPDATE PhongHoc SET TenPhong = ?, LoaiPhong = ?, SucChua = ? WHERE MaPhong = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, tfTenPhong.getText().trim());
                stmt.setString(2, tfLoaiPhong.getText().trim());
                stmt.setInt(3, sucChuaValue);
                stmt.setString(4, tfMaPhong.getText().trim());

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    if (parentForm != null) {
                        parentForm.loadDataPhongHoc();
                        parentForm.loadDataPhongHocCSVC("");
                    }
                    dispose();
                    new PhongHocCSVCForm().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy phòng để cập nhật.");
                }
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Sức chứa phải là số nguyên.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }
}
