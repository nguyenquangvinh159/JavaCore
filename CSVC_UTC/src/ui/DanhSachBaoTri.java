package ui;

import util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class DanhSachBaoTri extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;

    public DanhSachBaoTri() throws Exception {
        setTitle("Danh Sách Bảo Trì - Sửa Chữa");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(new String[]{
                "Mã BT", "Mã CSVC", "Ngày bảo trì", "Nội dung", "Trạng thái"
        }, 0);

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Buttons panel
        JButton btnThem = new JButton("Thêm");
        btnThem.addActionListener(e -> {
			try {
				openAddDialog();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        JButton btnSua = new JButton("Sửa");
        btnSua.addActionListener(e -> {
			try {
				openEditDialog();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        JButton btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(e -> {
			try {
				xoaBaoTri();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        JButton btnLamMoi = new JButton("Trở về");
        btnLamMoi.addActionListener(e -> {
        	this.dispose();
            new MenuQuanLyForm().setVisible(true);
		});

        txtSearch = new JTextField(20);
        txtSearch.setToolTipText("Tìm kiếm theo Mã BT hoặc Mã CSVC hoặc Nội dung");
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
					loadData(txtSearch.getText().trim());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(txtSearch);

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnThem);
        btnPanel.add(btnSua);
        btnPanel.add(btnXoa);
        btnPanel.add(btnLamMoi);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(btnPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadData();
    }

    // Load full data with optional search keyword
    private void loadData() throws Exception {
        loadData("");
    }

    private void loadData(String keyword) throws Exception {
        model.setRowCount(0); // Clear bảng
        String sql = "SELECT * FROM BaoTri_SuaChua";
        if (!keyword.isEmpty()) {
            sql += " WHERE MaBT LIKE ? OR MaCSVC LIKE ? OR NoiDung LIKE ?";
        }
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (!keyword.isEmpty()) {
                String likeKeyword = "%" + keyword + "%";
                ps.setString(1, likeKeyword);
                ps.setString(2, likeKeyword);
                ps.setString(3, likeKeyword);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getString("MaBT"),
                            rs.getString("MaCSVC"),
                            rs.getDate("NgayBaoTri"),
                            rs.getString("NoiDung"),
                            rs.getString("TrangThai")
                    });
                }
            }
        } catch (SQLException ex) {
            showErrorDialog("Lỗi khi tải dữ liệu", ex);
        }
    }

    private void xoaBaoTri() throws Exception {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để xóa");
            return;
        }

        String maBT = table.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa phiếu bảo trì: " + maBT + "?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            deleteMaintenanceRecord(maBT);
        }
    }

    private void deleteMaintenanceRecord(String maBT) throws Exception {
        String sql = "DELETE FROM BaoTri_SuaChua WHERE MaBT = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maBT);
            int affected = ps.executeUpdate();
            if (affected > 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                loadData(txtSearch.getText().trim());
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
        } catch (SQLException ex) {
            showErrorDialog("Lỗi khi xóa dữ liệu", ex);
        }
    }

    private void openAddDialog() throws Exception {
        MaintenanceDialog dialog = new MaintenanceDialog(this, "Thêm phiếu bảo trì", null);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            loadData(txtSearch.getText().trim());
        }
    }

    private void openEditDialog() throws Exception {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để sửa");
            return;
        }
        String maBT = table.getValueAt(selectedRow, 0).toString();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM BaoTri_SuaChua WHERE MaBT = ?")) {
            ps.setString(1, maBT);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    MaintenanceRecord record = new MaintenanceRecord(
                            rs.getString("MaBT"),
                            rs.getString("MaCSVC"),
                            rs.getDate("NgayBaoTri"),
                            rs.getString("NoiDung"),
                            rs.getString("TrangThai")
                    );
                    MaintenanceDialog dialog = new MaintenanceDialog(this, "Sửa phiếu bảo trì", record);
                    dialog.setVisible(true);
                    if (dialog.isSaved()) {
                        loadData(txtSearch.getText().trim());
                    }
                }
            }
        } catch (SQLException ex) {
            showErrorDialog("Lỗi khi tải dữ liệu để sửa", ex);
        }
    }

    private void showErrorDialog(String message, Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, message);
    }

    // Inner class to hold maintenance record data
    private static class MaintenanceRecord {
        String maBT;
        String maCSVC;
        java.util.Date ngayBaoTri;
        String noiDung;
        String trangThai;

        public MaintenanceRecord(String maBT, String maCSVC, java.util.Date ngayBaoTri, String noiDung, String trangThai) {
            this.maBT = maBT;
            this.maCSVC = maCSVC;
            this.ngayBaoTri = ngayBaoTri;
            this.noiDung = noiDung;
            this.trangThai = trangThai;
        }
    }

    // Dialog to Add/Edit maintenance record
    private class MaintenanceDialog extends JDialog {
        private JTextField txtMaBT;
        private JTextField txtMaCSVC;
        private JTextField txtNgayBaoTri;
        private JTextField txtNoiDung;
        private JComboBox<String> cmbTrangThai;
        private boolean saved = false;
        private MaintenanceRecord existingRecord;

        public MaintenanceDialog(JFrame parent, String title, MaintenanceRecord record) {
            super(parent, title, true);
            existingRecord = record;
            initComponents();
            if (record != null) {
                loadRecord(record);
                txtMaBT.setEnabled(false); // MaBT usually is primary key, disable editing
            }
            pack();
            setLocationRelativeTo(parent);
        }

        private void initComponents() {
            JLabel lblMaBT = new JLabel("Mã BT:");
            JLabel lblMaCSVC = new JLabel("Mã CSVC:");
            JLabel lblNgayBaoTri = new JLabel("Ngày bảo trì (yyyy-MM-dd):");
            JLabel lblNoiDung = new JLabel("Nội dung:");
            JLabel lblTrangThai = new JLabel("Trạng thái:");

            txtMaBT = new JTextField(20);
            txtMaCSVC = new JTextField(20);
            txtNgayBaoTri = new JTextField(20);
            txtNoiDung = new JTextField(20);
            cmbTrangThai = new JComboBox<>(new String[]{"Đang chờ", "Đang tiến hành", "Hoàn thành", "Hủy"});

            JButton btnSave = new JButton("Lưu");
            JButton btnCancel = new JButton("Hủy");

            btnSave.addActionListener(e -> {
				try {
					saveRecord();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
            btnCancel.addActionListener(e -> dispose());

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(4, 4, 4, 4);
            c.anchor = GridBagConstraints.WEST;

            c.gridx = 0; c.gridy = 0;
            panel.add(lblMaBT, c);
            c.gridx = 1;
            panel.add(txtMaBT, c);

            c.gridx = 0; c.gridy++;
            panel.add(lblMaCSVC, c);
            c.gridx = 1;
            panel.add(txtMaCSVC, c);

            c.gridx = 0; c.gridy++;
            panel.add(lblNgayBaoTri, c);
            c.gridx = 1;
            panel.add(txtNgayBaoTri, c);

            c.gridx = 0; c.gridy++;
            panel.add(lblNoiDung, c);
            c.gridx = 1;
            panel.add(txtNoiDung, c);

            c.gridx = 0; c.gridy++;
            panel.add(lblTrangThai, c);
            c.gridx = 1;
            panel.add(cmbTrangThai, c);

            JPanel btnPanel = new JPanel();
            btnPanel.add(btnSave);
            btnPanel.add(btnCancel);

            getContentPane().setLayout(new BorderLayout());
            getContentPane().add(panel, BorderLayout.CENTER);
            getContentPane().add(btnPanel, BorderLayout.SOUTH);
        }

        private void loadRecord(MaintenanceRecord record) {
            txtMaBT.setText(record.maBT);
            txtMaCSVC.setText(record.maCSVC);
            txtNgayBaoTri.setText(record.ngayBaoTri.toString());
            txtNoiDung.setText(record.noiDung);
            cmbTrangThai.setSelectedItem(record.trangThai);
        }

        private void saveRecord() throws Exception {
            String maCSVC = txtMaCSVC.getText().trim();
            String ngayBaoTriStr = txtNgayBaoTri.getText().trim();
            String noiDung = txtNoiDung.getText().trim();
            String trangThai = (String) cmbTrangThai.getSelectedItem();

            if (maCSVC.isEmpty() || ngayBaoTriStr.isEmpty() || noiDung.isEmpty() || trangThai.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
                return;
            }

            java.sql.Date ngayBaoTri;
            try {
                ngayBaoTri = java.sql.Date.valueOf(ngayBaoTriStr);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Ngày bảo trì phải có định dạng yyyy-MM-dd");
                return;
            }

            if (existingRecord == null) {
                // Insert new record
                String sql = "INSERT INTO BaoTri_SuaChua (MaCSVC, NgayBaoTri, NoiDung, TrangThai) VALUES (?, ?, ?, ?)";
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {

                    ps.setString(1, maCSVC);
                    ps.setDate(2, ngayBaoTri);
                    ps.setString(3, noiDung);
                    ps.setString(4, trangThai);

                    int inserted = ps.executeUpdate();
                    if (inserted > 0) {
                        JOptionPane.showMessageDialog(this, "Thêm phiếu bảo trì thành công");
                        saved = true;
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm phiếu bảo trì thất bại");
                    }
                } catch (SQLException ex) {
                    showErrorDialog("Lỗi khi thêm dữ liệu", ex);
                }
            } else {
                // Update existing record
                String sql = "UPDATE BaoTri_SuaChua SET MaCSVC = ?, NgayBaoTri = ?, NoiDung = ?, TrangThai = ? WHERE MaBT = ?";
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {

                    ps.setString(1, maCSVC);
                    ps.setDate(2, ngayBaoTri);
                    ps.setString(3, noiDung);
                    ps.setString(4, trangThai);
                    ps.setString(5, existingRecord.maBT); // Sử dụng maBT từ existingRecord

                    int updated = ps.executeUpdate();
                    if (updated > 0) {
                        JOptionPane.showMessageDialog(this, "Cập nhật phiếu bảo trì thành công");
                        saved = true;
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật phiếu bảo trì thất bại");
                    }
                } catch (SQLException ex) {
                    showErrorDialog("Lỗi khi cập nhật dữ liệu", ex);
                }
            }
        }

        public boolean isSaved() {
            return saved;
        }
    }
}
