package model;

public class ChiTietKiemKe {
    private String maTB;
    private String tenTB;
    private int soSach;
    private int thucTe;
    private String tinhTrang;
    private String ghiChu;

    public ChiTietKiemKe() {
    }

    public ChiTietKiemKe(String maTB, String tenTB, int soSach, int thucTe, String tinhTrang, String ghiChu) {
        this.maTB = maTB;
        this.tenTB = tenTB;
        this.soSach = soSach;
        this.thucTe = thucTe;
        this.tinhTrang = tinhTrang;
        this.ghiChu = ghiChu;
    }

    // Getters
    public String getMaTB() { return maTB; }
    public String getTenTB() { return tenTB; }
    public int getSoSach() { return soSach; }
    public int getThucTe() { return thucTe; }
    public String getTinhTrang() { return tinhTrang; }
    public String getGhiChu() { return ghiChu; }

    // Setters
    public void setMaTB(String maTB) { this.maTB = maTB; }
    public void setTenTB(String tenTB) { this.tenTB = tenTB; }
    public void setSoSach(int soSach) { this.soSach = soSach; }
    public void setThucTe(int thucTe) { this.thucTe = thucTe; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}
