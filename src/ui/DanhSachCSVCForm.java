package ui;

import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import util.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import controller.ThemSCVCForm;
import controller.CapNhatCSVCForm;  
import ui.MenuQuanLyForm;

public class DanhSachCSVCForm extends javax.swing.JFrame {

    public DanhSachCSVCForm() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCSVC = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanelFind = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnUpDate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnTroVe = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/logo_nha.jpg"))); // NOI18N
        jLabel1.setText("QUẢN LÝ THÔNG TIN CƠ SỞ VẬT CHẤT");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Danh sách Cơ Sở Vật Chất");

        tblCSVC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblCSVC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã số", "Tên thiết bị", "Loại thiết bị", "Đơn vị tính", "Số lượng", "Tình trạng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCSVC);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Chức năng");

        jPanelFind.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelFind.setToolTipText("");

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Icon_KinhLup.jpg"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelFindLayout = new javax.swing.GroupLayout(jPanelFind);
        jPanelFind.setLayout(jPanelFindLayout);
        jPanelFindLayout.setHorizontalGroup(
            jPanelFindLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFindLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelFindLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelFindLayout.setVerticalGroup(
            jPanelFindLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFindLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Icon_Add.jpg"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpDate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnUpDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/icon_UpDate.jpg"))); // NOI18N
        btnUpDate.setText("Cập nhật");
        btnUpDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpDateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/Icon_ThungRac.jpg"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnTroVe.setText("Trở về");
        btnTroVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTroVeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(jPanelFind, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUpDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(btnTroVe))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpDate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTroVe)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void loadDataToTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã TB", "Tên TB", "Loại TB", "Đơn vị tính", "Tình trạng", "Số lượng"});

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM CoSoVatChat";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maTB = rs.getString("MaTB");
                String tenTB = rs.getString("TenTB");
                String loaiTB = rs.getString("LoaiTB");
                String donViTinh = rs.getString("DonViTinh");
                String tinhTrang = rs.getString("TinhTrang");
                int soLuong = rs.getInt("SoLuong");

                model.addRow(new Object[]{maTB, tenTB, loaiTB, donViTinh, tinhTrang, soLuong});
            }

            tblCSVC.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu: " + e.getMessage());
        }
    }
    private void btnUpDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpDateActionPerformed
        int selectedRow = tblCSVC.getSelectedRow();
        if (selectedRow != -1) {
            String maTB = tblCSVC.getValueAt(selectedRow, 0).toString();
            String tenTB = tblCSVC.getValueAt(selectedRow, 1).toString();
            String loaiTB = tblCSVC.getValueAt(selectedRow, 2).toString();
            int soLuong = Integer.parseInt(tblCSVC.getValueAt(selectedRow, 3).toString());
            String tinhTrang = tblCSVC.getValueAt(selectedRow, 4).toString();
            String donViTinh = tblCSVC.getValueAt(selectedRow, 5).toString();

            CapNhatCSVCForm capNhatForm = new CapNhatCSVCForm(maTB, tenTB, loaiTB, soLuong, tinhTrang, donViTinh,this);
            capNhatForm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để cập nhật.");
        }

    }//GEN-LAST:event_btnUpDateActionPerformed
    
    public void loadData() {
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "SELECT * FROM ThietBi";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        DefaultTableModel model = (DefaultTableModel) tblCSVC.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        while (rs.next()) {
            Object[] row = {
                rs.getString("MaTB"),
                rs.getString("TenTB"),
                rs.getString("LoaiTB"),
                rs.getInt("SoLuong"),
                rs.getString("TinhTrang"),
                rs.getString("DonViTinh")
            };
            model.addRow(row);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu: " + ex.getMessage());
    }
}

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        this.dispose();
        new ThemSCVCForm().setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnTroVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTroVeActionPerformed
        this.dispose();
        new MenuQuanLyForm().setVisible(true);
    }//GEN-LAST:event_btnTroVeActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        String tuKhoa = txtTimKiem.getText().trim();

        DefaultTableModel model = (DefaultTableModel) tblCSVC.getModel();
        model.setRowCount(0); 

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM CoSoVatChat WHERE TenTB LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + tuKhoa + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maTB = rs.getString("MaTB");
                String tenTB = rs.getString("TenTB");
                String loaiTB = rs.getString("LoaiTB");
                String donViTinh = rs.getString("DonViTinh");
                String tinhTrang = rs.getString("TinhTrang");
                int soLuong = rs.getInt("SoLuong");

                model.addRow(new Object[]{maTB, tenTB, loaiTB, donViTinh, tinhTrang, soLuong});
            }

            tblCSVC.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm: " + e.getMessage());
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int rowIndex = tblCSVC.getSelectedRow();

    if (rowIndex != -1) {
        String maTB = tblCSVC.getValueAt(rowIndex, 0).toString();

        DefaultTableModel model = (DefaultTableModel) tblCSVC.getModel();
        model.removeRow(rowIndex);

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM CoSoVatChat WHERE MaCSVC = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, maTB);

            int result = pst.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu để xóa.");
            }

            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + e.getMessage());
        }

    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để xóa.");
    }
    }//GEN-LAST:event_btnDeleteActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTroVe;
    private javax.swing.JButton btnUpDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanelFind;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCSVC;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
