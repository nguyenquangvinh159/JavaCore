/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.SwingUtilities;
import model.DangNhapForm;

/**
 *
 * @author Acer
 */
public class Program {
     public static void main(String[] args) {
        // Đảm bảo giao diện Swing được khởi chạy đúng cách
        SwingUtilities.invokeLater(() -> {
            new DangNhapForm().setVisible(true);
        });
    }
}
