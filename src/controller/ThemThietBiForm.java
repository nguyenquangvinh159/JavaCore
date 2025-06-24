package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ThemThietBiForm extends JFrame {
    private JLabel lblPreview;
    private String selectedImagePath;

    public ThemThietBiForm() {
        setTitle("➕ Thêm thiết bị mới");
        setSize(550, 600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 2, 10, 10));

        add(new JLabel("Mã thiết bị:"));
        JTextField txtMa = new JTextField(); add(txtMa);

        add(new JLabel("Tên thiết bị:"));
        JTextField txtTen = new JTextField(); add(txtTen);

        add(new JLabel("Loại thiết bị:"));
        JComboBox<String> cboLoai = new JComboBox<>(new String[]{"Máy chiếu", "Âm thanh", "Khác"});
        add(cboLoai);

        add(new JLabel("Số lượng:"));
        JSpinner spSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        add(spSoLuong);

        add(new JLabel("Tình trạng:"));
        JComboBox<String> cboTinhTrang = new JComboBox<>(new String[]{"Tốt", "Bình thường", "Hư hỏng"});
        add(cboTinhTrang);

        add(new JLabel("Đơn vị tính:"));
        JTextField txtDonVi = new JTextField(); add(txtDonVi);

        add(new JLabel("Nhà cung cấp:"));
        JTextField txtNCC = new JTextField(); add(txtNCC);

        add(new JLabel("Giá trị tài sản:"));
        JTextField txtGiaTri = new JTextField(); add(txtGiaTri);

        add(new JLabel("Ngày mua:"));
        JTextField txtNgayMua = new JTextField("yyyy-mm-dd"); add(txtNgayMua);

        add(new JLabel("Hạn bảo hành:"));
        JTextField txtHanBH = new JTextField("yyyy-mm-dd"); add(txtHanBH);

        add(new JLabel("Ghi chú:"));
        JTextField txtGhiChu = new JTextField(); add(txtGhiChu);

        JButton btnChonAnh = new JButton("📁 Chọn ảnh");
        lblPreview = new JLabel("Chưa có ảnh", JLabel.CENTER);
        add(btnChonAnh); add(lblPreview);

        JButton btnLuu = new JButton("💾 Lưu");
        JButton btnHuy = new JButton("❌ Hủy");
        add(btnLuu); add(btnHuy);

        btnChonAnh.addActionListener(e -> chonAnh());

        btnLuu.addActionListener(e -> {
            // TODO: insert vào DB ở đây
            JOptionPane.showMessageDialog(this, "Đã lưu (demo)");
            dispose();
        });

        btnHuy.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void chonAnh() {
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                File destDir = new File("images");
                destDir.mkdirs();
                File destFile = new File(destDir, file.getName());
                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                selectedImagePath = "images/" + file.getName();
                ImageIcon icon = new ImageIcon(selectedImagePath);
                Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                lblPreview.setIcon(new ImageIcon(img));
                lblPreview.setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
