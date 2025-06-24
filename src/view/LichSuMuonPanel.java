package view;

import model.TaiKhoan;
import model.YeuCauMuon;
import model.DAO.YeuCauMuonDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LichSuMuonPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private TaiKhoan nguoiDung;

    public LichSuMuonPanel(TaiKhoan tk) {
        this.nguoiDung = tk;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("📜 Lịch sử mượn thiết bị", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{
                "Mã thiết bị", "Tên thiết bị", "Số lượng", "Ngày mượn",
                "Ngày trả dự kiến", "Ngày trả thực tế", "Trạng thái", "Người duyệt", "Lý do từ chối"
        }, 0);
        table = new JTable(model);
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<YeuCauMuon> list = new YeuCauMuonDAO().layLichSuMuonTheoNguoiDung(nguoiDung.getTenDangNhap());

        for (YeuCauMuon yc : list) {
            model.addRow(new Object[]{
                    yc.getMaTB(),
                    yc.getTenTB(),
                    yc.getSoLuongMuon(),
                    yc.getNgayMuon(),
                    yc.getNgayTraDuKien(),
                    yc.getNgayTraThucTe() != null ? yc.getNgayTraThucTe() : "Chưa trả",
                    yc.getTrangThai(),
                    yc.getNguoiDuyet() != null ? yc.getNguoiDuyet() : "",
                    yc.getLyDoTuChoi() != null ? yc.getLyDoTuChoi() : ""
            });
        }
    }
}
