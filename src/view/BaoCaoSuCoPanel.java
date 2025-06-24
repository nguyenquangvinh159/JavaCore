package view;

import model.DAO.PhongHocDAO;
import model.DAO.ThietBiDAO;
import model.DAO.BaoCaoSuCoDAO;
import model.TaiKhoan;
import model.PhongHoc;
import model.ThietBi;
import model.BaoCaoSuCo;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class BaoCaoSuCoPanel extends JPanel {
    private JComboBox<String> cboThietBi, cboPhong, cboMucDo;
    private JTextArea txtMoTa;
    private TaiKhoan nguoiDung;

    public BaoCaoSuCoPanel(TaiKhoan tk) {
        this.nguoiDung = tk;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("🛠️ Báo cáo sự cố thiết bị", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // ====== FORM CHÍNH ======
        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        form.setBackground(Color.WHITE);

        // Danh sách thiết bị
        cboThietBi = new JComboBox<>();
        for (ThietBi tb : new ThietBiDAO().getAllThietBi()) {
            cboThietBi.addItem(tb.getMaTB() + " - " + tb.getTenTB());
        }

        // Danh sách phòng
        cboPhong = new JComboBox<>();
        for (PhongHoc p : new PhongHocDAO().getAllPhongHoc()) {
            cboPhong.addItem(p.getMaPhong() + " - " + p.getTenPhong());
        }

        // Mức độ nghiêm trọng
        cboMucDo = new JComboBox<>(new String[] { "Thấp", "Trung bình", "Cao", "Nghiêm trọng" });

        // Mô tả sự cố
        txtMoTa = new JTextArea(3, 20);
        txtMoTa.setLineWrap(true);

        JButton btnGui = new JButton("📤 Gửi báo cáo");

        form.add(new JLabel("Thiết bị gặp sự cố:"));
        form.add(cboThietBi);
        form.add(new JLabel("Phòng học:"));
        form.add(cboPhong);
        form.add(new JLabel("Mức độ nghiêm trọng:"));
        form.add(cboMucDo);
        form.add(new JLabel("Mô tả sự cố:"));
        form.add(new JScrollPane(txtMoTa));
        form.add(new JLabel(""));
        form.add(btnGui);

        // ====== GÓI FORM GIỮA ======
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setPreferredSize(new Dimension(550, 300));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(form, BorderLayout.CENTER);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(contentPanel);

        add(wrapper, BorderLayout.CENTER);

        btnGui.addActionListener(e -> guiBaoCao());
    }

    private void guiBaoCao() {
        try {
            String maTB = cboThietBi.getSelectedItem().toString().split(" - ")[0];
            String maPhong = cboPhong.getSelectedItem().toString().split(" - ")[0];
            String mucDo = cboMucDo.getSelectedItem().toString();
            String moTa = txtMoTa.getText().trim();

            if (moTa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mô tả sự cố!");
                return;
            }

            BaoCaoSuCo bc = new BaoCaoSuCo(
                nguoiDung.getTenDangNhap(), maTB, maPhong, moTa, mucDo
            );

            boolean ok = new BaoCaoSuCoDAO().guiBaoCao(bc);
            if (ok) {
                JOptionPane.showMessageDialog(this, "✅ Báo cáo đã được gửi!");
                txtMoTa.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Gửi báo cáo thất bại!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}
