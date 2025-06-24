package view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import model.DAO.BaoCaoSuCoDAO;
import model.BaoCaoSuCo;
import model.TaiKhoan;

public class LichSuBaoCaoPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public LichSuBaoCaoPanel(TaiKhoan nguoiDung) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("📑 Lịch sử báo cáo sự cố", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{
            "Mã", "Thiết bị", "Phòng", "Mức độ", "Ngày báo cáo", "Trạng thái", "Kết quả"
        }, 0);
        table = new JTable(model);
        table.setRowHeight(28);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData(nguoiDung.getTenDangNhap());
    }

    private void loadData(String tenDangNhap) {
        List<BaoCaoSuCo> list = new BaoCaoSuCoDAO().layLichSuBaoCao(tenDangNhap);
        model.setRowCount(0);
        for (BaoCaoSuCo bc : list) {
            model.addRow(new Object[]{
                bc.getMaBC(),
                bc.getTenTB(),
                bc.getMaPhong(),
                bc.getMucDoNghiemTrong(),
                bc.getNgayBaoCao(),
                bc.getTrangThai(),
                bc.getKetQuaXuLy() == null ? "" : bc.getKetQuaXuLy()
            });
        }
    }
}
