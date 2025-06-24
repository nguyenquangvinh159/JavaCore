package ui;

import javax.swing.SwingUtilities;

import view.DangNhapForm;

public class Program {
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> new DangNhapForm().setVisible(true));
	    }
}
