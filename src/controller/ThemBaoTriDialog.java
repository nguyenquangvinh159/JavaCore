package controller;

import model.LichBaoTri;
import model.DAO.LichBaoTriDAO;
import model.DAO.ThietBiDAO;
import model.ThietBi;
import model.TaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ThemBaoTriDialog extends JDialog {
    private JComboBox<String> cboThietBi, cboLoai;
    private JTextField txtNgayDuKien, txtChiPhi;
    private JTextArea txtNoiDung;
    private TaiKhoan nguoiDung;

    public ThemBaoTriDialog(JFrame parent, TaiKhoan nguoiDung) {
        super(parent, "Thêm lịch bảo trì", true);
        this.nguoiDung = nguoiDung;
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("➕ Lịch bảo trì mới", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        cboThietBi = new JComboBox<>();
        List<ThietBi> list = new ThietBiDAO().getAllThietBi();
        for (ThietBi tb : list) {
            cboThietBi.addItem(tb.getMaTB());
        }

        cboLoai = new JComboBox<>(new String[]{"Định kỳ", "Sửa chữa", "Bảo dưỡng"});
        txtNgayDuKien = new JTextField(LocalDate.now().toString());
        txtChiPhi = new JTextField();
        txtNoiDung = new JTextArea(3, 20);
        txtNoiDung.setLineWrap(true);

        form.add(new JLabel("Thiết bị:"));
        form.add(cboThietBi);
        form.add(new JLabel("Loại bảo trì:"));
        form.add(cboLoai);
        form.add(new JLabel("Ngày dự kiến (yyyy-MM-dd):"));
        form.add(txtNgayDuKien);
        form.add(new JLabel("Chi phí:"));
        form.add(txtChiPhi);
        form.add(new JLabel("Nội dung:"));
        form.add(new JScrollPane(txtNoiDung));

        add(form, BorderLayout.CENTER);

        JButton btnThem = new JButton("✅ Thêm");
        btnThem.addActionListener(e -> themLich());
        JPanel bottom = new JPanel();
        bottom.add(btnThem);
        add(bottom, BorderLayout.SOUTH);
    }

    private void themLich() {
        try {
            // Parse ngày dự kiến
            LocalDate ngayDuKien = LocalDate.parse(txtNgayDuKien.getText().trim());
            LocalDate ngayHienTai = LocalDate.now();

            if (ngayDuKien.isBefore(ngayHienTai)) {
                JOptionPane.showMessageDialog(this, "❌ Ngày dự kiến không được nhỏ hơn ngày hiện tại!");
                return;
            }

            LichBaoTri lbt = new LichBaoTri();
            lbt.setMaTB(cboThietBi.getSelectedItem().toString());
            lbt.setLoaiBaoTri(cboLoai.getSelectedItem().toString());
            lbt.setNgayDuKien(ngayDuKien);
            lbt.setNoiDung(txtNoiDung.getText().trim());
            lbt.setNguoiThucHien(nguoiDung.getTenDangNhap());
            lbt.setChiPhi(Double.parseDouble(txtChiPhi.getText().trim()));
            lbt.setTrangThai("Chưa thực hiện");

            boolean ok = new LichBaoTriDAO().themLichBaoTri(lbt);
            if (ok) {
                JOptionPane.showMessageDialog(this, "✅ Đã thêm lịch bảo trì!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Thêm thất bại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Lỗi: " + e.getMessage());
        }
    }
}
