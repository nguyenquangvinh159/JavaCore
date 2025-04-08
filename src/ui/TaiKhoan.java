package ui;

import util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TaiKhoan extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cboVaiTro;

    public TaiKhoan() {
        setTitle("Tạo tài khoản mới");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Tên đăng nhập:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Mật khẩu:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        add(new JLabel("Vai trò:"));
        cboVaiTro = new JComboBox<>(new String[]{"Quản lý", "Nhân viên"});
        add(cboVaiTro);

        JButton btnTao = new JButton("Tạo tài khoản");
        btnTao.addActionListener(e -> taoTaiKhoan());
        add(btnTao);

        JButton btnThoat = new JButton("Thoát");
        btnThoat.addActionListener(e -> dispose());
        add(btnThoat);
    }

    private void taoTaiKhoan() {
        String tenDangNhap = txtUsername.getText().trim();
        String matKhau = new String(txtPassword.getPassword()).trim();
        String vaiTro = cboVaiTro.getSelectedItem().toString();

        if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO TaiKhoan (TenDangNhap, MatKhau, VaiTro) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);
            ps.setString(3, vaiTro);

            int kq = ps.executeUpdate();
            if (kq > 0) {
                JOptionPane.showMessageDialog(this, "Tạo tài khoản thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Tạo tài khoản thất bại!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối hoặc tài khoản đã tồn tại");
        }
    }
}
