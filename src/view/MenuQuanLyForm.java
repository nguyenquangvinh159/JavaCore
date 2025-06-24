package view;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import model.ChucNang;
import model.TaiKhoan;
import model.DAO.PhanQuyenDAO;

public class MenuQuanLyForm extends JFrame {
    private TaiKhoan nguoiDung;
    private ArrayList<JButton> buttonList = new ArrayList<>();
    private JPanel mainPanel;
    private Map<JButton, String> buttonMap = new HashMap<>();

    public MenuQuanLyForm(TaiKhoan tk) {
        this.nguoiDung = tk;
        setTitle("Chào mừng - " + tk.getHoTen() + " (" + tk.getVaiTro() + ")");
        if (nguoiDung.getVaiTro().equals("sinhvien")) {
            setSize(900, 600);
        } else {
            setSize(1500, 800);
        }
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        BackgroundPanel bgPanel = new BackgroundPanel("images/bg_main.jpg");
        bgPanel.setLayout(new BorderLayout());
        setContentPane(bgPanel);

        // ====== SIDE MENU ======
        JPanel sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        sideMenu.setBackground(new Color(240, 248, 255, 220));
        sideMenu.setPreferredSize(new Dimension(280, getHeight()));

        // ====== AVATAR + INFO ======
        String avatarPath = "images/" + (tk.getAvatar() != null ? tk.getAvatar() : "default_avatar.jpg");
        ImageIcon icon = new ImageIcon(avatarPath);
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel lblAvatar = new JLabel(new ImageIcon(img));
        lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAvatar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        lblAvatar.setPreferredSize(new Dimension(110, 110));
        lblAvatar.setMaximumSize(new Dimension(110, 110));

        JLabel lblName = new JLabel(tk.getHoTen(), SwingConstants.CENTER);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblRole = new JLabel("(" + tk.getVaiTro() + ")", SwingConstants.CENTER);
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblRole.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblRole.setForeground(Color.GRAY);

        sideMenu.add(Box.createVerticalStrut(20));
        sideMenu.add(lblAvatar);
        sideMenu.add(Box.createVerticalStrut(10));
        sideMenu.add(lblName);
        sideMenu.add(lblRole);
        sideMenu.add(Box.createVerticalStrut(30));

        // ====== MENU BUTTONS ======
        ArrayList<ChucNang> danhSachChucNang = new PhanQuyenDAO().layChucNangTheoRole(tk.getVaiTro());

        for (ChucNang cn : danhSachChucNang) {
            JButton btn = new JButton(cn.getTen());
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(200, 40));
            btn.setBackground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1, true));

            buttonList.add(btn);
            buttonMap.put(btn, cn.getMa());

            btn.addActionListener(e -> moChucNang(cn.getMa(), btn));
            sideMenu.add(btn);
            sideMenu.add(Box.createVerticalStrut(10));
        }

        bgPanel.add(sideMenu, BorderLayout.WEST);
     // ====== NÚT ĐĂNG XUẤT ======
        JButton btnDangXuat = new JButton("🔌 Đăng xuất");
        btnDangXuat.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDangXuat.setMaximumSize(new Dimension(200, 40));
        btnDangXuat.setBackground(new Color(255, 100, 100));
        btnDangXuat.setForeground(Color.WHITE);
        btnDangXuat.setFocusPainted(false);
        btnDangXuat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDangXuat.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnDangXuat.addActionListener(e -> {
            dispose();
            new DangNhapForm().setVisible(true); 
        });

        sideMenu.add(btnDangXuat);
        sideMenu.add(Box.createVerticalStrut(20));

        // ====== MAIN PANEL ======
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        bgPanel.add(mainPanel, BorderLayout.CENTER);
    }

    private void moChucNang(String ma, JButton clickedButton) {
        mainPanel.removeAll();

        for (JButton btn : buttonList) {
            btn.setBackground(btn == clickedButton ? new Color(144, 238, 144) : Color.WHITE);
        }

        switch (ma) {
            case "MANAGE_ACCOUNT":
                mainPanel.add(new QuanLyTaiKhoanPanel()); break;
            case "MANAGE_ROOM":
                mainPanel.add(new QuanLyPhongHocPanel()); break;
            case "APPROVE_BORROW":
                mainPanel.add(new DuyetMuonPanel(nguoiDung)); break;
            case "VIEW_REPORT":
                mainPanel.add(new XuLyBaoCaoPanel(nguoiDung)); break;
            case "MANAGE_INVENTORY":
                mainPanel.add(new QuanLyKiemKePanel(nguoiDung)); break;
            case "MANAGE_MAINTENANCE":
                mainPanel.add(new BaoTriPanel(nguoiDung)); break;
            case "MANAGE_DEVICE":
                mainPanel.add(new DanhSachThietBiPanel()); break;

            case "REQUEST_BORROW":
                mainPanel.add(new YeuCauMuonPanel(nguoiDung)); break;
            case "REPORT_ISSUE":
                mainPanel.add(new BaoCaoSuCoPanel(nguoiDung)); break;
            case "lichsu_muon":
                mainPanel.add(new LichSuMuonPanel(nguoiDung)); break;
            case "lichsu_baocao":
                mainPanel.add(new LichSuBaoCaoPanel(nguoiDung)); break;

            default:
                mainPanel.add(new JLabel("Chức năng chưa hỗ trợ", JLabel.CENTER));
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
