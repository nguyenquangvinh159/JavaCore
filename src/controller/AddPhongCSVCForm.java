package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import ui.PhongHocCSVCForm;
import util.DBConnection;

public class AddPhongCSVCForm extends JFrame {
    private JComboBox<String> comboMaTB;
    private JTextField txtTenTB, txtLoai, txtDonVi, txtTinhTrang, txtSoLuong;
    private JButton btnThem, btnHuy;

    private String maPhong;

    public AddPhongCSVCForm(String maPhong) throws Exception {
        this.maPhong = maPhong;
        setTitle("Thêm CSVC vào Phòng " + maPhong);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        loadComboBoxMaTB();
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new GridLayout(7, 2, 5, 5));

        comboMaTB = new JComboBox<>();
        txtTenTB = new JTextField();
        txtLoai = new JTextField();
        txtDonVi = new JTextField();
        txtTinhTrang = new JTextField();
        txtSoLuong = new JTextField();

        txtTenTB.setEditable(false);
        txtLoai.setEditable(false);
        txtDonVi.setEditable(false);
        txtTinhTrang.setEditable(false);

        btnThem = new JButton("Thêm");
        btnHuy = new JButton("Hủy");

        add(new JLabel("Mã Thiết Bị:"));
        add(comboMaTB);
        add(new JLabel("Tên Thiết Bị:"));
        add(txtTenTB);
        add(new JLabel("Loại:"));
        add(txtLoai);
        add(new JLabel("Đơn Vị Tính:"));
        add(txtDonVi);
        add(new JLabel("Tình Trạng:"));
        add(txtTinhTrang);
        add(new JLabel("Số Lượng:"));
        add(txtSoLuong);
        add(btnThem);
        add(btnHuy);

        comboMaTB.addActionListener(e -> updateThongTinThietBi());
        btnThem.addActionListener(e -> themCSVCVaoPhong());
        btnHuy.addActionListener(e -> {
            dispose();
            try {
                new PhongHocCSVCForm();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Không thể mở lại PhongHocCSVCForm: " + ex.getMessage());
            }
        });
    }

    private void loadComboBoxMaTB() throws Exception {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT MaCSVC FROM CoSoVatChat";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            comboMaTB.addItem(rs.getString("MaCSVC"));
        }
        rs.close();
        stmt.close();
        conn.close();

        updateThongTinThietBi();
    }

    private void updateThongTinThietBi() {
        String maTB = (String) comboMaTB.getSelectedItem();
        if (maTB == null) return;

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT TenCSVC, LoaiCSVC, DonViTinh, TinhTrang FROM CoSoVatChat WHERE MaCSVC = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maTB);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                txtTenTB.setText(rs.getString("TenCSVC"));
                txtLoai.setText(rs.getString("LoaiCSVC"));
                txtDonVi.setText(rs.getString("DonViTinh"));
                txtTinhTrang.setText(rs.getString("TinhTrang"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi load thông tin thiết bị: " + ex.getMessage());
        }
    }

    private void themCSVCVaoPhong() {
        String maTB = (String) comboMaTB.getSelectedItem();
        int soLuong;

        try {
            soLuong = Integer.parseInt(txtSoLuong.getText());
            if (soLuong <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên dương.");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO PhongHoc_CSVC (MaPhong, MaCSVC, SoLuong) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maPhong);
            stmt.setString(2, maTB);
            stmt.setInt(3, soLuong);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Thêm cơ sở vật chất thành công!");
                dispose();
            }

            stmt.close();
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm CSVC vào phòng: " + ex.getMessage());
        }
    }
}
