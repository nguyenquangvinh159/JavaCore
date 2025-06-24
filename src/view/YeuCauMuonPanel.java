package view;

import model.YeuCauMuon;
import model.DAO.ThietBiDAO;
import model.DAO.YeuCauMuonDAO;
import model.ThietBi;
import model.TaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class YeuCauMuonPanel extends JPanel {
    private JComboBox<String> cboThietBi;
    private JTextField txtSoLuong, txtNgayTra;
    private JTextArea txtMucDich;
    private TaiKhoan nguoiDung;

    public YeuCauMuonPanel(TaiKhoan tk) {
        this.nguoiDung = tk;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("📥 Yêu cầu mượn thiết bị", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // ====== FORM THẬT ======
        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        form.setBackground(Color.WHITE);

        cboThietBi = new JComboBox<>();
        List<ThietBi> ds = new ThietBiDAO().getAllThietBi();
        for (ThietBi tb : ds) {
            cboThietBi.addItem(tb.getMaTB() + " - " + tb.getTenTB());
        }

        txtSoLuong = new JTextField();
        txtNgayTra = new JTextField();
        txtMucDich = new JTextArea(3, 20);
        txtMucDich.setLineWrap(true);

        JButton btnGui = new JButton("📤 Gửi yêu cầu");

        form.add(new JLabel("Thiết bị:"));
        form.add(cboThietBi);
        form.add(new JLabel("Số lượng mượn:"));
        form.add(txtSoLuong);
        form.add(new JLabel("Ngày trả dự kiến (yyyy-MM-dd):"));
        form.add(txtNgayTra);
        form.add(new JLabel("Mục đích sử dụng:"));
        form.add(new JScrollPane(txtMucDich));
        form.add(new JLabel(""));
        form.add(btnGui);

        // ====== BỌC FORM VỚI KÍCH THƯỚC NHỎ ======
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setPreferredSize(new Dimension(550, 300));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(form, BorderLayout.CENTER);

        // ====== WRAPPER CĂN GIỮA ======
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(contentPanel);

        add(wrapper, BorderLayout.CENTER);

        // ====== XỬ LÝ GỬI ======
        btnGui.addActionListener(e -> guiYeuCau());
    }

    private void guiYeuCau() {
        try {
            String maTB = cboThietBi.getSelectedItem().toString().split(" - ")[0];
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            LocalDate ngayMuon = LocalDate.now();
            LocalDate ngayTraDuKien = LocalDate.parse(txtNgayTra.getText().trim());
            String mucDich = txtMucDich.getText().trim();

            YeuCauMuon yc = new YeuCauMuon(
                    nguoiDung.getTenDangNhap(), maTB, soLuong, ngayMuon, ngayTraDuKien, mucDich
            );
            boolean ok = new YeuCauMuonDAO().themYeuCau(yc);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Đã gửi yêu cầu mượn thành công!");
                txtSoLuong.setText("");
                txtNgayTra.setText("");
                txtMucDich.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Gửi yêu cầu thất bại!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}
