package view;

import javax.swing.*;
import javax.swing.table.*;

import controller.CapNhatThietBiForm;
import controller.ThemThietBiForm;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;
import model.ThietBi;
import model.DAO.ThietBiDAO;

public class DanhSachThietBiPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private List<ThietBi> danhSachGoc;
    private JLabel lblImage;

    public DanhSachThietBiPanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("📋 Danh sách thiết bị", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtSearch = new JTextField(20);
        JButton btnAdd = new JButton("➕ Thêm thiết bị");

        topBar.add(new JLabel("Tìm theo tên:"));
        topBar.add(txtSearch);
        topBar.add(Box.createHorizontalStrut(30));
        topBar.add(btnAdd);
        add(topBar, BorderLayout.SOUTH);

        model = new DefaultTableModel(new Object[]{"Mã", "Tên", "Loại", "Số lượng", "Tình trạng", "HinhAnh", "Sửa", "Xoá"}, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        table.getColumn("HinhAnh").setMinWidth(0);
        table.getColumn("HinhAnh").setMaxWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        lblImage = new JLabel();
        lblImage.setPreferredSize(new Dimension(200, 200));
        lblImage.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(lblImage, BorderLayout.EAST);

        loadData();

        table.getColumn("Sửa").setCellRenderer(new ButtonRenderer());
        table.getColumn("Sửa").setCellEditor(new ButtonEditor(new JCheckBox(), "edit"));
        table.getColumn("Xoá").setCellRenderer(new ButtonRenderer());
        table.getColumn("Xoá").setCellEditor(new ButtonEditor(new JCheckBox(), "delete"));

        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { timKiem(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { timKiem(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { timKiem(); }
        });

        btnAdd.addActionListener(e -> SwingUtilities.invokeLater(() -> new ThemThietBiForm().setVisible(true)));

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    String imgPath = model.getValueAt(row, 5).toString();
                    if (imgPath != null && !imgPath.isEmpty()) {
                        ImageIcon icon = new ImageIcon(imgPath);
                        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        lblImage.setIcon(new ImageIcon(img));
                    } else {
                        lblImage.setIcon(null);
                    }
                }
            }
        });
    }

    private void timKiem() {
        String keyword = txtSearch.getText().toLowerCase();
        List<ThietBi> ketQua = danhSachGoc.stream()
                .filter(tb -> tb.getTenTB().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        model.setRowCount(0);
        for (ThietBi tb : ketQua) {
            model.addRow(new Object[]{tb.getMaTB(), tb.getTenTB(), tb.getLoaiTB(), tb.getSoLuong(), tb.getTinhTrang(), tb.getHinhAnh(), "Sửa", "Xoá"});
        }
    }

    private void loadData() {
        model.setRowCount(0);
        danhSachGoc = new ThietBiDAO().getAllThietBi();
        for (ThietBi tb : danhSachGoc) {
            model.addRow(new Object[]{tb.getMaTB(), tb.getTenTB(), tb.getLoaiTB(), tb.getSoLuong(), tb.getTinhTrang(), tb.getHinhAnh(), "Sửa", "Xoá"});
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private String action;
        private JButton button;
        private String maTB;

        public ButtonEditor(JCheckBox checkBox, String action) {
            super(checkBox);
            this.action = action;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            maTB = table.getValueAt(row, 0).toString();
            button.setText((value == null) ? "" : value.toString());
            return button;
        }

        public Object getCellEditorValue() {
            if ("delete".equals(action)) {
                int confirm = JOptionPane.showConfirmDialog(null, "Xoá thiết bị " + maTB + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = new ThietBiDAO().delete(maTB);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Đã xoá thành công!");
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(null, "Xoá thất bại!");
                    }
                }
            } else if ("edit".equals(action)) {
            	ThietBi tb = danhSachGoc.stream()
            		    .filter(t -> t.getMaTB().equals(maTB))
            		    .findFirst().orElse(null);

            		if (tb != null) {
            		    SwingUtilities.invokeLater(() -> new CapNhatThietBiForm(tb).setVisible(true));
            		}
            }
            return button.getText();
        }
    }
}