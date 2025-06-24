package view;

import model.KiemKe;
import model.TaiKhoan;
import model.DAO.KiemKeDAO;
import view.JDialog.ChiTietKiemKeDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.ThemKiemKeDialog;

import java.awt.*;
import java.util.List;

public class QuanLyKiemKePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private TaiKhoan nguoiDung;

    public QuanLyKiemKePanel(TaiKhoan nguoiDung) {
        setLayout(new BorderLayout());
        this.nguoiDung = nguoiDung;
        
        // ====== TIÊU ĐỀ ======
        JLabel title = new JLabel("📦 Quản lý kiểm kê thiết bị", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // ====== THANH CÔNG CỤ ======
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnThem = new JButton("➕ Thêm kiểm kê");
        JButton btnRefresh = new JButton("🔄 Làm mới");

        btnThem.addActionListener(e -> {
            new ThemKiemKeDialog((JFrame) SwingUtilities.getWindowAncestor(this), nguoiDung).setVisible(true);
            loadData();
        });

        btnRefresh.addActionListener(e -> loadData());

        topBar.add(btnThem);
        topBar.add(btnRefresh);
        add(topBar, BorderLayout.SOUTH);

        // ====== BẢNG ======
        model = new DefaultTableModel(new Object[] {
            "Mã", "Người kiểm kê", "Phòng", "Ngày", "Loại", "Trạng thái", "Chi tiết"
        }, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();

        table.getColumn("Chi tiết").setCellRenderer(new ButtonRenderer());
        table.getColumn("Chi tiết").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    private void loadData() {
        model.setRowCount(0);
        List<KiemKe> list = new KiemKeDAO().getAllKiemKe();
        for (KiemKe kk : list) {
            model.addRow(new Object[] {
                kk.getMaKiemKe(),
                kk.getTenDangNhap(),
                kk.getMaPhong(),
                kk.getNgayKiemKe(),
                kk.getLoaiKiemKe(),
                kk.getTrangThai(),
                "🔍 Xem"
            });
        }
    }

    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int maKiemKe;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            maKiemKe = (int) table.getValueAt(row, 0);
            button.setText(value.toString());
            return button;
        }

        public Object getCellEditorValue() {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(table); // lấy JFrame cha
                new ChiTietKiemKeDialog(frame, maKiemKe).setVisible(true);
            });
            return button.getText();
        }

    }
}
