package ui;

import controller.AddPhongCSVCForm;
import controller.AddPhongHocForm;
import controller.EditPhongCSVCForm;
import controller.EditPhongHocForm;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DBConnection;

public class PhongHocCSVCForm extends JFrame {
    private JTable tablePhongHoc, tablePhongHocCSVC;
    private JButton btnThemPhongHoc, btnSuaPhongHoc, btnXoaPhongHoc;
    private JButton btnThemCSVC, btnSuaCSVC, btnXoaCSVC;
    private JButton btnQuayLai;

    public PhongHocCSVCForm() throws Exception {
        setTitle("Danh sách phòng học và cơ sở vật chất");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel cho bảng Phòng Học
        JPanel panelPhongHoc = new JPanel();
        panelPhongHoc.setLayout(new BorderLayout());

        // Tạo bảng Phòng Học
        tablePhongHoc = new JTable();
        JScrollPane scrollPanePhongHoc = new JScrollPane(tablePhongHoc);
        scrollPanePhongHoc.setPreferredSize(new Dimension(800, 200));
        panelPhongHoc.add(scrollPanePhongHoc, BorderLayout.CENTER);

        // Các nút dưới bảng Phòng Học
        JPanel panelPhongHocButtons = new JPanel();
        btnThemPhongHoc = new JButton("Thêm");
        btnSuaPhongHoc = new JButton("Sửa");
        btnXoaPhongHoc = new JButton("Xóa");
        panelPhongHocButtons.add(btnThemPhongHoc);
        panelPhongHocButtons.add(btnSuaPhongHoc);
        panelPhongHocButtons.add(btnXoaPhongHoc);
        panelPhongHoc.add(panelPhongHocButtons, BorderLayout.SOUTH);

        // Panel cho bảng Cơ Sở Vật Chất
        JPanel panelCSVC = new JPanel();
        panelCSVC.setLayout(new BorderLayout());

        // Tạo bảng Cơ Sở Vật Chất
        tablePhongHocCSVC = new JTable();
        JScrollPane scrollPaneCSVC = new JScrollPane(tablePhongHocCSVC);
        panelCSVC.add(scrollPaneCSVC, BorderLayout.CENTER);

        // Các nút dưới bảng Cơ Sở Vật Chất
        JPanel panelCSVCButtons = new JPanel();
        btnThemCSVC = new JButton("Thêm");
        btnSuaCSVC = new JButton("Sửa");
        btnXoaCSVC = new JButton("Xóa");
        panelCSVCButtons.add(btnThemCSVC);
        panelCSVCButtons.add(btnSuaCSVC);
        panelCSVCButtons.add(btnXoaCSVC);
        panelCSVC.add(panelCSVCButtons, BorderLayout.SOUTH);

        // Panel cho nút quay lại
        JPanel panelQuayLai = new JPanel();
        btnQuayLai = new JButton("Quay lại");
        panelQuayLai.add(btnQuayLai);
        add(panelQuayLai, BorderLayout.SOUTH);

        // Thêm các panel vào JFrame
        add(panelPhongHoc, BorderLayout.NORTH);
        add(panelCSVC, BorderLayout.CENTER);

        // Load dữ liệu cho bảng
        loadDataPhongHoc();
        loadDataPhongHocCSVC("");

        // Sự kiện cho các nút
        btnXoaPhongHoc.addActionListener(e -> {
            try {
                deletePhongHoc();
            } catch (Exception ex) {
                Logger.getLogger(PhongHocCSVCForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnXoaCSVC.addActionListener(e -> {
            try {
                deleteCSVC();
            } catch (Exception ex) {
                Logger.getLogger(PhongHocCSVCForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnSuaPhongHoc.addActionListener(e -> editPhongHoc());
        btnSuaCSVC.addActionListener(e -> editCSVC());

        btnThemPhongHoc.addActionListener(e -> addPhongHoc());
        btnThemCSVC.addActionListener(e -> {
            try {
                addCSVC();
            } catch (Exception ex) {
                Logger.getLogger(PhongHocCSVCForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Sự kiện khi chọn một phòng học trong bảng Phòng Học
        tablePhongHoc.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tablePhongHoc.getSelectedRow();
            if (selectedRow >= 0) {
                String maPhong = (String) tablePhongHoc.getValueAt(selectedRow, 0);
                try {
                    loadDataPhongHocCSVC(maPhong);
                } catch (Exception ex) {
                    Logger.getLogger(PhongHocCSVCForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Sự kiện cho nút "Quay lại"
        btnQuayLai.addActionListener(e -> {
            // Đóng form hiện tại và quay lại form chính
            this.dispose();
            new MenuQuanLyForm().setVisible(true);
        }); 
        setVisible(true);
    }

    // Tải dữ liệu vào bảng Phòng Học
    // Thay vì trực tiếp sử dụng getConnection() và không xử lý ngoại lệ, bạn nên bao bọc nó trong try-catch
public void loadDataPhongHoc() throws Exception {
    DefaultTableModel model = new DefaultTableModel(new Object[]{"Mã Phòng", "Tên Phòng", "Loại", "Sức chứa"}, 0);
    try {
        Connection conn = DBConnection.getConnection();  // Kết nối đến CSDL
        String sql = "SELECT * FROM PhongHoc";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("MaPhong"),
                rs.getString("TenPhong"),
                rs.getString("LoaiPhong"),
                rs.getInt("SucChua")
            });
        }
        conn.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    tablePhongHoc.setModel(model);
}

public void loadDataPhongHocCSVC(String maPhong) throws Exception {
    DefaultTableModel model = new DefaultTableModel(new Object[]{"Mã Phòng", "Mã Thiết Bị", "Tên Thiết Bị", "Số Lượng"}, 0);
    try {
        Connection conn = DBConnection.getConnection();  // Kết nối đến CSDL
        String sql = "SELECT p.MaPhong, p.MaCSVC, c.TenCSVC, p.SoLuong " +
                     "FROM PhongHoc_CSVC p " +
                     "JOIN CoSoVatChat c ON p.MaCSVC = c.MaCSVC " +
                     "WHERE p.MaPhong = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, maPhong);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("MaPhong"),
                rs.getString("MaCSVC"),
                rs.getString("TenCSVC"),
                rs.getInt("SoLuong")
            });
        }
        conn.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    tablePhongHocCSVC.setModel(model);
}


    // Xóa Phòng Học
private void deletePhongHoc() throws Exception {
    int selectedRow = tablePhongHoc.getSelectedRow();
    if (selectedRow >= 0) {
        String maPhong = (String) tablePhongHoc.getValueAt(selectedRow, 0);
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu transaction

            try {
                // Xóa các thiết bị liên kết với phòng trước
                String deleteCSVC = "DELETE FROM PhongHoc_CSVC WHERE MaPhong=?";
                try (PreparedStatement stmtCSVC = conn.prepareStatement(deleteCSVC)) {
                    stmtCSVC.setString(1, maPhong);
                    stmtCSVC.executeUpdate();
                }

                // Sau đó mới xóa phòng
                String deletePhong = "DELETE FROM PhongHoc WHERE MaPhong=?";
                try (PreparedStatement stmtPhong = conn.prepareStatement(deletePhong)) {
                    stmtPhong.setString(1, maPhong);
                    int rowsAffected = stmtPhong.executeUpdate();
                    if (rowsAffected > 0) {
                        conn.commit(); // Xác nhận transaction
                        JOptionPane.showMessageDialog(this, "Xóa thành công!");
                        loadDataPhongHoc();
                        loadDataPhongHocCSVC("");
                    } else {
                        conn.rollback();
                        JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                    }
                }

            } catch (SQLException ex) {
                conn.rollback(); // Nếu có lỗi thì rollback
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa dữ liệu!");
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng để xóa.");
    }
}


    // Xóa Cơ Sở Vật Chất
    private void deleteCSVC() throws Exception {
        int selectedRow = tablePhongHocCSVC.getSelectedRow();
        if (selectedRow >= 0) {
            String maPhong = (String) tablePhongHocCSVC.getValueAt(selectedRow, 0);
            String maTB = (String) tablePhongHocCSVC.getValueAt(selectedRow, 1);
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "DELETE FROM PhongHoc_CSVC WHERE MaPhong=? AND MaCSVC=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, maPhong);
                stmt.setString(2, maTB);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadDataPhongHocCSVC(maPhong); // Load lại dữ liệu bảng Cơ Sở Vật Chất
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Các phương thức thêm, sửa (sẽ thêm sau)
    private void addPhongHoc() {
        this.dispose();
        new AddPhongHocForm();
    }

    private void addCSVC() throws Exception {
       int selectedRow = tablePhongHoc.getSelectedRow();
    if (selectedRow >= 0) {
        String maPhong = tablePhongHoc.getValueAt(selectedRow, 0).toString();
        this.dispose();
        new AddPhongCSVCForm(maPhong).setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một phòng học trước khi thêm cơ sở vật chất.");
    }
    }

    private void editPhongHoc() {
            int selectedRow = tablePhongHoc.getSelectedRow();
            if (selectedRow != -1) {
                String maPhong = tablePhongHoc.getValueAt(selectedRow, 0).toString();
                String tenPhong = tablePhongHoc.getValueAt(selectedRow, 1).toString();
                String loaiPhong = tablePhongHoc.getValueAt(selectedRow, 2).toString();
                int sucChua = Integer.parseInt(tablePhongHoc.getValueAt(selectedRow, 3).toString());

                EditPhongHocForm editForm = new EditPhongHocForm(maPhong, tenPhong, loaiPhong, sucChua,this);
                dispose();
                editForm.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một phòng để sửa.");
            }
        }

    private void editCSVC() {
        int selectedRow = tablePhongHocCSVC.getSelectedRow();
        int selectedPhongRow = tablePhongHoc.getSelectedRow();

        if (selectedRow != -1 && selectedPhongRow != -1) {
            String maPhong = tablePhongHoc.getValueAt(selectedPhongRow, 0).toString();
            String maTB = tablePhongHocCSVC.getValueAt(selectedRow, 1).toString(); // cột Mã Thiết Bị
            String tenTB = tablePhongHocCSVC.getValueAt(selectedRow, 2).toString(); // cột Tên Thiết Bị
            int soLuong = Integer.parseInt(tablePhongHocCSVC.getValueAt(selectedRow, 3).toString()); // cột Số lượng

            EditPhongCSVCForm editForm = new EditPhongCSVCForm(maPhong, maTB, tenTB, "", "", soLuong, "");
            dispose();
            editForm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn thiết bị trong phòng để sửa.");
        }
    }
}
