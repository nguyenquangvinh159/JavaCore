package util;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Utils {

    // Kiểm tra JTextField có trống không
    public static boolean isEmpty(JTextField txt) {
        return txt.getText().trim().isEmpty();
    }

    // Kiểm tra chuỗi có phải là số nguyên dương
    public static boolean isPositiveInteger(String str) {
        try {
            int num = Integer.parseInt(str.trim());
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Hiện thông báo đơn giản
    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    // Kiểm tra tất cả đầu vào 1 lượt (cho tiện)
    public static boolean validateInputs(JTextField txtMaTB, JTextField txtTenTB, JTextField txtSoLuong) {
        if (isEmpty(txtMaTB)) {
            showMessage("Mã thiết bị không được để trống!");
            txtMaTB.requestFocus();
            return false;
        }

        if (isEmpty(txtTenTB)) {
            showMessage("Tên thiết bị không được để trống!");
            txtTenTB.requestFocus();
            return false;
        }

        if (isEmpty(txtSoLuong)) {
            showMessage("Số lượng không được để trống!");
            txtSoLuong.requestFocus();
            return false;
        }

        if (!isPositiveInteger(txtSoLuong.getText())) {
            showMessage("Số lượng phải là số nguyên dương!");
            txtSoLuong.requestFocus();
            return false;
        }

        return true;
    }
}
