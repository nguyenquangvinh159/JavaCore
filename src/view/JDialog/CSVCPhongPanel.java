package view.JDialog;

import javax.swing.*;
import javax.swing.table.*;

import controller.ThemThietBiVaoPhongForm;

import java.awt.*;
import java.util.List;
import model.DAO.CSVCPhongDAO;
import model.CSVCPhong;

public class CSVCPhongPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private String maPhong;

    public CSVCPhongPanel(String maPhong) {
        this.maPhong = maPhong;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("📦 Cơ sở vật chất của phòng: " + maPhong, JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{
            "Mã TB", "Tên thiết bị", "Số lượng", "Ngày lắp", "Tình trạng"
        }, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();
        JButton btnThem = new JButton("➕ Thêm thiết bị");
        btnThem.addActionListener(e -> {
            new ThemThietBiVaoPhongForm(maPhong, () -> loadData()).setVisible(true);
        });
        add(btnThem, BorderLayout.SOUTH);
    }

    private void loadData() {
        List<CSVCPhong> list = new CSVCPhongDAO().getThietBiTheoPhong(maPhong);
        model.setRowCount(0);
        for (CSVCPhong c : list) {
            model.addRow(new Object[]{
                c.getMaTB(), c.getTenTB(), c.getSoLuong(), c.getNgayLapDat(), c.getTinhTrang()
            });
        }
    }
}