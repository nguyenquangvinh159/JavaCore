package view;

import javax.swing.*;
import javax.swing.table.*;

import controller.CapNhatTaiKhoanForm;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;
import model.TaiKhoan;
import model.DAO.TaiKhoanDAO;
import view.DanhSachThietBiPanel.ButtonEditor;
import view.DanhSachThietBiPanel.ButtonRenderer;

public class QuanLyTaiKhoanPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private List<TaiKhoan> danhSachGoc;

    public QuanLyTaiKhoanPanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("👤 Quản lý tài khoản", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("🔍 Tìm");
        JButton btnAdd = new JButton("📥 Duyệt tài khoản");

        topBar.add(new JLabel("Tìm tên đăng nhập hoặc họ tên:"));
        topBar.add(txtSearch);
        topBar.add(btnSearch);
        topBar.add(Box.createHorizontalStrut(30));
        topBar.add(btnAdd);

        add(topBar, BorderLayout.SOUTH);

        model = new DefaultTableModel(new Object[]{
            "Tên đăng nhập", "Họ tên", "Vai trò", "Email", "SĐT", "Trạng thái", "Sửa", "Vô hiệu hóa"
        }, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadData();

        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { timKiem(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { timKiem(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { timKiem(); }
        });
        btnAdd.addActionListener(e -> {
            List<TaiKhoan> canDuyet = danhSachGoc.stream()
                .filter(tk -> tk.getTrangThai() == 0)
                .collect(Collectors.toList());

            if (canDuyet.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có tài khoản nào chờ duyệt.");
            } else {
                String message = "Có " + canDuyet.size() + " tài khoản chờ duyệt.\nHãy kích hoạt từ danh sách.";
                JOptionPane.showMessageDialog(this, message);
            }
        });
    }

    private void timKiem() {
        String keyword = txtSearch.getText().toLowerCase();
        List<TaiKhoan> ketQua = danhSachGoc.stream()
            .filter(tk -> tk.getTenDangNhap().toLowerCase().contains(keyword) ||
                          tk.getHoTen().toLowerCase().contains(keyword))
            .collect(Collectors.toList());

        model.setRowCount(0);
        for (TaiKhoan tk : ketQua) {
            model.addRow(toRow(tk));
        }
    }

    private void loadData() {
        danhSachGoc = new TaiKhoanDAO().getAllTaiKhoan();
        model.setRowCount(0);
        for (TaiKhoan tk : danhSachGoc) {
            model.addRow(toRow(tk));
        }
        table.getColumn("Sửa").setCellRenderer(new ButtonRenderer());
        table.getColumn("Sửa").setCellEditor(new ButtonEditor(new JCheckBox(), "edit"));

        table.getColumn("Vô hiệu hóa").setCellRenderer(new ButtonRenderer());
        table.getColumn("Vô hiệu hóa").setCellEditor(new ButtonEditor(new JCheckBox(), "toggle"));
    }

    private Object[] toRow(TaiKhoan tk) {
        return new Object[]{
            tk.getTenDangNhap(),
            tk.getHoTen(),
            tk.getVaiTro(),
            tk.getEmail(),
            tk.getSoDienThoai(),
            tk.getTrangThai() == 1 ? "Hoạt động" : "Vô hiệu hóa",
            "Sửa",
            tk.getTrangThai() == 1 ? "Vô hiệu hóa" : "Kích hoạt"
        };
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
        private String action;
        private JButton button;
        private String tenDangNhap;

        public ButtonEditor(JCheckBox checkBox, String action) {
            super(checkBox);
            this.action = action;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
            tenDangNhap = table.getValueAt(row, 0).toString(); // lấy Tên đăng nhập
            button.setText((value == null) ? "" : value.toString());
            return button;
        }

        public Object getCellEditorValue() {
            if ("edit".equals(action)) {
                TaiKhoan tk = danhSachGoc.stream()
                    .filter(t -> t.getTenDangNhap().equals(tenDangNhap))
                    .findFirst().orElse(null);
                if (tk != null) {
                    new CapNhatTaiKhoanForm(tk, () -> loadData());
                }
            } else if ("toggle".equals(action)) {
                int xacNhan = JOptionPane.showConfirmDialog(null,
                    "Bạn có chắc muốn thay đổi trạng thái tài khoản này?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (xacNhan == JOptionPane.YES_OPTION) {
                    boolean ok = new TaiKhoanDAO().toggleTrangThai(tenDangNhap);
                    if (ok) {
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(null, "Cập nhật trạng thái thất bại!");
                    }
                }
            }
            return button.getText();
        }
    }

}
