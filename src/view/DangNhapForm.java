package view;

import javax.swing.*;
import java.awt.*;
import model.DAO.TaiKhoanDAO;
import model.TaiKhoan;

public class DangNhapForm extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;

    public DangNhapForm() {
        setTitle("Đăng nhập hệ thống UTC2");
        setSize(400, 540);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel 
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 247, 255));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));

        // Tiêu đề & mô tả
        JLabel lblLogo = new JLabel("🏫 UTC2");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblLogo.setForeground(new Color(0, 102, 204));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Hệ thống Quản lý Cơ sở Vật chất");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(Color.DARK_GRAY);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(lblLogo);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(lblSub);
        mainPanel.add(Box.createVerticalStrut(30));

        // Tên đăng nhập
        JLabel lblUser = new JLabel("Tên đăng nhập");
        txtUser = new JTextField();
        styleTextInput(txtUser);

        // Mật khẩu
        JLabel lblPass = new JLabel("Mật khẩu");
        txtPass = new JPasswordField();
        styleTextInput(txtPass);

        // Nút đăng nhập
        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(new Color(0, 102, 255));
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(300, 40));
        btnLogin.setMaximumSize(new Dimension(300, 40));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.addActionListener(e -> dangNhap());

        // 2 nút phụ bên dưới
        JPanel bottomButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomButtons.setOpaque(false);

        JButton btnForgot = new JButton("Quên mật khẩu");
        JButton btnRegister = new JButton("Tạo tài khoản");

        for (JButton b : new JButton[]{btnForgot, btnRegister}) {
            b.setFocusPainted(false);
            b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            b.setBackground(Color.WHITE);
            b.setForeground(new Color(0, 102, 204));
            b.setBorder(BorderFactory.createEmptyBorder());
        }

        // Sự kiện mở form khác
        btnForgot.addActionListener(e -> new QuenMatKhauForm().setVisible(true));
        btnRegister.addActionListener(e -> new DangKyForm().setVisible(true));

        bottomButtons.add(btnForgot);
        bottomButtons.add(btnRegister);

        // Thêm vào main panel
        mainPanel.add(lblUser); mainPanel.add(txtUser);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(lblPass); mainPanel.add(txtPass);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(btnLogin);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(bottomButtons);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void styleTextInput(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 40));
        field.setMaximumSize(new Dimension(300, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    private void dangNhap() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();

        TaiKhoan tk = new TaiKhoanDAO().dangNhap(user, pass);
        if (tk != null) {
            JOptionPane.showMessageDialog(this, "Xin chào " + tk.getHoTen());
            dispose(); // đóng form đăng nhập
            new MenuQuanLyForm(tk).setVisible(true); // truyền đối tượng đã đăng nhập
        } else {
            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!");
        }
    }

}
