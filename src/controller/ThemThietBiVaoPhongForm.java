package controller;

import javax.swing.*;
import java.awt.*;
import model.ThietBi;
import model.DAO.ThietBiDAO;
import model.DAO.CSVCPhongDAO;

public class ThemThietBiVaoPhongForm extends JDialog {
    private JComboBox<String> cboMaTB;
    private JTextField txtSoLuong;
    private JButton btnLuu;
    private String maPhong;
    private Runnable onSaved;

    public ThemThietBiVaoPhongForm(String maPhong, Runnable onSaved) {
        this.maPhong = maPhong;
        this.onSaved = onSaved;

        setTitle("➕ Thêm thiết bị vào phòng " + maPhong);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);	

        cboMaTB = new JComboBox<>();
        for (ThietBi tb : new ThietBiDAO().getAllThietBi()) {
            cboMaTB.addItem(tb.getMaTB() + " - " + tb.getTenTB());
        }

        txtSoLuong = new JTextField();
        btnLuu = new JButton("Lưu");

        add(new JLabel("Chọn thiết bị:")); add(cboMaTB);
        add(new JLabel("Số lượng:")); add(txtSoLuong);
        add(new JLabel("")); add(btnLuu);

        btnLuu.addActionListener(e -> luu());


    }

    private void luu() {
        try {
            String maTB = cboMaTB.getSelectedItem().toString().split(" - ")[0];
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());

            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải > 0");
                return;
            }

            boolean ok = new CSVCPhongDAO().themHoacCapNhat(maPhong, maTB, soLuong);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Lưu thành công!");
                onSaved.run();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lưu thất bại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }
}
