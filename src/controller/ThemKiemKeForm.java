/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import util.DBConnection;

public class ThemKiemKeForm extends JFrame {
    private JTextField txtMaKK, txtNguoiThucHien;
    private JTextArea txtGhiChu;
    private JFormattedTextField txtNgayKK;
    private JButton btnThem, btnHuy;

    public ThemKiemKeForm() {
        setTitle("Thêm phiếu kiểm kê");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtMaKK = new JTextField();
        txtMaKK.setEditable(false);
        txtMaKK.setText(sinhMaKKTuDong());

        txtNgayKK = new JFormattedTextField(java.time.LocalDate.now());
        txtNguoiThucHien = new JTextField();
        txtGhiChu = new JTextArea(3, 20);
        JScrollPane scroll = new JScrollPane(txtGhiChu);

        panel.add(new JLabel("Mã kiểm kê:"));
        panel.add(txtMaKK);

        panel.add(new JLabel("Ngày kiểm kê (yyyy-mm-dd):"));
        panel.add(txtNgayKK);

        panel.add(new JLabel("Người thực hiện:"));
        panel.add(txtNguoiThucHien);

        panel.add(new JLabel("Ghi chú:"));
        panel.add(scroll);

        btnThem = new JButton("Thêm");
        btnHuy = new JButton("Hủy");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnThem);
        buttonPanel.add(btnHuy);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Xử lý nút
        btnThem.addActionListener(e -> themPhieuKiemKe());
        btnHuy.addActionListener(e -> dispose());
    }

    private String sinhMaKKTuDong() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT TOP 1 MaKK FROM KiemKeCSVC ORDER BY MaKK DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String lastMa = rs.getString("MaKK");
                int number = Integer.parseInt(lastMa.substring(2)) + 1;
                return String.format("KK%03d", number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "KK001";
    }

    private void themPhieuKiemKe() {
        String ma = txtMaKK.getText();
        String ngay = txtNgayKK.getText();
        String nguoi = txtNguoiThucHien.getText();
        String ghichu = txtGhiChu.getText();

        if (nguoi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập người thực hiện");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO KiemKeCSVC (MaKK, NgayKK, NguoiThucHien, GhiChu) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ma);
            stmt.setDate(2, Date.valueOf(ngay));
            stmt.setString(3, nguoi);
            stmt.setString(4, ghichu);

            int result = stmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Thêm phiếu kiểm kê thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể thêm phiếu kiểm kê");
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi thêm: " + e.getMessage());
        }
    }
}
