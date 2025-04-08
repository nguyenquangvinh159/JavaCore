package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.PhongHocCSVCForm;

public class AddPhongHocForm extends JFrame {

    private JLabel lblMa, lblTen, lblLoai, lblSucChua;
    private JTextField txtMa, txtTen, txtSucChua;
    private JComboBox<String> cbLoaiPhong;
    private JButton btnLuu, btnTroVe;

    public AddPhongHocForm() {
        initComponents();
    }

    private void initComponents() {
        // Khởi tạo các thành phần giao diện người dùng
        lblMa = new JLabel("Mã Phòng:");
        lblMa.setBounds(30, 30, 100, 25);
        txtMa = new JTextField();
        txtMa.setBounds(150, 30, 200, 25);

        lblTen = new JLabel("Tên Phòng:");
        lblTen.setBounds(30, 70, 100, 25);
        txtTen = new JTextField();
        txtTen.setBounds(150, 70, 200, 25);

        lblLoai = new JLabel("Loại Phòng:");
        lblLoai.setBounds(30, 110, 100, 25);
        cbLoaiPhong = new JComboBox<>(new String[] {"Phòng Lý Thuyết", "Phòng Máy", "Phòng Thực Hành"});
        cbLoaiPhong.setBounds(150, 110, 200, 25);

        lblSucChua = new JLabel("Sức Chứa:");
        lblSucChua.setBounds(30, 150, 100, 25);
        txtSucChua = new JTextField();
        txtSucChua.setBounds(150, 150, 200, 25);

        btnLuu = new JButton("Lưu");
        btnLuu.setBounds(150, 200, 100, 30);
        btnTroVe = new JButton("Trở về");
        btnTroVe.setBounds(10, 240, 80, 25);

        // Thêm các thành phần vào frame
        add(lblMa);
        add(txtMa);
        add(lblTen);
        add(txtTen);
        add(lblLoai);
        add(cbLoaiPhong);
        add(lblSucChua);
        add(txtSucChua);
        add(btnLuu);
        add(btnTroVe);

        setLayout(null);
        setSize(400, 350);
        setLocationRelativeTo(null); // Đặt cửa sổ ở giữa màn hình
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Xử lý sự kiện cho nút "Lưu"
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addPhongHoc();
                } catch (Exception ex) {
                    Logger.getLogger(AddPhongHocForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Xử lý sự kiện cho nút "Trở về"
        btnTroVe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new PhongHocCSVCForm();
                } catch (Exception ex) {
                    Logger.getLogger(AddPhongHocForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        setVisible(true);
    }

    private void addPhongHoc() throws Exception {
        // Lấy dữ liệu từ các trường nhập
        String maPhong = txtMa.getText();
        String tenPhong = txtTen.getText();
        String loaiPhong = (String) cbLoaiPhong.getSelectedItem();
        String sucChua = txtSucChua.getText();

        // Kiểm tra dữ liệu hợp lệ
        if (maPhong.isEmpty() || tenPhong.isEmpty() || sucChua.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Thông báo thành công
        JOptionPane.showMessageDialog(this, "Phòng học đã được thêm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

        dispose();
        new PhongHocCSVCForm();
    }

}
