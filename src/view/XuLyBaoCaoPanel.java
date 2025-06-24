package view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import model.BaoCaoSuCo;
import model.DAO.BaoCaoSuCoDAO;
import model.TaiKhoan;

public class XuLyBaoCaoPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private TaiKhoan nguoiDung;

    public XuLyBaoCaoPanel(TaiKhoan tk) {
        this.nguoiDung = tk;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("🛠️ Xử lý báo cáo sự cố", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[] {
            "Mã", "Người báo", "Thiết bị", "Phòng", "Mức độ", "Mô tả", "Ngày báo", "Trạng thái", "Xử lý"
        }, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();

        table.getColumn("Xử lý").setCellRenderer(new ButtonRenderer());
        table.getColumn("Xử lý").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    private void loadData() {
        model.setRowCount(0);
        List<BaoCaoSuCo> list = new BaoCaoSuCoDAO().layBaoCaoChuaXuLy();
        for (BaoCaoSuCo bc : list) {
            model.addRow(new Object[] {
                bc.getMaBC(),
                bc.getTenDangNhap(),
                bc.getMaTB(),
                bc.getMaPhong(),
                bc.getMucDoNghiemTrong(),
                bc.getMoTa(),
                bc.getNgayBaoCao(),
                bc.getTrangThai(),
                "✔ Xử lý"
            });
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int maBC;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            maBC = (int) table.getValueAt(row, 0);
            button.setText(value != null ? value.toString() : "");
            return button;
        }

        public Object getCellEditorValue() {
            String ketQua = JOptionPane.showInputDialog("Nhập kết quả xử lý:");
            if (ketQua != null && !ketQua.trim().isEmpty()) {
                boolean ok = new BaoCaoSuCoDAO().xuLyBaoCao(maBC, nguoiDung.getTenDangNhap(), ketQua);
                if (ok) {
                    JOptionPane.showMessageDialog(null, "Đã xử lý báo cáo!");
                    SwingUtilities.invokeLater(() -> loadData());
                } else {
                    JOptionPane.showMessageDialog(null, "Xử lý thất bại!");
                }
            }
            return button.getText();
        }
    }
}
