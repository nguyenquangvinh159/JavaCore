package view;

import javax.swing.*;
import java.awt.*;
import model.DAO.TaiKhoanDAO;

public class QuenMatKhauForm extends JFrame {
    private JTextField txtUser, txtEmail;

    public QuenMatKhauForm() {
        setTitle("🔑 Quên mật khẩu");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        txtUser = new JTextField();
        txtEmail = new JTextField();
        JButton btnXacNhan = new JButton("Đặt lại mật khẩu");

        // Tùy chỉnh nút: màu xanh dương + bo góc
        btnXacNhan.setBackground(new Color(30, 144, 255)); // xanh dương
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setFocusPainted(false);
        btnXacNhan.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnXacNhan.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 1, true));

        add(new JLabel("Tên đăng nhập:")); add(txtUser);
        add(new JLabel("Email:")); add(txtEmail);
        add(new JLabel("")); add(btnXacNhan);

        btnXacNhan.addActionListener(e -> xuLyQuenMK());
    }

    private void xuLyQuenMK() {
        String user = txtUser.getText().trim();
        String email = txtEmail.getText().trim();

        boolean hopLe = new TaiKhoanDAO().kiemTraTaiKhoanVaEmail(user, email);
        if (hopLe) {
            String mkMoi = JOptionPane.showInputDialog(this, "Nhập mật khẩu mới:");
            if (mkMoi != null && !mkMoi.trim().isEmpty()) {
                boolean thanhCong = new TaiKhoanDAO().datLaiMatKhau(user, mkMoi.trim());
                if (thanhCong) {
                    JOptionPane.showMessageDialog(this, "✅ Mật khẩu đã được cập nhật!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Cập nhật thất bại!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu mới!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản hoặc email không đúng!");
        }
    }
}
