package view;

import javax.swing.SwingUtilities;
import model.DangNhapForm;


public class Program {
     public static void main(String[] args) {
        // Đảm bảo giao diện Swing được khởi chạy đúng cách
        SwingUtilities.invokeLater(() -> {
            new DangNhapForm().setVisible(true);
        });
    }
}
