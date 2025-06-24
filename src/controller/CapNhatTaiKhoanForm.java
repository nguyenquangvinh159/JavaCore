package controller;

import javax.swing.*;
import java.awt.*;
import model.TaiKhoan;
import model.DAO.TaiKhoanDAO;

public class CapNhatTaiKhoanForm extends JFrame {
    public CapNhatTaiKhoanForm(TaiKhoan tk, Runnable onUpdate) {
        setTitle("✏️ Cập nhật tài khoản: " + tk.getTenDangNhap());
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 2, 10, 10));

        add(new JLabel("Họ tên:"));
        JTextField txtHoTen = new JTextField(tk.getHoTen()); add(txtHoTen);

        add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField(tk.getEmail()); add(txtEmail);

        add(new JLabel("SĐT:"));
        JTextField txtSDT = new JTextField(tk.getSoDienThoai()); add(txtSDT);

        add(new JLabel("Vai trò:"));
        JComboBox<String> cboVaiTro = new JComboBox<>(new String[]{"sinhvien", "nhanvien", "admin", "quanly", "kythuat"});
        cboVaiTro.setSelectedItem(tk.getVaiTro()); add(cboVaiTro);

        JButton btnLuu = new JButton("💾 Lưu");
        JButton btnHuy = new JButton("❌ Hủy");
        add(btnLuu); add(btnHuy);

        btnLuu.addActionListener(e -> {
            tk.setHoTen(txtHoTen.getText());
            tk.setEmail(txtEmail.getText());
            tk.setSoDienThoai(txtSDT.getText());
            tk.setVaiTro(cboVaiTro.getSelectedItem().toString());
            boolean ok = new TaiKhoanDAO().capNhatTaiKhoan(tk);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                onUpdate.run();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        });

        btnHuy.addActionListener(e -> dispose());

        setVisible(true);
    }
}
