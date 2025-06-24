package view;

import model.LichBaoTri;
import model.TaiKhoan;
import model.DAO.LichBaoTriDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.ThemBaoTriDialog;

import java.awt.*;
import java.util.List;

public class BaoTriPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private TaiKhoan nguoiDung; 
    public BaoTriPanel(TaiKhoan nguoiDung) {
        setLayout(new BorderLayout());
        this.nguoiDung = nguoiDung;
        JLabel title = new JLabel("🛠️ Quản lý bảo trì thiết bị", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnThem = new JButton("➕ Lịch bảo trì mới");
        JButton btnRefresh = new JButton("🔄 Làm mới");
        topBar.add(btnThem); topBar.add(btnRefresh);
        add(topBar, BorderLayout.SOUTH);

        model = new DefaultTableModel(new Object[]{
            "Mã", "Thiết bị", "Loại", "Ngày dự kiến", "Nội dung", "Người thực hiện", "Chi phí", "Trạng thái"
        }, 0);
        table = new JTable(model);
        table.setRowHeight(28);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();

        btnThem.addActionListener(e -> {
        	new ThemBaoTriDialog((JFrame) SwingUtilities.getWindowAncestor(this), nguoiDung).setVisible(true);
            loadData();
        });
        btnRefresh.addActionListener(e -> loadData());
    }

    private void loadData() {
        model.setRowCount(0);
        List<LichBaoTri> list = new LichBaoTriDAO().getAllLichBaoTri();
        for (LichBaoTri lbt : list) {
            model.addRow(new Object[]{
                lbt.getMaBaoTri(),
                lbt.getMaTB(),
                lbt.getLoaiBaoTri(),
                lbt.getNgayDuKien(),
                lbt.getNoiDung(),
                lbt.getNguoiThucHien(),
                lbt.getChiPhi(),
                lbt.getTrangThai()
            });
        }
    }
}
