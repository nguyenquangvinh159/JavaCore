package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import ui.PhongHocCSVCForm;
import util.DBConnection;

public class EditPhongCSVCForm extends JFrame {
    private JTextField tfMaPhong, tfMaTB, tfTenTB, tfLoai, tfDonVi;
    private JComboBox<String> cbTinhTrang;
    private JSpinner spSoLuong;
    private JButton btnCapNhat, btnHuy;

    public EditPhongCSVCForm(String maPhong, String maTB, String tenTB, String loai, String tinhTrang, int soLuong, String donVi) {
        setTitle("Cập nhật CSVC trong phòng học");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.add(new JLabel("Mã phòng:"));
        tfMaPhong = new JTextField(maPhong);
        tfMaPhong.setEditable(false);
        panel.add(tfMaPhong);

        panel.add(new JLabel("Mã thiết bị:"));
        tfMaTB = new JTextField(maTB);
        tfMaTB.setEditable(false);
        panel.add(tfMaTB);

        panel.add(new JLabel("Tên thiết bị:"));
        tfTenTB = new JTextField(tenTB);
        tfTenTB.setEditable(false);
        panel.add(tfTenTB);

        panel.add(new JLabel("Loại thiết bị:"));
        tfLoai = new JTextField(loai);
        tfLoai.setEditable(false);
        panel.add(tfLoai);

        panel.add(new JLabel("Đơn vị tính:"));
        tfDonVi = new JTextField(donVi);
        tfDonVi.setEditable(false);
        panel.add(tfDonVi);

        panel.add(new JLabel("Tình trạng:"));
        cbTinhTrang = new JComboBox<>(new String[]{"Tốt", "Đang sửa", "Xuống cấp"});
        cbTinhTrang.setSelectedItem(tinhTrang);
        panel.add(cbTinhTrang);

        panel.add(new JLabel("Số lượng:"));
        spSoLuong = new JSpinner(new SpinnerNumberModel(soLuong, 1, 1000, 1));
        panel.add(spSoLuong);

        btnCapNhat = new JButton("Cập nhật");
        btnHuy = new JButton("Hủy");
        panel.add(btnCapNhat);
        panel.add(btnHuy);

        add(panel);
        setVisible(true);

        btnCapNhat.addActionListener(e -> capNhatCSVC());
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

    private void capNhatCSVC() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE PhongHoc_CSVC SET SoLuong = ?, TinhTrang = ? WHERE MaPhong = ? AND MaTB = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, (int) spSoLuong.getValue());
            stmt.setString(2, cbTinhTrang.getSelectedItem().toString());
            stmt.setString(3, tfMaPhong.getText());
            stmt.setString(4, tfMaTB.getText());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cập nhật CSVC thành công!");
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }
}