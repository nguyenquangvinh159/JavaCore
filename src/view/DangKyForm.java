package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import model.DAO.TaiKhoanDAO;

public class DangKyForm extends JFrame {
    private JTextField txtUser, txtHoTen, txtEmail, txtSDT;
    private JPasswordField txtPass;
    private JComboBox<String> cboVaiTro;
    private JLabel lblAvatarPath;
    private String selectedAvatar = "default_avatar.jpg";

    public DangKyForm() {
        setTitle("Đăng ký tài khoản");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 1, 5, 5));

        txtUser = new JTextField();
        txtPass = new JPasswordField();
        txtHoTen = new JTextField();
        txtEmail = new JTextField();
        txtSDT = new JTextField();
        cboVaiTro = new JComboBox<>(new String[] { "sinhvien", "nhanvien", "admin", "quanly", "kythuat" });
        lblAvatarPath = new JLabel("Ảnh đại diện: mặc định");

        JButton btnChonAnh = new JButton("Chọn ảnh");
        JButton btnDangKy = new JButton("Đăng ký");

        add(new JLabel("Tên đăng nhập:")); add(txtUser);
        add(new JLabel("Mật khẩu:")); add(txtPass);
        add(new JLabel("Họ tên:")); add(txtHoTen);
        add(new JLabel("Email:")); add(txtEmail);
        add(new JLabel("SĐT:")); add(txtSDT);
        add(new JLabel("Vai trò:")); add(cboVaiTro);
        add(lblAvatarPath); add(btnChonAnh);
        add(btnDangKy);

        btnChonAnh.addActionListener(e -> chonAnh());
        btnDangKy.addActionListener(e -> dangKy());
    }

    private void chonAnh() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            selectedAvatar = f.getName(); // Lưu tên file
            lblAvatarPath.setText("Đã chọn: " + selectedAvatar);
            // TODO: Copy file vào thư mục /images nếu cần
        }
    }

    private void dangKy() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();
        String hoten = txtHoTen.getText().trim();
        String email = txtEmail.getText().trim();
        String sdt = txtSDT.getText().trim();
        String vaitro = cboVaiTro.getSelectedItem().toString();

        if (user.isEmpty() || pass.isEmpty() || hoten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống các ô bắt buộc");
            return;
        }

        boolean success = new TaiKhoanDAO().dangKy(user, pass, vaitro, hoten, selectedAvatar, email, sdt);
        if (success) {
            JOptionPane.showMessageDialog(this, "Vui lòng chờ sét duyệt");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!");
        }
    }
}
