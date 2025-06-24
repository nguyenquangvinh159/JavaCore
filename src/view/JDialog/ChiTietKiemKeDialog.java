package view.JDialog;

import model.ChiTietKiemKe;
import model.DAO.ChiTietKiemKeDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ChiTietKiemKeDialog extends JDialog {
    private JTable table;
    private DefaultTableModel model;
    private int maKiemKe;

    public ChiTietKiemKeDialog(JFrame parent, int maKiemKe) {
        super(parent, "📋 Chi tiết kiểm kê", true);
        this.maKiemKe = maKiemKe;

        setSize(800, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("📋 Danh sách thiết bị kiểm kê #" + maKiemKe, JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        // ==== BẢNG ====
        model = new DefaultTableModel(new Object[]{
                "Mã TB", "Tên TB", "Số lượng sổ sách", "Số lượng thực tế", "Tình trạng", "Ghi chú"
        }, 0) {
            public boolean isCellEditable(int row, int col) {
                return col == 3 || col == 4 || col == 5; // Chỉ cho phép sửa 3 cột này
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ==== NÚT CẬP NHẬT ====
        JButton btnCapNhat = new JButton("💾 Cập nhật");
        btnCapNhat.addActionListener(e -> capNhatChiTiet());
        JPanel bottom = new JPanel();
        bottom.add(btnCapNhat);
        add(bottom, BorderLayout.SOUTH);

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<ChiTietKiemKe> list = new ChiTietKiemKeDAO().getChiTiet(maKiemKe);
        for (ChiTietKiemKe ct : list) {
            model.addRow(new Object[]{
                    ct.getMaTB(),
                    ct.getTenTB(),
                    ct.getSoSach(),
                    ct.getThucTe(),
                    ct.getTinhTrang(),
                    ct.getGhiChu()
            });
        }
    }

    private void capNhatChiTiet() {
        try {
            for (int i = 0; i < model.getRowCount(); i++) {
                String maTB = model.getValueAt(i, 0).toString();
                String tenTB = model.getValueAt(i, 1).toString(); // chỉ để hiển thị
                int soSach = Integer.parseInt(model.getValueAt(i, 2).toString());
                int thucTe = Integer.parseInt(model.getValueAt(i, 3).toString());
                String tinhTrang = model.getValueAt(i, 4).toString();
                String ghiChu = model.getValueAt(i, 5).toString();

                ChiTietKiemKe ct = new ChiTietKiemKe(maTB, tenTB, soSach, thucTe, tinhTrang, ghiChu);
                new ChiTietKiemKeDAO().capNhatChiTiet(maKiemKe, ct);
            }

            JOptionPane.showMessageDialog(this, "✅ Đã cập nhật chi tiết kiểm kê!");
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Lỗi khi cập nhật: " + ex.getMessage());
        }
    }
}