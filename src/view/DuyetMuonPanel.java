package view;

import model.DAO.YeuCauMuonDAO;
import model.TaiKhoan;
import model.YeuCauMuon;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DuyetMuonPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private TaiKhoan nguoiDung;

    public DuyetMuonPanel(TaiKhoan tk) {
    	this.nguoiDung = tk;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("📋 Duyệt yêu cầu mượn thiết bị", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{
            "Mã YC", "Người mượn", "Thiết bị", "Số lượng", "Ngày mượn", "Ngày trả", "Mục đích", "Duyệt", "Từ chối"
        }, 0);

        table = new JTable(model);
        table.setRowHeight(30);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();

        table.getColumn("Duyệt").setCellRenderer(new ButtonRenderer());
        table.getColumn("Duyệt").setCellEditor(new ButtonEditor(new JCheckBox(), "accept"));

        table.getColumn("Từ chối").setCellRenderer(new ButtonRenderer());
        table.getColumn("Từ chối").setCellEditor(new ButtonEditor(new JCheckBox(), "reject"));
    }

    private void loadData() {
        model.setRowCount(0);
        List<YeuCauMuon> ds = new YeuCauMuonDAO().getYeuCauChoDuyet();
        for (YeuCauMuon yc : ds) {
            model.addRow(new Object[]{
                yc.getMaYeuCau(),
                yc.getTenDangNhap(),
                yc.getMaTB(),
                yc.getSoLuongMuon(),
                yc.getNgayMuon(),
                yc.getNgayTraDuKien(),
                yc.getMucDichSuDung(),
                "✔️ Duyệt", "❌ Từ chối"
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
        private String action;
        private JButton button;
        private int maYeuCau;

        public ButtonEditor(JCheckBox checkBox, String action) {
            super(checkBox);
            this.action = action;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
            maYeuCau = (int) table.getValueAt(row, 0);
            button.setText(value.toString());
            return button;
        }

        public Object getCellEditorValue() {
        	if ("accept".equals(action)) {
        	    new YeuCauMuonDAO().duyetYeuCau(maYeuCau, nguoiDung.getTenDangNhap(), true, null);
        	    JOptionPane.showMessageDialog(null, "Đã duyệt yêu cầu!");
        	} else if ("reject".equals(action)) {
        	    String lyDo = JOptionPane.showInputDialog("Nhập lý do từ chối:");
        	    if (lyDo != null && !lyDo.trim().isEmpty()) {
        	        new YeuCauMuonDAO().duyetYeuCau(maYeuCau, nguoiDung.getTenDangNhap(), false, lyDo);
        	        JOptionPane.showMessageDialog(null, "Đã từ chối yêu cầu!");
        	    }
        	}
        	SwingUtilities.invokeLater(() -> loadData());
            return button.getText();
        }
    }
}
