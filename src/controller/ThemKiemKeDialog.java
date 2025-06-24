package controller;

import model.DAO.ChiTietKiemKeDAO;
import model.DAO.KiemKeDAO;
import model.DAO.PhongHocDAO;
import model.DAO.ThietBiDAO;
import model.KiemKe;
import model.ThietBi;
import model.TaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ThemKiemKeDialog extends JDialog {
    private JComboBox<String> cboPhong;
    private JComboBox<String> cboLoai;
    private JTextArea txtGhiChu;
    private JButton btnTao;
    private TaiKhoan nguoiDung;

    public ThemKiemKeDialog(JFrame parent, TaiKhoan nguoiDung) {
        super(parent, "➕ Thêm kiểm kê", true);
        this.nguoiDung = nguoiDung;

        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // ======= Tiêu đề ========
        JLabel lblTitle = new JLabel("➕ Tạo đợt kiểm kê mới", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        // ======= Form ========
        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        cboPhong = new JComboBox<>();
        for (var p : new PhongHocDAO().getAllPhongHoc()) {
            cboPhong.addItem(p.getMaPhong() + " - " + p.getTenPhong());
        }

        cboLoai = new JComboBox<>(new String[]{"Định kỳ", "Đột xuất", "Bàn giao"});
        txtGhiChu = new JTextArea(3, 20);
        txtGhiChu.setLineWrap(true);

        form.add(new JLabel("Phòng kiểm kê:"));
        form.add(cboPhong);
        form.add(new JLabel("Loại kiểm kê:"));
        form.add(cboLoai);
        form.add(new JLabel("Ghi chú:"));
        form.add(new JScrollPane(txtGhiChu));

        add(form, BorderLayout.CENTER);

        // ======= Nút ========
        btnTao = new JButton("✅ Tạo");
        JPanel bottom = new JPanel();
        bottom.add(btnTao);
        add(bottom, BorderLayout.SOUTH);

        // ======= Sự kiện ========
        btnTao.addActionListener(e -> {
            String maPhong = cboPhong.getSelectedItem().toString().split(" - ")[0];
            String loai = cboLoai.getSelectedItem().toString();
            String ghiChu = txtGhiChu.getText().trim();

            KiemKe kk = new KiemKe(
                    nguoiDung.getTenDangNhap(), maPhong,
                    LocalDate.now(), loai, "Đang thực hiện", ghiChu
            );

            int maKiemKe = new KiemKeDAO().themKiemKeVaLayMa(kk);
            if (maKiemKe != -1) {
                new ChiTietKiemKeDAO().themChiTietTuPhong(maKiemKe, maPhong);
                JOptionPane.showMessageDialog(this, "Tạo đợt kiểm kê thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Tạo thất bại!");
            }
        });

    }
}
