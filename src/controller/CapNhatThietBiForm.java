package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import model.ThietBi;

public class CapNhatThietBiForm extends JFrame {
    private JLabel lblPreview;
    private String selectedImagePath;

    public CapNhatThietBiForm(ThietBi tb) {
        setTitle("✏️ Cập nhật thiết bị: " + tb.getMaTB());
        setSize(550, 600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 2, 10, 10));

        add(new JLabel("Tên thiết bị:"));
        JTextField txtTen = new JTextField(tb.getTenTB()); add(txtTen);

        add(new JLabel("Loại thiết bị:"));
        JComboBox<String> cboLoai = new JComboBox<>(new String[]{"Máy chiếu", "Âm thanh", "Khác"});
        cboLoai.setSelectedItem(tb.getLoaiTB()); add(cboLoai);

        add(new JLabel("Số lượng:"));
        JSpinner spSoLuong = new JSpinner(new SpinnerNumberModel(tb.getSoLuong(), 1, 1000, 1));
        add(spSoLuong);

        add(new JLabel("Tình trạng:"));
        JComboBox<String> cboTinhTrang = new JComboBox<>(new String[]{"Tốt", "Bình thường", "Hư hỏng"});
        cboTinhTrang.setSelectedItem(tb.getTinhTrang()); add(cboTinhTrang);

        add(new JLabel("Đơn vị tính:"));
        JTextField txtDonVi = new JTextField(tb.getDonViTinh()); add(txtDonVi);

        add(new JLabel("Nhà cung cấp:"));
        JTextField txtNCC = new JTextField(tb.getNhaCungCap()); add(txtNCC);

        add(new JLabel("Giá trị tài sản:"));
        JTextField txtGiaTri = new JTextField(String.valueOf(tb.getGiaTriTaiSan())); add(txtGiaTri);

        add(new JLabel("Ngày mua:"));
        JTextField txtNgayMua = new JTextField(String.valueOf(tb.getNgayMua())); add(txtNgayMua);

        add(new JLabel("Hạn bảo hành:"));
        JTextField txtHanBH = new JTextField(String.valueOf(tb.getHanBaoHanh())); add(txtHanBH);

        add(new JLabel("Ghi chú:"));
        JTextField txtGhiChu = new JTextField(tb.getGhiChu()); add(txtGhiChu);

        JButton btnChonAnh = new JButton("🖼️ Đổi ảnh");
        lblPreview = new JLabel("", JLabel.CENTER);
        selectedImagePath = tb.getHinhAnh();
        if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
            ImageIcon icon = new ImageIcon(selectedImagePath);
            Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblPreview.setIcon(new ImageIcon(img));
        } else {
            lblPreview.setText("Chưa có ảnh");
        }
        add(btnChonAnh); add(lblPreview);

        JButton btnLuu = new JButton("💾 Cập nhật");
        JButton btnHuy = new JButton("❌ Hủy");
        add(btnLuu); add(btnHuy);

        btnChonAnh.addActionListener(e -> chonAnh());

        btnLuu.addActionListener(e -> {
            // TODO: update DB ở đây
            JOptionPane.showMessageDialog(this, "Đã cập nhật (demo)");
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
