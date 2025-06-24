package view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;
import model.PhongHoc;
import model.DAO.PhongHocDAO;
import view.JDialog.CSVCPhongPanel;

public class QuanLyPhongHocPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private List<PhongHoc> danhSachGoc;

    public QuanLyPhongHocPanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("🏫 Quản lý phòng học", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtSearch = new JTextField(20);
        topBar.add(new JLabel("Tìm theo mã hoặc tên phòng:"));
        topBar.add(txtSearch);
        add(topBar, BorderLayout.SOUTH);

        model = new DefaultTableModel(new Object[]{
            "Mã", "Tên", "Tòa nhà", "Tầng", "Sức chứa", "Loại", "Trạng thái", "Thiết bị"
        }, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();

        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { timKiem(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { timKiem(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { timKiem(); }
        });

        table.getColumn("Trạng thái").setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{
            "Hoạt động", "Đang bảo trì", "Đang sử dụng", "Đóng cửa"
        })));

        table.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            String ma = table.getValueAt(row, 0).toString();
            String trangThai = table.getValueAt(row, 6).toString();
            new PhongHocDAO().capNhatTrangThai(ma, trangThai);
        });

        table.getColumn("Thiết bị").setCellRenderer(new ButtonRenderer());
        table.getColumn("Thiết bị").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    private void loadData() {
        danhSachGoc = new PhongHocDAO().getAllPhongHoc();
        model.setRowCount(0);
        for (PhongHoc p : danhSachGoc) {
            model.addRow(new Object[]{
                p.getMaPhong(), p.getTenPhong(), p.getToaNha(), p.getTang(), p.getSucChua(),
                p.getLoaiPhong(), p.getTrangThai(), "Xem"
            });
        }
    }

    private void timKiem() {
        String keyword = txtSearch.getText().toLowerCase();
        List<PhongHoc> ketQua = danhSachGoc.stream()
            .filter(p -> p.getMaPhong().toLowerCase().contains(keyword) ||
                         p.getTenPhong().toLowerCase().contains(keyword))
            .collect(Collectors.toList());

        model.setRowCount(0);
        for (PhongHoc p : ketQua) {
            model.addRow(new Object[]{
                p.getMaPhong(), p.getTenPhong(), p.getToaNha(), p.getTang(), p.getSucChua(),
                p.getLoaiPhong(), p.getTrangThai(), "Xem"
            });
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String maPhong;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            maPhong = table.getValueAt(row, 0).toString();
            button.setText((value == null) ? "" : value.toString());
            return button;
        }

        public Object getCellEditorValue() {
            // Mở CSVCPhongPanel thay vì JOptionPane
            JDialog dialog = new JDialog();
            dialog.setTitle("Thiết bị trong phòng " + maPhong);
            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(null);
            dialog.setModal(true);
            dialog.setContentPane(new CSVCPhongPanel(maPhong));
            dialog.setVisible(true);
            return button.getText();
        }

    }
}