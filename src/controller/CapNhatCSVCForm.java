package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import ui.DanhSachCSVCForm;
import util.DBConnection;

public class CapNhatCSVCForm extends JFrame {
    private JTextField txtMa, txtTen, txtSoLuong, txtDonVi;
    private JComboBox<String> cbLoai, cbTinhTrang;
    private JButton btnCapNhat, btnTroVe;
    private String maCu;

    private DanhSachCSVCForm parentForm;

    public CapNhatCSVCForm(String ma, String ten, String loai, int soLuong, String tinhTrang, String donVi, DanhSachCSVCForm parentForm) {
        this.parentForm = parentForm;
        this.maCu = ma;

        initComponents();

        setTitle("Cập nhật cơ sở vật chất");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Label và TextField
        JLabel lblMa = new JLabel("Mã:");
        lblMa.setBounds(30, 30, 100, 25);
        add(lblMa);

        txtMa = new JTextField(ma);
        txtMa.setBounds(150, 30, 250, 25);
        txtMa.setEditable(false);
        add(txtMa);

        JLabel lblTen = new JLabel("Tên:");
        lblTen.setBounds(30, 70, 100, 25);
        add(lblTen);

        txtTen = new JTextField(ten);
        txtTen.setBounds(150, 70, 250, 25);
        add(txtTen);

        JLabel lblLoai = new JLabel("Loại:");
        lblLoai.setBounds(30, 110, 100, 25);
        add(lblLoai);

        cbLoai = new JComboBox<>(new String[] {"Máy tính", "Máy chiếu", "Bàn", "Ghế", "Khác"});
        cbLoai.setBounds(150, 110, 250, 25);
        cbLoai.setSelectedItem(loai);
        add(cbLoai);

        JLabel lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setBounds(30, 150, 100, 25);
        add(lblSoLuong);

        txtSoLuong = new JTextField(String.valueOf(soLuong));
        txtSoLuong.setBounds(150, 150, 250, 25);
        add(txtSoLuong);

        JLabel lblTinhTrang = new JLabel("Tình trạng:");
        lblTinhTrang.setBounds(30, 190, 100, 25);
        add(lblTinhTrang);

        cbTinhTrang = new JComboBox<>(new String[] {"Tốt", "Hỏng", "Đang sửa"});
        cbTinhTrang.setBounds(150, 190, 250, 25);
        cbTinhTrang.setSelectedItem(tinhTrang);
        add(cbTinhTrang);

        JLabel lblDonVi = new JLabel("Đơn vị tính:");
        lblDonVi.setBounds(30, 230, 100, 25);
        add(lblDonVi);

        txtDonVi = new JTextField(donVi);
        txtDonVi.setBounds(150, 230, 250, 25);
        add(txtDonVi);

        // Nút cập nhật
        btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.setBounds(150, 280, 150, 30);
        add(btnCapNhat);

        // Nút trở về
        btnTroVe = new JButton("Trở về");
        btnTroVe.setBounds(10, 320, 80, 25);
        btnTroVe.setFont(new Font("Arial", Font.PLAIN, 10));
        add(btnTroVe);

        // Sự kiện nút trở về
        btnTroVe.addActionListener(e -> dispose());

        // Sự kiện nút cập nhật
        btnCapNhat.addActionListener(e -> {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "UPDATE ThietBi SET TenTB=?, LoaiTB=?, SoLuong=?, TinhTrang=?, DonViTinh=? WHERE MaTB=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, txtTen.getText());
                stmt.setString(2, cbLoai.getSelectedItem().toString());
                stmt.setInt(3, Integer.parseInt(txtSoLuong.getText()));
                stmt.setString(4, cbTinhTrang.getSelectedItem().toString());
                stmt.setString(5, txtDonVi.getText());
                stmt.setString(6, maCu);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");

                    // GỌI LẠI FORM CHA ĐỂ LOAD DỮ LIỆU MỚI
                    if (parentForm != null) {
                        parentForm.loadData();
                    }

                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể cập nhật dữ liệu.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }

    private void initComponents() {
        setLocationRelativeTo(null);
    }
}
